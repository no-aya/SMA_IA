package org.example.rsa;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {

    @Override
    protected void setup() {
        System.out.println("Server Agent running");

        String encodedPrk = (String) getArguments()[0];
        byte[] decodePrk = Base64.getDecoder().decode(encodedPrk);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    String encryEncodedMsg = aclMessage.getContent();
                    byte[] encryptMSG = Base64.getDecoder().decode(encryEncodedMsg);
                    try {
                        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodePrk));
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] decrypted = cipher.doFinal(encryptMSG);
                        System.out.println("Decrypted message: " + new String(decrypted));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    block();
                }
            }
        });
            }
}

