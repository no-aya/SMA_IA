package org.example;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SimpleAgentContainer1 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile= new ProfileImpl();
        profile.setParameter("host", "localhost");

        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController agentClient = container.createNewAgent("Client", "org.example.AgentClient", new Object[]{});
        agentClient.start();
    }
}
