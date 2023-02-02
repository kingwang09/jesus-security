package org.jesus.security.user.service;

import lombok.extern.slf4j.Slf4j;
import org.jesus.security.domain.user.constant.UserRole;
import org.jesus.security.domain.user.entity.Role;
import org.jesus.security.domain.user.entity.User;
import org.jesus.security.domain.user.repository.RoleRepository;
import org.jesus.security.domain.user.repository.UserRepository;
import org.jesus.security.domain.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    private User user1 = User.builder()
                .userName("basic-user")
                .password("1234")
            .build();

    private Role user1Role = Role.builder()
            .role(UserRole.USER)
            .user(user1)
            .build();

    @BeforeEach
    public void init(){
        //user1 & user1의 권한도 추가
        userRepository.save(user1);
        roleRepository.save(user1Role);
    }

    /**
     * 돌아는 가는데 쿼리가 너무 많이 돌아감.. 체크 필요
     */
    @Test
    public void 사용자수정(){
        //given & when
        User updatedUser = userService.updateUser(user1.getId(), "9876", Set.of(UserRole.MAKER, UserRole.ADMIN));

        //then
        Assertions.assertEquals("9876", updatedUser.getPassword());

        List<UserRole> hasRoles = updatedUser.getRoles().stream().map(Role::getRole).collect(Collectors.toList());
        Assertions.assertTrue(hasRoles.contains(UserRole.MAKER));
        Assertions.assertTrue(hasRoles.contains(UserRole.ADMIN));
    }
}
