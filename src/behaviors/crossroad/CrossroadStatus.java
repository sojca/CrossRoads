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

    private CrossRoad crossroad;

    public CrossroadStatus() {
        crossroad = (CrossRoad) myAgent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        }
        
        else {
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
