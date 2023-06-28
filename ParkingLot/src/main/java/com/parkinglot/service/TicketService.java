package service;

import exceptions.SpotNotFoundException;
import models.Gate;
import models.ParkingSpot;
import models.Ticket;
import models.Vehicle;
import models.VehicleType;
import repositories.TicketRepository;
import strategies.spotassignment.SpotAssignmentStrategy;

import java.util.Date;

public class TicketService {
    
    private VehicleService vehicleService;
    private GateService gateService;
    private TicketRepository ticketRepository;
    private SpotAssignmentStrategy spotAssignmentStrategy;

    public TicketService(VehicleService vehicleService, GateService gateService, TicketRepository ticketRepository, SpotAssignmentStrategy spotAssignmentStrategy) {
        this.vehicleService = vehicleService;
        this.gateService = gateService;
        this.ticketRepository = ticketRepository;
        this.spotAssignmentStrategy = spotAssignmentStrategy;
    }

    public Ticket generateTicket(String vehicleNumber, VehicleType vehicleType, long gateId) 
        throws SpotNotFoundException {

        Vehicle vehicle = vehicleService.getVehicle(vehicleNumber);
        if (vehicle == null) {
            vehicle = vehicleService.registerVehicle(vehicleNumber, vehicleType);
        }

        Gate gate = gateService.getGateById(gateId);

        Ticket ticket = new Ticket();
        ticket.setGate(gate);
        ticket.setVehicle(vehicle);
        ticket.setOperator(gate.getOperator());
        ticket.setEntryTime(new Date());

        ParkingSpot spot = spotAssignmentStrategy.assignSpot(vehicleType, gate);

        if (spot == null){
            throw new SpotNotFoundException(vehicleNumber);
        }


        ticket.setParkingSpot(spot);
        Ticket savedTicket = ticketRepository.save(ticket);

        return savedTicket;

    }

}
