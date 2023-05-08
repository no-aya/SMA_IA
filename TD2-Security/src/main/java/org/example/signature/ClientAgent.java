package org.example.signature;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Client Agent running");

                String documents = "Hello Server!";
                String encodedPrk = (String) getArguments()[0];
                byte[] decodedPrk = Base64.getDecoder().decode(encodedPrk);

                try {
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPrk));
                    Signature signature = Signature.getInstance("SHA256withRSA");
                    signature.initSign(privateKey);
                    signature.update(documents.getBytes());
                    byte[] sign = signature.sign();
                    String encodedSign = Base64.getEncoder().encodeToString(sign);
                    String encodedDocuments = Base64.getEncoder().encodeToString(documents.getBytes());
                    String encryptedEncoded = encodedDocuments + "__.__" + encodedSign;


                    System.out.println("********Encrypted Document with signature********");
                    System.out.println(encryptedEncoded);
                    System.out.println("*************************************************");



                    ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(encryptedEncoded);
                    aclMessage.addReceiver(new AID("server", AID.ISLOCALNAME));
                    send(aclMessage);

                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }
        });
    }
}

