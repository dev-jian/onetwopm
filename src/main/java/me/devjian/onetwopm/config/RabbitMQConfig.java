package me.devjian.onetwopm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import java.util.Map;

@Slf4j
@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "email_notifications";

    public static final String EMAIL_DLQ = "email_notifications_dlq";

    public static final String EMAIL_QUEUE_EXCHANGE = "email_notifications_exchange";

    public static final String EMAIL_DLQ_EXCHANGE = "email_notifications_dlq_exchange";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true, false, false,
                Map.of("x-dead-letter-exchange", EMAIL_DLQ_EXCHANGE, "x-dead-letter-routing-key", EMAIL_DLQ));
    }

    // TODO EMAIL DLQ Listener 구현이 필요합니다
    @Bean
    public Queue emailDlq() {
        return new Queue(EMAIL_DLQ, true);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(EMAIL_QUEUE_EXCHANGE);
    }

    @Bean
    public DirectExchange emailDlqExchange() {
        return new DirectExchange(EMAIL_DLQ_EXCHANGE);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(EMAIL_QUEUE);
    }

    @Bean
    public Binding emailDlqBinding() {
        return BindingBuilder.bind(emailDlq()).to(emailDlqExchange()).with(EMAIL_DLQ);
    }

    // TODO 추후 적절한 곳으로 이동 필요
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("me.devjian.onetwopm.dto.service");
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
