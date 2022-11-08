package eventdriven.config;

import eventdriven.dto.ConsumerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class SyncConfig {


    @Value(value = "${kafka.groupId}")
    private String groupId;

    @Value(value = "${kafka.reply.topic}")
    private String replyTopic;

    @Bean
    public ReplyingKafkaTemplate<String, ConsumerDto, ConsumerDto> replyingKafkaTemplate(ProducerFactory<String, ConsumerDto> pf,
                                                                                    ConcurrentKafkaListenerContainerFactory<String, ConsumerDto> factory) {
        ConcurrentMessageListenerContainer<String, ConsumerDto> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, ConsumerDto> replyTemplate(ProducerFactory<String, ConsumerDto> pf,
                                                       ConcurrentKafkaListenerContainerFactory<String, ConsumerDto> factory) {
        KafkaTemplate<String, ConsumerDto> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

}
