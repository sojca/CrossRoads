package behaviors.car;

import jade.core.behaviours.FSMBehaviour;

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

        System.out.println("HERE");
        
        registerFirstState(new ChooseDirectionsBehavior(), STATE_CHOOSE_DIRECTION);
        registerState(new EnqueueBehaviour(), STATE_ENQUEUE);
        registerState(new WaitForGreenLightBehaviour(), STATE_WAIT_GREEN);
        registerState(new MovementDelayBehavior(), STATE_MOVEMENT_DELAY);
        registerLastState(new ExitCrossroadBehavior(), STATE_ENDPOINT);

        registerDefaultTransition(STATE_CHOOSE_DIRECTION, STATE_ENQUEUE);
        registerDefaultTransition(STATE_ENQUEUE, STATE_WAIT_GREEN);
        registerDefaultTransition(STATE_WAIT_GREEN, STATE_MOVEMENT_DELAY, new String[] { STATE_WAIT_GREEN });
        registerDefaultTransition(STATE_MOVEMENT_DELAY, STATE_ENDPOINT);
    }
}
