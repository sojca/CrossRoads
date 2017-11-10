
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juraj.sojcak
 */
public class CrossRoad extends Agent{
    
    protected int counter = 0;
    
    protected void setup(){
        System.out.println("there is crossroad");
        
        try {
            DFAgentDescription dfd = new DFAgentDescription();  // DF register
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("magic crossroad");
            sd.setType("crossroad");
            sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
            dfd.addServices(sd);
            DFService.register(this, dfd);
        }
        catch (FIPAException e){
            e.printStackTrace();
        }
        
        addBehaviour(new ReceivedMsgBehaviour());
    }
    
}

class ReceivedMsgBehaviour extends CyclicBehaviour {
    protected AID sender;

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if(msg == null){
            block();
        }
        else{
            sender = msg.getSender();
            
            if(msg.getPerformative() == ACLMessage.CFP){
                System.out.println("prislo auto: " + sender.getName());
            }

        }
        
    }
    
}