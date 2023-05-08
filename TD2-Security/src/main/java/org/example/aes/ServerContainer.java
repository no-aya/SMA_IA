package org.example.aes;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class ServerContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer agentContainer= runtime.createAgentContainer(profile);
        String password = "1234567812345678";//128 bit key
        AgentController serverAgent= agentContainer.createNewAgent("server", ServerAgent.class.getName(), new Object[]{password});
        serverAgent.start();
    }

}
