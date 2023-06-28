import controllers.TicketController;
import dto.GenerateTicketRequestDto;
import dto.GenerateTicketResponse;
import models.Ticket;
import models.VehicleType;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import service.GateService;
import service.TicketService;
import service.VehicleService;
import strategies.spotassignment.RandomSpotAssignmentStrategy;
import strategies.spotassignment.SpotAssignmentStrategy;

public class Main {
    public static void main(String[] args) {
        ObjectRegistry objectRegistry = new ObjectRegistry();
        VehicleService vehicleService = new VehicleService();
        GateService gateService = new GateService();
        TicketRepository ticketRepository = new TicketRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        SpotAssignmentStrategy spotAssignmentStrategy = new RandomSpotAssignmentStrategy(parkingLotRepository);
        
        TicketService ticketService = new TicketService(
            vehicleService, gateService, ticketRepository, spotAssignmentStrategy
        );

        TicketController ticketController = new TicketController(ticketService);

        objectRegistry.register("ticketService", ticketService);

        GenerateTicketRequestDto requestDto = new GenerateTicketRequestDto();
        requestDto.setVehicleType(VehicleType.LARGE);
        requestDto.setGateId(1l);
        requestDto.setVehicleNumber("MP-04-AA-1234");

        GenerateTicketResponse generateTicketResponse = ticketController.generateTicket(requestDto);
    }
}