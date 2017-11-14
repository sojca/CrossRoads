package behaviors.car;

import agents.CarAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Car waits in queue until its first
 */
public class EnqueueBehaviour extends Behaviour {

    private boolean isFirstInQueue = false;

    @Override
    public void onStart() {
        isFirstInQueue = false;

        CarAgent agent = (CarAgent) myAgent;

        // Inform crossroad that car has entered it
        if (agent.getCrossroad() != null) {
            String msg = Integer.toString(agent.getSource());
            ACLMessage m = new ACLMessage(ACLMessage.PROPOSE);
            m.addReceiver(agent.getCrossroad().getName());
            m.setContent(msg);
            myAgent.send(m);
            System.out.println("Informovany");
        } else {
            System.out.println("nemam sluzbu");
        }
    }

    @Override
    public void action() {
        System.out.println("WAITING IN QUEUE " + myAgent.getLocalName());

        // Wait for message
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage msg = myAgent.receive(mt);

        if (msg == null) {
            block();
        } else {
            isFirstInQueue = true;
        }
    }

    @Override
    public boolean done() {
        return isFirstInQueue;
    }
}
