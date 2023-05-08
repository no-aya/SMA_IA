package org.example.signature;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ServerContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer agentContainer= runtime.createAgentContainer(profile);
        String encoderPbk = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALu8secepveCbYBZJs8E+/VJRJtSSd62zphKkn2LtbhvHbK/ZhvqBzP6Rvq6yovNYdQEi1FmJTbTISVG9L6z3j8CAwEAAQ==";
        AgentController serverAgent= agentContainer.createNewAgent("server", ServerAgent.class.getName(), new Object[]{encoderPbk});
        serverAgent.start();
    }

}
