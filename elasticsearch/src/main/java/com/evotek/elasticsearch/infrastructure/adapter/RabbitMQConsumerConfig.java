package com.evotek.elasticsearch.infrastructure.adapter;

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
    @Value("${rabbitmq.exchange.product}")
    private String productExchange;

    @Value("${rabbitmq.queue.product.create}")
    private String productCreateQueue;

    @Value("${rabbitmq.queue.product.update}")
    private String productUpdateQueue;

    @Value("${rabbitmq.queue.product.delete}")
    private String productDeleteQueue;

    @Value("${rabbitmq.queue.product.dlq}")
    private String productDeadQueue;

    @Value("${rabbitmq.routing.product.create}")
    private String productCreateRoutingKey;

    @Value("${rabbitmq.routing.product.update}")
    private String productUpdateRoutingKey;

    @Value("${rabbitmq.routing.product.delete}")
    private String productDeleteRoutingKey;

    @Value("${rabbitmq.exchange.product-dlx}")
    private String deadLetterExchange;

    @Value("${rabbitmq.routing.product.dlk}")
    private String deadLetterRoutingKey;

    @Value("${rabbitmq.queue.product.update-all}")
    private String productUpdateAllQueue;

    @Value("${rabbitmq.routing.product.update-all}")
    private String productUpdateAllRoutingKey;

    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange(productExchange);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(productDeadQueue).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterRoutingKey);
    }

    @Bean
    public Queue productCreateQueue() {
        return QueueBuilder.durable(productCreateQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Queue productUpdateQueue() {
        return QueueBuilder.durable(productUpdateQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Queue productUpdateAllQueue() {
        return QueueBuilder.durable(productUpdateAllQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Queue productDeleteQueue() {
        return QueueBuilder.durable(productDeleteQueue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Binding productCreateBinding() {
        return BindingBuilder.bind(productCreateQueue()).to(productExchange()).with(productCreateRoutingKey);
    }

    @Bean
    public Binding productUpdateBinding() {
        return BindingBuilder.bind(productUpdateQueue()).to(productExchange()).with(productUpdateRoutingKey);
    }

    @Bean
    public Binding productUpdateAllBinding() {
        return BindingBuilder.bind(productUpdateAllQueue())
                .to(productExchange())
                .with(productUpdateAllRoutingKey);
    }

    @Bean
    public Binding productDeleteBinding() {
        return BindingBuilder.bind(productDeleteQueue()).to(productExchange()).with(productDeleteRoutingKey);
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
