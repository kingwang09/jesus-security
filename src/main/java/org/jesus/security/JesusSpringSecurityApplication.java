package org.jesus.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jesus.security.domain.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class JesusSpringSecurityApplication implements CommandLineRunner {

	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JesusSpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var user = userService.createUser("jesus", "inlove");
		log.debug("create first user: {}", user.getUserName());

	}
}
