package me.devjian.onetwopm.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devjian.onetwopm.config.RabbitMQConfig;
import me.devjian.onetwopm.dto.service.SimpleEmailDTO;
import me.devjian.onetwopm.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void receiveMessage(SimpleEmailDTO simpleEmailDTO) {
        log.info("Listening EmailConsumer Message");

        mailService.sendSimpleEmail(simpleEmailDTO.getTo(), simpleEmailDTO.getSubject(), simpleEmailDTO.getBody());
    }
}
