package org.example.rsa;

import java.security.*;
import java.util.Base64;

public class GenerateRSAKeys {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("Private key: " + privateKey);
        System.out.println("Public key: " + publicKey);
        String encoderPRK = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String encoderPUBK = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Private key: " + encoderPRK);
        System.out.println("Public key: " + encoderPUBK);

    }
}
