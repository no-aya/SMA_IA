package org.example.signature;

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
        String encodPrk = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAu7yx5x6m94JtgFkmzwT79UlEm1JJ3rbOmEqSfYu1uG8dsr9mG+oHM/pG+rrKi81h1ASLUWYlNtMhJUb0vrPePwIDAQABAkA2kVVAcWdkn9OGL+OY3sEBfcdkQhnYk1gfaeWEelxae+o7Q2fOojpK/BbjgaCjUIsool7HHp7vyNgwXQQjiBQhAiEA2+5WTqL9PjyaYqBWhuRqcv7pjn6N8zhknCkVU92yIC8CIQDahreB6K7z+1V97SAc79IDeO74IHaFCqaWYsjdTo8O8QIgLpzLCgudZM1vYyvcauaKcSj500ubRktTd411ibrY/90CIHf8+qwBKHddLL8dQcHByO4RLB3gLVO12my+xw4FQbXxAiEAkYxauaiswiwblAxHHzO5NsDma6YDMqMzJZD1JFjXAKA=";//128 bit key

        //128 bit key
        AgentController clientAgent = agentContainer.createNewAgent("client", ClientAgent.class.getName(), new Object[]{encodPrk});
        clientAgent.start();
    }
}