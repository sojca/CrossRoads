package behaviors.car;

import agents.CarAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * Car waits in queue until its first
 */
public class EnqueueBehaviour extends Behaviour {

    private boolean isFirstInQueue = false;

    @Override
    public void onStart() {
        isFirstInQueue = false;

        CarAgent agent = (CarAgent) myAgent;

        if (agent.getCrossroad() != null) {
            String msg = Integer.toString(agent.getSource());
            ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
            m.addReceiver(agent.getCrossroad().getName());
            m.setContent(msg);
            myAgent.send(m);
        } else {
            System.out.println("nemam sluzbu");
        }
    }

    @Override
    public void action() {
        System.out.println("WAITING IN QUEUE " + myAgent.getLocalName());

        // Wait for message
        ACLMessage msg = myAgent.receive();

        if (msg == null) {
            block();
        } else {
            if (msg.getPerformative() == ACLMessage.INFORM) {
                isFirstInQueue = true;
            }
        }
    }

    @Override
    public boolean done() {
        return isFirstInQueue;
    }
}
