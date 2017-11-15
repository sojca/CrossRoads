package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.FSMBehaviour;

/**
 *
 * @author patri
 */
public class LightsBehaviour extends FSMBehaviour {

    private static final String STATE_BASIC = "basic";
    private static final String STATE_SWITCH = "transition";

    public LightsBehaviour() {
        super();

        CrossRoad crossroad = (CrossRoad) myAgent;
        
        registerFirstState(new TransitionBehaviour(), STATE_SWITCH);
        registerState(new BasicBehaviour(crossroad), STATE_BASIC);

        registerDefaultTransition(STATE_SWITCH, STATE_BASIC);
        registerDefaultTransition(STATE_BASIC, STATE_SWITCH, new String[] { STATE_BASIC });
    }
}
