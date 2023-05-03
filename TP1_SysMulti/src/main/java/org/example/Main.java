package org.example;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
public class Main {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile= new ProfileImpl();
        profile.setParameter("gui", "true");
        AgentContainer container = runtime.createMainContainer(profile);
        container.start();
    }
}