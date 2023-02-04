package org.jesus.security.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

@Slf4j
@SpringBootTest
public class KeyGeneratorTest {

    @Test
    public void stringKeyGenerator(){
        StringKeyGenerator keyGenerator = KeyGenerators.string();
        String salt1 = keyGenerator.generateKey();
        String salt2 = keyGenerator.generateKey();

        log.debug("key1 = {}", salt1);
        log.debug("key2 = {}", salt2);
    }

    @Test
    public void byteKeyGenerator(){
        BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom();
        byte[] salt1 = keyGenerator.generateKey();
        byte[] salt2 = keyGenerator.generateKey();

        log.debug("key1 = {}, size={}", salt1, keyGenerator.getKeyLength());
        log.debug("key2 = {}, size={}", salt2, keyGenerator.getKeyLength());
    }

    @Test
    public void sharedByteKeyGenerator(){
        BytesKeyGenerator keyGenerator = KeyGenerators.shared(8);
        byte[] salt1 = keyGenerator.generateKey();
        byte[] salt2 = keyGenerator.generateKey();

        log.debug("key1 = {}, size={}", salt1, keyGenerator.getKeyLength());
        log.debug("key2 = {}, size={}", salt2, keyGenerator.getKeyLength());

        keyGenerator = KeyGenerators.shared(8);
        byte[] salt3 = keyGenerator.generateKey();
        byte[] salt4 = keyGenerator.generateKey();

        log.debug("key3 = {}, size={}", salt3, keyGenerator.getKeyLength());
        log.debug("key4 = {}, size={}", salt4, keyGenerator.getKeyLength());
    }
}
