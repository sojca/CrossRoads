
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
public class Generator extends Agent{
    
    @Override
    protected void setup(){
        System.out.println("volaco");
        AgentContainer c = getContainerController();
        try {
            AgentController ac = c.createNewAgent("car", "CarAgent", new Object[]{this.getAID()});
            ac.start();
        } catch (StaleProxyException ex) {
            System.out.println("ERORORROPROROR ======");
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
      
       
    }
}
