package org.example.aes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {

    @Override
    protected void setup() {
        String password = (String) getArguments()[0];
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                System.out.println("Decrypting msg");
                if (aclMessage != null) {
                    String encry = aclMessage.getContent();
                    byte[] encryptMSG = Base64.getDecoder().decode(encry);
                    SecretKey secretKey = new SecretKeySpec(password.getBytes(), "AES");
                    try {
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE, secretKey);
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

