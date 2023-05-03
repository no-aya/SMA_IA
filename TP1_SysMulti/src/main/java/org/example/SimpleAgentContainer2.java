package org.example;

import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class SimpleAgentContainer2 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile= new ProfileImpl();
        profile.setParameter("host", "localhost");

        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController agentClient = container.createNewAgent("Server", "org.example.AgentServer", new Object[]{});
        agentClient.start();
    }
}
