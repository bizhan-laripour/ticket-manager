package eventdriven.service;

import eventdriven.dto.ConsumerDto;
import eventdriven.entity.Ticket;
import eventdriven.enums.Status;
import eventdriven.producers.SyncTicketProducer;
import eventdriven.producers.TicketProducer;
import eventdriven.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketProducer producer;

    private final SyncTicketProducer syncTicketProducer;

    @Autowired
    public TicketService(TicketRepository ticketRepository , TicketProducer producer , SyncTicketProducer syncTicketProducer){
        this.ticketRepository = ticketRepository;
        this.producer = producer;
        this.syncTicketProducer = syncTicketProducer;
    }


    public Ticket deleteById(Ticket ticket){
        ticketRepository.deleteById(ticket.getId());
        return ticket;
    }

    public Ticket findById(Long id){
        return ticketRepository.findById(id).get();
    }

    public Ticket findByTicketNo(String no){
        return ticketRepository.findByTicketNo(no);
    }

    @Transactional
    public Ticket createTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }
    @Transactional
    public Ticket saveTicket(Ticket ticket){
        ticket.setStatus(Status.UNKNOWN);
        createTicket(ticket);
        ConsumerDto consumerDto = new ConsumerDto();
        consumerDto.setName("ticket");
        consumerDto.setObject(ticket);
        producer.sendMessage(consumerDto);
        return ticket;
    }


    public ConsumerDto getWorkFlows() throws ExecutionException, InterruptedException {
       return syncTicketProducer.getAllWorkFlows();
    }





}
