package org.jesus.security.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Slf4j
@SpringBootTest
public class EncryptorTest {

    private final String value = "Hello world";
    private final String secretKey = "jesus";

    /**
     * 256 byte AES encrypt
     */
    @Test
    public void standardBytesEncryptTest(){
        String salt = KeyGenerators.string().generateKey();
        BytesEncryptor encryptor = Encryptors.standard(secretKey, salt);

        byte[] encryptedValue = encryptor.encrypt(value.getBytes());
        log.debug("encrypt value: {}", encryptedValue);
        byte[] decryptedValue = encryptor.decrypt(encryptedValue);
        log.debug("decrypt value: {}", decryptedValue);

        Assertions.assertEquals(new String(value.getBytes()), new String(decryptedValue));
    }

    @Test
    public void strongBytesEncryptorTest(){
        String salt = KeyGenerators.string().generateKey();
        BytesEncryptor encryptor = Encryptors.stronger(secretKey, salt);

        byte[] encryptedValue1 = encryptor.encrypt(value.getBytes());
        byte[] encryptedValue2 = encryptor.encrypt(value.getBytes());
        log.debug("encrypt value1: {}", encryptedValue1);
        log.debug("encrypt value2: {}", encryptedValue2);

        byte[] decryptedValue1 = encryptor.decrypt(encryptedValue1);
        byte[] decryptedValue2 = encryptor.decrypt(encryptedValue1);
        log.debug("decrypt value1: {}", decryptedValue1);
        log.debug("decrypt value2: {}", decryptedValue2);

        Assertions.assertEquals(new String(value.getBytes()), new String(decryptedValue1));
    }

    @Test
    public void strongStringEncryptorTest(){
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(secretKey, salt);

        String encryptedValue = encryptor.encrypt(value);
        log.debug("encrypt value: {}", encryptedValue);
        String decryptedValue = encryptor.decrypt(encryptedValue);
        log.debug("decrypt value: {}", decryptedValue);

        Assertions.assertEquals(value, decryptedValue);
    }

    @Test
    public void queryableTest(){
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.queryableText(secretKey, salt);

        String encryptedValue1 = encryptor.encrypt(value);
        String encryptedValue2 = encryptor.encrypt(value);
        log.debug("encrypt value1: {}", encryptedValue1);
        log.debug("encrypt value2: {}", encryptedValue2);
        Assertions.assertEquals(encryptedValue1, encryptedValue2);

        String decryptedValue1 = encryptor.decrypt(encryptedValue1);
        String decryptedValue2 = encryptor.decrypt(encryptedValue2);
        log.debug("decrypt value1: {}", decryptedValue1);
        log.debug("decrypt value2: {}", decryptedValue2);

        Assertions.assertEquals(value, decryptedValue1);
    }
}
