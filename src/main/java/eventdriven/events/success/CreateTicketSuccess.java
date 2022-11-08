package eventdriven.events.success;

import eventdriven.annotations.Success;
import eventdriven.entity.Ticket;
import eventdriven.enums.Status;
import eventdriven.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Success
public class CreateTicketSuccess {

    private final TicketService ticketService;

    @Autowired
    public CreateTicketSuccess(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @Transactional
    public Ticket CreateTicketSuccessful(Long id){
        Ticket ticket = ticketService.findByTicketNo(id.toString());
        ticket.setStatus(Status.CREATED);
         return ticketService.createTicket(ticket);
    }
}
