package com.zferreiro.dummydatamanager.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zferreiro.dummydatamanager.model.dummyDTO;
import com.zferreiro.dummydatamanager.producer.Producer;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.time.LocalDateTime;

@Component
@EnableJms
public class MessageConsumer {

    @Autowired
    private Producer producer;
    @Autowired
    private ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = "${activemq.name.tolisten}")
    public void listener(ActiveMQTextMessage msg) throws JMSException, JsonProcessingException {
        String timestamp = LocalDateTime.now().toString();
        logger.info("Message Type: {}", msg.getDataStructureType());
        logger.info("Message Received -[ {} ]-", msg.getText());
        logger.info("TimeStamp -[ {} ]-", timestamp);
        dummyDTO msg2  = mapper.readValue(msg.getText(), dummyDTO.class);
        msg2.getDetails().put("data_manager", "passed on data manager");
        msg2.getHeader().put("added_on_data_manager", "true");
        producer.publish(msg2);
    }
}
