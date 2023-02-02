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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username+" 해당 사용자가 존재하지 않습니다."));
        return new SecurityUser(user);
    }
}
