package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behavior for adding new cars into queues
 */
public class AddCarBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        CrossRoad crossroad = (CrossRoad) myAgent;

        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        } else {
            // Parse message
            int direction = Integer.parseInt(msg.getContent());

            // Enqueue car
            if (crossroad == null) {
                System.out.println("NULL");
            }
            crossroad.enqueueCar(direction, msg.getSender());
        }
    }
}
