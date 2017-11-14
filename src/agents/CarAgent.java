package agents;

import behaviors.car.CarBehaviour;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;

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

    private int source;
    private int destination;

    private long timestampStart = 0;
    private long timestampEnd = 0;

    private DFAgentDescription crossroad = null;

    @Override
    protected void setup() {
        Object args[] = getArguments();

        System.out.println("Auto - ARGUMENTS");
        for (Object arg : args) {
            System.out.println(arg);
        }

        getService();

        //registrateDirectionFromService();
        addBehaviour(new WakerBehaviour(this, 1000) {

            @Override
            protected void onWake() {
                addBehaviour(new CarBehaviour());
            }
        });
    }

    protected void registrateDirectionFromService() {
        try {
            DFAgentDescription dfd = new DFAgentDescription();  // DF register
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("dirrection from service");
            sd.setType("north");
            sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
            dfd.addServices(sd);
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get and save crossroad reference
     */
    protected void getService() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("crossroad");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            crossroad = DFService.search(this, dfd)[0];
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public DFAgentDescription getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(DFAgentDescription crossroad) {
        this.crossroad = crossroad;
    }
}
