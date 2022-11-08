package eventdriven.controller;


import eventdriven.dto.ConsumerDto;
import eventdriven.entity.Ticket;
import eventdriven.enums.Status;
import eventdriven.producers.TicketProducer;
import eventdriven.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class TicketController {


    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {

        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/create-ticket", method = RequestMethod.POST)
    public void insertReview(@RequestBody Ticket ticket) {
        ticketService.saveTicket(ticket);
    }

    @RequestMapping(value = "/get-workflow", method = RequestMethod.GET)
    public ConsumerDto getAllWorkFlows() throws ExecutionException, InterruptedException {
        return ticketService.getWorkFlows();
    }
}