package eventdriven.repository;

import eventdriven.entity.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket , Long> {

    @NotNull
    Optional<Ticket> findById(@NotNull Long id);

    void deleteById(@NotNull Long id);

    Ticket findByTicketNo(String ticketNo);

}
