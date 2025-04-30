package com.evo.product.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RabbitMQPublisherConfig {
    @Value("${rabbitmq.exchange.product}")
    private String productExchange;

    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange(productExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        // Tùy chọn bổ sung cho xác nhận message được gửi thành công
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("Message không thể gửi đến exchange:  {}", cause);
            }
        });
        return template;
    }
}
