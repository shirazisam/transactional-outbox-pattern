package poc.messagerelay.infrastructure.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import poc.messagerelay.config.RabbitMqConfig;
import poc.messagerelay.domain.data.OutboxDto;

@Component
@Slf4j
public class MandatePublisher {

    @Autowired
    @Qualifier("myRabbitTemplate")
    RabbitTemplate template;

    @Autowired
    private RabbitMqConfig rabbitMqConfig;


    public void publishMessage(OutboxDto dto){
        template.convertAndSend(rabbitMqConfig.getOutboxMessagingQueue(), dto);
        if ("abort".equals(dto.getOperation())) {
            throw new RuntimeException();  // simulate crash after publish
        }
        log.info("{} published.", dto);
     }
}
