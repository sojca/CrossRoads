
import jade.core.Agent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juraj.sojcak
 */
public class CarAgent extends Agent{
    
    protected void setup(){
        System.out.println("this");
        Object[] args = getArguments();
        for (Object arg : args) {
            System.out.println(arg);
        }
    }
}
