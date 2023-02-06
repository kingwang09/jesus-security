package org.jesus.security.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder encoder;

    private final String value = "hello world";

    @Test
    public void test(){
        //given & when
        String encodedValue1 = encoder.encode(value);
        String encodedValue2 = encoder.encode(value);

        log.debug("encodedValue1: {}", encodedValue1);
        log.debug("encodedValue2: {}", encodedValue2);

        //then: encodedValue가 달라야한다.
        Assertions.assertFalse(encodedValue1.equals(encodedValue2));
    }
}
