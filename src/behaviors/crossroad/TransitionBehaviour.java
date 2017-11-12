package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Random;
import lib.Constants;

/**
 * Crossroad changing semaphore lights
 */
public class TransitionBehaviour extends OneShotBehaviour {

    @Override
    public void action() {
        CrossRoad crossroad = (CrossRoad) myAgent;

        if (crossroad.getSemaphore(Constants.NORTH) == Constants.GREEN && crossroad.getSemaphore(Constants.SOUTH) == Constants.GREEN) {
            crossroad.setSemaphore(Constants.NORTH, Constants.RED);
            crossroad.setSemaphore(Constants.SOUTH, Constants.RED);
            crossroad.setSemaphore(Constants.WEST, Constants.GREEN);
            crossroad.setSemaphore(Constants.EAST, Constants.GREEN);
        } else {
            crossroad.setSemaphore(Constants.NORTH, Constants.GREEN);
            crossroad.setSemaphore(Constants.SOUTH, Constants.GREEN);
            crossroad.setSemaphore(Constants.WEST, Constants.RED);
            crossroad.setSemaphore(Constants.EAST, Constants.RED);

        }
    }

    @Override
    public int onEnd() {
        Random r = new Random();
        return 1000 * (1 + r.nextInt(5));
    }
}
