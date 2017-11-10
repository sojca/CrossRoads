
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author juraj.sojcak
 */
public class CarAgent extends Agent {

    //private String crossAgentName;
    protected DFAgentDescription[] pas;

    protected void setup() {
        System.out.println("auto");
        Object args[] = getArguments();
        for (Object arg : args) {
            System.out.println(arg);
        }

        addBehaviour(new WakerBehaviour(this, 1000) {

            @Override
            protected void onWake() {
                String msg = "som auto";
                
                getService("crossroad");
                if (pas.length > 0) {
                    ACLMessage m = new ACLMessage(ACLMessage.CFP);
                    m.addReceiver(pas[0].getName());
                    m.setContent(msg);
                    myAgent.send(m);
                } else {
                    System.out.println("nemam sluzbu");
                }
            }
        });
    }
    
    protected void getService(String service) {
        ServiceDescription sd  = new ServiceDescription();
        sd.setType(service);

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            pas =  DFService.search(this, dfd);
            System.out.println("Pocet agentov poskytujucich sluzbu: "
                + pas.length);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
