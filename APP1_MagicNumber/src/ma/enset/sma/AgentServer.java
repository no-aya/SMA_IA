package ma.enset.sma;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class AgentServer extends GuiAgent {
    private AgentServerGui agentServerGui;
    private int magicNumber;
    List<String> all=new ArrayList<>();

    @Override
    protected void setup() {
        magicNumber=magicNumber();
        System.out.println("***  la méthode setup *****");
        agentServerGui=(AgentServerGui)getArguments()[0];
        agentServerGui.setAgentServer(this);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                //test if a client is connected
                ACLMessage receivedMSG = receive();
                if (receivedMSG!=null){
                    agentServerGui.showMessage(receivedMSG.getContent());
                    //Convert message to integer
                    Integer number=Integer.parseInt(receivedMSG.getContent());//get the client id
                    String clientName=receivedMSG.getSender().getLocalName();
                    //Collect Clients Ids
                    if (!all.contains(clientName)){
                        all.add(clientName);
                    }

                    //Test if number is equal to magic number
                    String response=guessNumber(number, clientName);


                    //Send response to client
                    ACLMessage responseMSG=new ACLMessage(ACLMessage.INFORM);
                    responseMSG.addReceiver(new AID(clientName,AID.ISLOCALNAME));
                    responseMSG.setContent(response);
                    send(responseMSG);
                    agentServerGui.showMessage("==>>"+response);
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

    /*Generating number methode between 0 and 100*/
    protected int magicNumber(){
        return (int)(Math.random()*100);
    }

    protected void sendToAll(String clientName){
        ACLMessage responseMSG=new ACLMessage(ACLMessage.INFORM);
        for (String client : all) {
            responseMSG.addReceiver(new AID(client,AID.ISLOCALNAME));
        }
        responseMSG.setContent("Bravo "+clientName+" a gagné");
        send(responseMSG);

    }

    protected String guessNumber(Integer number,String clientName){
        if(number==magicNumber){
            magicNumber=magicNumber();
            sendToAll(clientName);
            return "Bravo\nRénitialisation du nombre!";
        }else if(number<magicNumber){
            return "Plus grand";
        }else{
            return "Plus petit";
        }
    }
}
