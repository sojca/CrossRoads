package behaviors.crossroad;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.WakerBehaviour;
import java.util.Date;

/**
 *
 * @author patri
 */
public class BasicBehaviour extends WakerBehaviour {

    private int lastExitValue = -1;

    public BasicBehaviour(Agent a, Date wakeupDate) {
        super(a, wakeupDate);
    }

    @Override
    public void onStart() {
        FSMBehaviour fsm = (FSMBehaviour) parent;
        lastExitValue = fsm.getLastExitValue();
    }

    @Override
    public int onEnd() {
        return lastExitValue;
    }
}
