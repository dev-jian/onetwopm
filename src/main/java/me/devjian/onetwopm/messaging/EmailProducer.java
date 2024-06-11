package me.devjian.onetwopm.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devjian.onetwopm.config.RabbitMQConfig;
import me.devjian.onetwopm.dto.service.SimpleEmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(SimpleEmailDTO simpleEmailDTO) {
        log.info("Producing EmailQueue Message");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_QUEUE, simpleEmailDTO);
    }
}
