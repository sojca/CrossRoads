package behaviors.crossroad;

import agents.CrossRoad;
import jade.core.behaviours.OneShotBehaviour;
import lib.Constants;

/**
 * Crossroad changing semaphore lights
 */
public class TransitionBehaviour extends OneShotBehaviour {

    @Override
    public void action() {
        CrossRoad crossroad = (CrossRoad) myAgent;

        if (crossroad.getSemaphore(Constants.NORTH) == Constants.GREEN && crossroad.getSemaphore(Constants.SOUTH) == Constants.GREEN) {
            System.out.println("lights switched W + E");
            crossroad.setSemaphore(Constants.NORTH, Constants.RED);
            crossroad.setSemaphore(Constants.SOUTH, Constants.RED);
            crossroad.setSemaphore(Constants.WEST, Constants.GREEN);
            crossroad.setSemaphore(Constants.EAST, Constants.GREEN);
            crossroad.propageStateTwo();
        } else {
            System.out.println("lights switched N + S");
            crossroad.setSemaphore(Constants.NORTH, Constants.GREEN);
            crossroad.setSemaphore(Constants.SOUTH, Constants.GREEN);
            crossroad.setSemaphore(Constants.WEST, Constants.RED);
            crossroad.setSemaphore(Constants.EAST, Constants.RED);
            crossroad.propageStateOne();
        }
    }
}
