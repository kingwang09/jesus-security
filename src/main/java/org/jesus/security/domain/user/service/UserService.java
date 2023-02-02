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

    @Transactional
    public User updateUser(Long id, String password, Set<UserRole> roles){
        var user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id+"의 회원을 찾을 수 없습니다."));

        //현재 권한 모두 삭제
        var currentRoles = user.getRoles();
        roleRepository.deleteAll(currentRoles);

        var newRoles = roles.stream().map(v -> Role.builder().user(user).role(v).build()).collect(Collectors.toSet());
        user.addRoles(newRoles);
        user.change(password, newRoles);

        //신규 권한 추가
        roleRepository.saveAll(newRoles);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username+" 해당 사용자가 존재하지 않습니다."));
        return new SecurityUser(user);
    }
}
