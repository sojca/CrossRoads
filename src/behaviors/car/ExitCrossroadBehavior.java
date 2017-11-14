package behaviors.car;

import agents.CarAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ExitCrossroadBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        CarAgent agent = (CarAgent) myAgent;

        // Inform crossroad that car is leaving and from which direction
        String msg = Integer.toString(agent.getSource());
        ACLMessage m = new ACLMessage(ACLMessage.INFORM);
        m.addReceiver(agent.getCrossroad().getName());
        m.setContent(msg);
        myAgent.send(m);

        System.out.println("Car |" + myAgent.getLocalName() + "| is leaving crossroad");
    }
}
