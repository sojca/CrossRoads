package agents;


import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    "agents.CrossRoad",
                    new Object[]{this.getAID()});
            crossAgent.start();

            AgentController carAgent;

            for (int i = 0; i < 10; i++) {

                carAgent = c.createNewAgent(
                        "car" + i,
                        "agents.CarAgent",
                        new Object[]{});

                carAgent.start();
            }
        } catch (StaleProxyException ex) {
            System.out.println("ERORORROPROROR ======");
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
