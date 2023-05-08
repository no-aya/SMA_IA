package org.example.rsa;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ClientContainer {
    public static void main(String[] args) throws StaleProxyException {


        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        String encoderPbk = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALu8secepveCbYBZJs8E+/VJRJtSSd62zphKkn2LtbhvHbK/ZhvqBzP6Rvq6yovNYdQEi1FmJTbTISVG9L6z3j8CAwEAAQ==";//128 bit key
        AgentController clientAgent = agentContainer.createNewAgent("client", ClientAgent.class.getName(), new Object[]{encoderPbk});
        clientAgent.start();
    }
}