package org.example.signature;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class ServerAgent extends Agent {

    @Override
    protected void setup() {
        System.out.println("Server Agent running");

        String encodedPbk = (String) getArguments()[0];
        byte[] decodePbk = Base64.getDecoder().decode(encodedPbk);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    String documentSign = aclMessage.getContent();
                    String[] documentSplit = documentSign.split("__.__");


                    byte[] decodedDocument = Base64.getDecoder().decode(documentSplit[0]);
                    //Trying to modify document
                    decodedDocument = new String(decodedDocument).replace("Hello", "Bye").getBytes();
                    //
                    byte[] decodedSign = Base64.getDecoder().decode(documentSplit[1]);

                    try {
                        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodePbk));
                        Signature signature = Signature.getInstance("SHA256withRSA");
                        signature.initVerify(publicKey);
                        signature.update(decodedDocument);
                        boolean verify = signature.verify(decodedSign);
                        System.out.println("********Document********");
                        System.out.println(new String(decodedDocument));
                        System.out.println("***************************************");
                        System.out.println("********Signature********");
                        System.out.println(Arrays.toString(decodedSign));
                        System.out.println("***********************");
                        System.out.println("********Verify********");
                        System.out.println(verify?"OK":"NOT OK");
                        System.out.println("*********************");

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

