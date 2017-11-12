package behaviors.car;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Car delay while passing through crossroad
 */
public class MovementDelayBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        long delay = 1000;
        System.out.println("Car is passing through crossroad " + myAgent.getLocalName());
        myAgent.blockingReceive(delay);
    }
}
