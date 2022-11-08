package eventdriven;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class RestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String , Object> concurrentKafkaListenerContainerFactory(){
        return new ConcurrentKafkaListenerContainerFactory<String , Object>();
    }

    @Bean
    public NewTopic studentTopic() {
        return TopicBuilder.name("student")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic responseTopic() {
        return TopicBuilder.name("response")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic requestTopic() {
        return TopicBuilder.name("request")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic resultTopic() {
        return TopicBuilder.name("result")
                .partitions(3)
                .compact()
                .build();
    }

}
