package agents;

import behaviors.crossroad.AddCarBehaviour;
import behaviors.crossroad.CrossroadStatus;
import behaviors.crossroad.LightsBehaviour;
import behaviors.crossroad.RemoveCarBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
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

        addBehaviour(new LightsBehaviour());
        addBehaviour(new AddCarBehaviour());
        addBehaviour(new CrossroadStatus());
        addBehaviour(new RemoveCarBehaviour());

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

    public void enqueueCar(int direction, AID car) {
        switch (direction) {
            case Constants.NORTH: {
                northQueue.add(car);
                if (northQueue.size() == 1) {
                    informCar(car);
                }
                break;
            }
            case Constants.SOUTH: {
                southQueue.add(car);
                if (southQueue.size() == 1) {
                    informCar(car);
                }
                break;
            }
            case Constants.WEST: {
                westQueue.add(car);
                if (westQueue.size() == 1) {
                    informCar(car);
                }
                break;
            }
            case Constants.EAST: {
                eastQueue.add(car);
                if (eastQueue.size() == 1) {
                    informCar(car);
                }
                break;
            }
        }
    }

    public void unqueueCar(int direction, AID car) {
        switch (direction) {
            case Constants.NORTH: {
                if (car != northQueue.peek()) {
                    System.err.println("ERROR: Desync");
                    break;
                }
                northQueue.remove();
                if (!northQueue.isEmpty()) {
                    informCar(car);
                }
                break;
            }
            case Constants.SOUTH: {
                if (car != southQueue.peek()) {
                    System.err.println("ERROR: Desync");
                    break;
                }
                southQueue.remove();
                if (!southQueue.isEmpty()) {
                    informCar(car);
                }
                break;
            }
            case Constants.WEST: {
                if (car != westQueue.peek()) {
                    System.err.println("ERROR: Desync");
                    break;
                }
                westQueue.remove();
                if (!westQueue.isEmpty()) {
                    informCar(car);
                }
                break;
            }
            case Constants.EAST: {
                if (car != eastQueue.peek()) {
                    System.err.println("ERROR: Desync");
                    break;
                }
                eastQueue.remove();
                if (!eastQueue.isEmpty()) {
                    informCar(car);
                }
                break;
            }
        }
    }

    private void informCar(AID aid) {
        ACLMessage m = new ACLMessage(ACLMessage.INFORM);
        m.addReceiver(aid);
        this.send(m);
    }

    public void propageStateOne() {
        ACLMessage m = new ACLMessage(ACLMessage.PROPAGATE);
        if (!northQueue.isEmpty()) {
            m.addReceiver(northQueue.peek());
        }
        if (!southQueue.isEmpty()) {
            m.addReceiver(southQueue.peek());
        }
        this.send(m);
    }

    public void propageStateTwo() {
        ACLMessage m = new ACLMessage(ACLMessage.PROPAGATE);
        if (!eastQueue.isEmpty()) {
            m.addReceiver(eastQueue.peek());
        }
        if (!westQueue.isEmpty()) {
            m.addReceiver(westQueue.peek());
        }this.send(m);
    }
}
