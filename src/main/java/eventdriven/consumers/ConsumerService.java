package eventdriven.consumers;

import com.google.gson.Gson;
import eventdriven.dto.ConsumerDto;
import eventdriven.dto.Review;
import eventdriven.events.fail.CreateTicketFailure;
import eventdriven.events.success.CreateTicketSuccess;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ConsumerService {

    private final CreateTicketFailure createTicketFailure;

    private final CreateTicketSuccess createTicketSuccess;


    @Autowired
    public ConsumerService(CreateTicketFailure createTicketFailure, CreateTicketSuccess createTicketSuccess) {
        this.createTicketFailure = createTicketFailure;
        this.createTicketSuccess = createTicketSuccess;
    }

    @KafkaListener(topics = "result", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, ConsumerDto> review) {
        ConsumerDto consumerDto = review.value();
        Gson gson = new Gson();
        String t = gson.toJson(consumerDto.getObject());
        Review re = gson.fromJson(t , Review.class);
        if (re.getContent().equals("fail")){
            createTicketFailure.createTicketFailed(re.getId());
        }else {
            createTicketSuccess.CreateTicketSuccessful(re.getId());
        }
    }


}
