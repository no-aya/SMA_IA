package ma.enset.sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class AgentServer extends GuiAgent {
    private AgentServerGui agentServerGui;
    @Override
    protected void setup() {
        System.out.println("***  la méthode setup *****");
        agentServerGui=(AgentServerGui)getArguments()[0];
        agentServerGui.setAgentServer(this);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG!=null){
                    agentServerGui.showMessage("<<=="+receivedMSG.getContent());
                    System.out.println(receivedMSG.getContent());
                    System.out.println(receivedMSG.getSender().getName());
                }else {
                    block();
                }

            }}
        );

    }

    @Override
    protected void beforeMove() {
        System.out.println("***  la méthode beforeMove *****");
    }

    @Override
    protected void afterMove() {
        System.out.println("***  la méthode afterMove *****");
    }

    @Override
    protected void takeDown() {
        System.out.println("***  la méthode takeDown *****");
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        String parameter =(String) guiEvent.getParameter(0);
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("client",AID.ISLOCALNAME));
        message.setContent(parameter);
        send(message);
    }
}
