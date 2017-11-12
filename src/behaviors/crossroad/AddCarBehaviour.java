package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behavior for adding new cars into queues
 */
public class AddCarBehaviour extends CyclicBehaviour {

    private final CrossRoad crossroad;

    public AddCarBehaviour() {
        crossroad = (CrossRoad) myAgent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        } else {
            // Parse message
            int direction = Integer.parseInt(msg.getContent());

            ACLMessage reply = msg.createReply();

            // Enqueue car
            crossroad.enqueueCar(direction, msg.getSender());

            myAgent.send(reply);
        }
    }
}
