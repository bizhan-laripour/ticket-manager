package eventdriven.events.fail;

import eventdriven.annotations.Failure;
import eventdriven.entity.Ticket;
import eventdriven.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Failure
public class CreateTicketFailure {

    private final TicketService ticketService;

    @Autowired
    public CreateTicketFailure(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @Transactional
    public Ticket createTicketFailed(Long id){
        Ticket ticket = ticketService.findByTicketNo(id.toString());
        ticketService.deleteById(ticket);
        return ticket;
    }

}
