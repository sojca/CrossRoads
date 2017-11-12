package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behavior for removing cars from crossroad that are ready to leave
 */
public class RemoveCarBehavior extends CyclicBehaviour {

    private final CrossRoad crossroad;

    public RemoveCarBehavior() {
        crossroad = (CrossRoad) myAgent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        }
        else {
            int direction = Integer.parseInt(msg.getContent());
            System.out.println("CAR IS LEAVING: " + direction);

            // Remove car from queue
            crossroad.unqueueCar(direction, msg.getSender());
        }
    }
}
