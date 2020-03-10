package com.zferreiro.dummyreceiver.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zferreiro.dummyreceiver.model.dummyDTO;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import java.time.LocalDateTime;

@Component
@EnableJms
public class MessageConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = "response-queue")
    public void listener(ActiveMQTextMessage msg) throws JMSException, JsonProcessingException {
        String timestamp = LocalDateTime.now().toString();
        logger.info("Message Received -[ {} ]-", msg.getText());
        logger.info("TimeStamp -[ {} ]-", timestamp);

        ObjectMapper mapper = new ObjectMapper();
        dummyDTO dto = mapper.readValue(msg.getText(), dummyDTO.class);
        logger.info("toString de DTO: {}", dto.toString());
        logger.info("toString de DTO header: {}", dto.getHeader().toString());
        logger.info("toString de DTO details: {}", dto.getDetails().toString());

    }

}
