package org.example.aes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String message ="Hello server!";
                String password = (String) getArguments()[0];
                SecretKey secretKey = new SecretKeySpec(password.getBytes(), "AES");
                try {
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    byte[] encrypted = cipher.doFinal(message.getBytes());
                   String encryptedandencod = Base64.getEncoder().encodeToString(encrypted);
                    ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                    aclMessage.addReceiver(new AID("server", AID.ISLOCALNAME));
                    aclMessage.setContent(encryptedandencod);
                    send(aclMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }
        });
    }
}

