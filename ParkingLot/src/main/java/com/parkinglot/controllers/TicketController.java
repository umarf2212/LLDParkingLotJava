package controllers;

import dto.GenerateTicketRequestDto;
import dto.GenerateTicketResponse;
import dto.ResponseStatus;
import exceptions.SpotNotFoundException;
import models.Ticket;
import service.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // 1. Get Vehicle
    // 2. Generate Ticket

    // Ticket Service - Generate Ticket
    // Vehicle Service - Get Vehicle by Number and Create Vehicle
    // assign Spot
    // createTicketandStoreInDB

    public GenerateTicketResponse generateTicket(GenerateTicketRequestDto generateTicketRequestDto) {
        try {
            Ticket ticket = ticketService.generateTicket(
                generateTicketRequestDto.getVehicleNumber(),
                generateTicketRequestDto.getVehicleType(),
                generateTicketRequestDto.getGateId()
            );

            GenerateTicketResponse generateTicketResponse = new GenerateTicketResponse();
            generateTicketResponse.setTicket(ticket);
            generateTicketResponse.setResponseStatus((ResponseStatus.SUCCESS));
            return generateTicketResponse;
        } catch (SpotNotFoundException e) {
            GenerateTicketResponse generateTicketResponse = new GenerateTicketResponse();
            generateTicketResponse.setResponseStatus(ResponseStatus.FAILURE);
            return generateTicketResponse;
        }
    }
}
