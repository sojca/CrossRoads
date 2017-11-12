package agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import java.util.LinkedList;
import java.util.Queue;
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
public class CrossRoad extends Agent {

    private DFAgentDescription[] pas;
    private Queue<AID> northQueue = new LinkedList();
    private Queue<AID> eastQueue = new LinkedList();
    private Queue<AID> southQueue = new LinkedList();
    private Queue<AID> westQueue = new LinkedList();

    protected void setup() {
        System.out.println("there is crossroad");

        try {
            DFAgentDescription dfd = new DFAgentDescription();  // DF register
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("magic crossroad");
            sd.setType("crossroad");
            sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
            dfd.addServices(sd);
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg == null) {
                    block();
                } else {
                    AID sender;
                    sender = msg.getSender();

                    if (msg.getPerformative() == ACLMessage.INFORM) {
                        System.out.println("prislo auto: " + sender.getName() + " " + msg.getContent());
                        switch (Integer.parseInt(msg.getContent())) {
                            case NORTH: {
                                northQueue.add(sender);
                                break;
                            }
                            case SOUTH: {
                                southQueue.add(sender);
                                break;
                            }
                            case EAST: {
                                eastQueue.add(sender);
                                break;
                            }
                            case WEST: {
                                westQueue.add(sender);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    protected void getService(String service) {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(service);

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            pas = DFService.search(this, dfd);
            System.out.println("Pocet agentov poskytujucich sluzbu: "
                    + pas.length);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
