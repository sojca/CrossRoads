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
import lib.Constants;
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

    private int northSemaphore;
    private int southSemaphore;
    private int eastSemaphore;
    private int westSemaphore;

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

        //addBehaviour(new LightsBehaviour());
        //addBehaviour(new AcceptCarBehaviour());
        //addBehaviour(new ReleaseCarBehaviour());
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

    public int getSemaphore(int direction) {
        switch (direction) {
            case Constants.NORTH: {
                return northSemaphore;
            }
            case Constants.SOUTH: {
                return southSemaphore;
            }
            case Constants.WEST: {
                return eastSemaphore;
            }
            default: {
                return westSemaphore;
            }
        }
    }

    public void setSemaphore(int direction, int value) {
        switch (direction) {
            case Constants.NORTH: {
                northSemaphore = value;
                break;
            }
            case Constants.SOUTH: {
                southSemaphore = value;
                break;
            }
            case Constants.WEST: {
                eastSemaphore = value;
                break;
            }
            case Constants.EAST: {
                westSemaphore = value;
                break;
            }
        }
    }

    public void enqueueCar(int direction, AID aid) {
        switch (direction) {
            case Constants.NORTH: {
                northQueue.add(aid);
                break;
            }
            case Constants.SOUTH: {
                southQueue.add(aid);
                break;
            }
            case Constants.WEST: {
                westQueue.add(aid);
                break;
            }
            case Constants.EAST: {
                eastQueue.add(aid);
                break;
            }
        }
    }

    public void unqueueCar(int direction, AID aid) {
        switch (direction) {
            case Constants.NORTH: {
                northQueue.remove();
                break;
            }
            case Constants.SOUTH: {
                southQueue.remove();
                break;
            }
            case Constants.WEST: {
                westQueue.remove();
                break;
            }
            case Constants.EAST: {
                eastQueue.remove();
                break;
            }
        }
    }
}
