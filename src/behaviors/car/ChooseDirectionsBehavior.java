package behaviors.car;

import agents.CarAgent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Random;
import static lib.Constants.DIRECTIONS;

/**
 * Car sets starting direction and ending direction
 */
public class ChooseDirectionsBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        CarAgent agent = (CarAgent) myAgent;

        Random rand = new Random();
        int src = rand.nextInt(DIRECTIONS - 1) + 1;  // Random 1 - 4
        agent.setSource(src);

        int dst = rand.nextInt(DIRECTIONS - 2) + 1;  // Random 1 - 3
        agent.setDestination((src + dst) % DIRECTIONS);    // Random source and destination, cannot be same

        System.out.println("DIRECTIONS - " + agent.getName() + " - souce: " + src + " - dest: " + dst);
    }
}
