package behaviors.car;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Overall car behavior in crossroad
 */
public class CarBehaviour extends FSMBehaviour {

    private static final String STATE_CHOOSE_DIRECTION = "choosing_direction";
    private static final String STATE_ENQUEUE = "enqueue";
    private static final String STATE_WAIT_GREEN = "wait_for_green_light";
    private static final String STATE_MOVEMENT_DELAY = "queue_delay";
    private static final String STATE_ENDPOINT = "endpoint";

    public CarBehaviour() {
        super();

        registerFirstState(new ChooseDirectionsBehavior(), STATE_CHOOSE_DIRECTION);
        registerState(new EnqueueBehaviour(), STATE_ENQUEUE);
        registerState(new WaitForGreenLightBehaviour(), STATE_WAIT_GREEN);
        registerState(new MovementDelayBehavior(), STATE_MOVEMENT_DELAY);
        registerLastState(leaveCrossroad, STATE_ENDPOINT);

        registerDefaultTransition(STATE_CHOOSE_DIRECTION, STATE_ENQUEUE);
        registerDefaultTransition(STATE_ENQUEUE, STATE_WAIT_GREEN);
        registerDefaultTransition(STATE_WAIT_GREEN, STATE_MOVEMENT_DELAY);
        registerDefaultTransition(STATE_MOVEMENT_DELAY, STATE_ENDPOINT);
    }

    /**
     * Car is leaving crossroad
     */
    private final OneShotBehaviour leaveCrossroad = new OneShotBehaviour() {
        @Override
        public void action() {
            System.out.println("Car |" + myAgent.getLocalName() + "| is leaving crossroad");
        }
    };
}
