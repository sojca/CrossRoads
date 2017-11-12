package behaviors.car;

import agents.CarAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import lib.Constants;

/**
 * Car waits for green light
 */
public class WaitForGreenLightBehaviour extends Behaviour {

    private boolean isGreenLight;

    @Override
    public void onStart() {
        isGreenLight = false;

        System.out.println("WAITING FOR GREEN LIGHT " + myAgent.getLocalName());
    }

    @Override
    public void action() {
        CarAgent agent = (CarAgent) myAgent;

        // Ask about crossroad state
        String msg = Integer.toString(agent.getSource());
        ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
        m.addReceiver(agent.getCrossroad().getName());
        m.setContent(msg);
        myAgent.send(m);

        // Wait for response
        ACLMessage rsp = myAgent.receive();

        if (rsp == null) {
            block();
        } else {
            if (rsp.getPerformative() == ACLMessage.CONFIRM) {
                int status = Integer.parseInt(rsp.getContent());
                isGreenLight = (status == Constants.GREEN);
            }
        }
    }

    @Override
    public boolean done() {
        return isGreenLight;
    }
}
