package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lib.Constants;

/**
 * Inform cars requesting to know whether crossroad lights from their directions are green
 */
public class CrossroadStatus extends CyclicBehaviour {

    @Override
    public void action() {
        CrossRoad crossroad = (CrossRoad) myAgent;

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        } else {
            System.out.println("RECEIVED REQUEST");
            // Parse message
            int direction = Integer.parseInt(msg.getContent());

            // Respond only if light is green
            if (crossroad.getSemaphore(direction) == Constants.GREEN) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.PROPAGATE);
                myAgent.send(reply);
            }
        }
    }
}
