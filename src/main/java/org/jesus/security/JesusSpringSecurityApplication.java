package org.jesus.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jesus.security.domain.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class JesusSpringSecurityApplication implements CommandLineRunner {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JesusSpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var encryptPassword = passwordEncoder.encode("inlove");
		var user = userService.createUser("jesus", encryptPassword);
		log.info("create first user: {}", user.getUserName());
	}
}
