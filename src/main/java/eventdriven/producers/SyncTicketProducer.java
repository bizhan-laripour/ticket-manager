package eventdriven.producers;

import eventdriven.dto.ConsumerDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class SyncTicketProducer {

    @Value("${kafka.request.topic}")
    private String requestTopic;

    private final ReplyingKafkaTemplate<String, ConsumerDto, ConsumerDto> replyingKafkaTemplate;

    @Autowired
    public SyncTicketProducer(ReplyingKafkaTemplate<String , ConsumerDto , ConsumerDto> replyingKafkaTemplate){
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public ConsumerDto getAllWorkFlows() throws ExecutionException, InterruptedException {
        ConsumerDto consumerDto = new ConsumerDto();
        consumerDto.setName("getAllWorkFlows");
        ProducerRecord<String, ConsumerDto> record = new ProducerRecord<>(requestTopic, null, consumerDto.getName(), consumerDto);
        RequestReplyFuture<String, ConsumerDto, ConsumerDto> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, ConsumerDto> response = future.get();
        return response.value();
    }
}
