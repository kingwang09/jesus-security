package org.jesus.security.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jesus.security.domain.user.constant.UserRole;
import org.jesus.security.domain.user.entity.Role;
import org.jesus.security.domain.user.entity.SecurityUser;
import org.jesus.security.domain.user.entity.User;
import org.jesus.security.domain.user.repository.RoleRepository;
import org.jesus.security.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User createUser(String userName, String password){
        var user = User.builder()
                .userName(userName)
                .password(password)
                .build();
        var userRole = Role.builder()
                .role(UserRole.USER)
                .build();
        userRole.setUser(user);

        userRepository.save(user);
        roleRepository.save(userRole);
        return user;
    }

    /**
     * 지금은 권한 수정 시
     * - 일괄 삭제 -> 추가하고 있는데 이게 최선인가
     * @param id
     * @param password
     * @param roles
     * @return
     */
    @Transactional
    public User updateUser(Long id, String password, Set<UserRole> roles){
        var user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id+"의 회원을 찾을 수 없습니다."));

        //현재 권한 모두 삭제
        var currentRoles = user.getRoles();//lazy쿼리 발생하기 때문에 위에서 fetch로 가져오는건 어떨까
        roleRepository.deleteAll(currentRoles);

        //신규 권한 추가
        var newRoles = roles.stream().map(v -> Role.builder().user(user).role(v).build()).collect(Collectors.toSet());
        roleRepository.saveAll(newRoles);

        //사용자 수정 로직
        user.change(password, newRoles);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username+" 해당 사용자가 존재하지 않습니다."));
        return new SecurityUser(user);
    }
}
