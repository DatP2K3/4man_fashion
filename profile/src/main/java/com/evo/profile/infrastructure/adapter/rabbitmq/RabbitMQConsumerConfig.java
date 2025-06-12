package com.evo.profile.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConsumerConfig {
    @Value("${rabbitmq.exchange.cashback}")
    private String cashbackExchange;

    @Value("${rabbitmq.queue.cashback.earn}")
    private String cashBackEarnQueue;

    @Value("${rabbitmq.queue.cashback.use}")
    private String cashBackUseQueue;

    @Value("${rabbitmq.queue.cashback.dlq}")
    private String cashbackDeadQueue;

    @Value("${rabbitmq.routing.cashback.earn}")
    private String cashbackEarnRoutingKey;

    @Value("${rabbitmq.routing.cashback.use}")
    private String cashbackUseRoutingKey;

    @Value("${rabbitmq.exchange.cashback-dlx}")
    private String deadLetterExchange;

    @Value("${rabbitmq.routing.cashback.dlk}")
    private String deadLetterRoutingKey;

    @Bean
    public DirectExchange cashbackExchange() {
        return new DirectExchange(cashbackExchange);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(cashbackDeadQueue).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterRoutingKey);
    }

    @Bean
    public Queue cashBackEarnQueue() {
        return QueueBuilder.durable(cashBackEarnQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Binding cashBackEarnBinding() {
        return BindingBuilder.bind(cashBackEarnQueue()).to(cashbackExchange()).with(cashbackEarnRoutingKey);
    }

    @Bean
    public Queue cashBackUseQueue() {
        return QueueBuilder.durable(cashBackUseQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Binding cashBackUseBinding() {
        return BindingBuilder.bind(cashBackUseQueue()).to(cashbackExchange()).with(cashbackUseRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 1 giây
        backOffPolicy.setMultiplier(2.0); // Tăng gấp đôi
        backOffPolicy.setMaxInterval(10000); // Tối đa 10 giây
        retryTemplate.setBackOffPolicy(backOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3); // Tối đa 3 lần
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setRetryTemplate(retryTemplate());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}
