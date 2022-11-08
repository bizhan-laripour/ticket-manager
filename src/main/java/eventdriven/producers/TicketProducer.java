package eventdriven.producers;

import eventdriven.dto.ConsumerDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.validation.constraints.NotNull;

@Component
public final class TicketProducer {

    private static final Logger logger = LoggerFactory.getLogger(TicketProducer.class);
    private final KafkaTemplate<String, ConsumerDto> kafkaTemplate;


    @Autowired
    public TicketProducer(KafkaTemplate<String, ConsumerDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ConsumerDto consumerDto) {
        String topicName = "student";
        ListenableFuture<SendResult<String, ConsumerDto>> future = kafkaTemplate.send(topicName, consumerDto);

        //This will check producer result asynchronously to avoid thread blocking
        future.addCallback(new ListenableFutureCallback<SendResult<String, ConsumerDto>>() {
            @Override
            public void onFailure(@NotNull Throwable throwable) {
                logger.error("Failed to send message", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, ConsumerDto> stringStringSendResult) {
                logger.info("Sent message successfully");
            }
        });
    }
}