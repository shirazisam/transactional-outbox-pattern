package poc.messagerelay.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {


    @Value("${rabbitmq.exchange.outbox-exchange}")
    private String outboxExchange;

    @Getter
    @Value("${rabbitmq.queue.outbox-messaging-queue}")
    private String outboxMessagingQueue;


    @Bean
    protected Queue outboxMessagingQueue() {
        return new Queue(outboxMessagingQueue, false);
    }


    @Bean
    protected Exchange outboxExchange() {
        return new DirectExchange(outboxExchange);
    }


    @Bean
    protected RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    protected RabbitTemplate myRabbitTemplate(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin) {
        var messagingBinding = prepareBinding(outboxMessagingQueue(), outboxExchange(), outboxMessagingQueue);
        rabbitAdmin.declareBinding(messagingBinding);
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(outboxExchange);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }


    private Binding prepareBinding(Queue queue, Exchange exchange, String queueRoutingKey) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(queueRoutingKey)
                .noargs();
    }
}