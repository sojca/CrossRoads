
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lib.Constants.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author juraj.sojcak
 */
public class Generator extends Agent {

    @Override
    protected void setup() {
        System.out.println("generator");
        AgentContainer c = getContainerController();
        try {
            AgentController crossAgent = c.createNewAgent(
                    "crossroad",
                    "CrossRoad",
                    new Object[]{this.getAID()});
            crossAgent.start();

            AgentController carAgent;
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                int src = rand.nextInt(DIRECTIONS - 1) + 1;  // Random 1 - 4
                int dst = rand.nextInt(DIRECTIONS - 2) + 1;  // Random 1 - 3
                carAgent = c.createNewAgent(
                        "car" + i,
                        "CarAgent",
                        new Object[]{src, (src + dst) % DIRECTIONS});  // Random source and destination, cannot be same

                carAgent.start();
            }
        } catch (StaleProxyException ex) {
            System.out.println("ERORORROPROROR ======");
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
