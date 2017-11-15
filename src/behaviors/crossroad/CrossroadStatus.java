package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Duh
 *
 * @author patri
 */
public class CrossroadStatus extends CyclicBehaviour {

    @Override
    public void action() {
        CrossRoad crossroad = (CrossRoad) myAgent;
        
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        }
        
        else {
            System.out.println("RECEIVED REQUEST");
            // Parse message
            int direction = Integer.parseInt(msg.getContent());
            
            // Respont with crossroad light status
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.PROPAGATE);

            reply.setContent(Integer.toString(crossroad.getSemaphore(direction)));

            myAgent.send(reply);
        }
    }
}
