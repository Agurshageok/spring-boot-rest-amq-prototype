package com.zferreiro.dummydatamanager.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zferreiro.dummydatamanager.model.dummyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

@Component
public class Producer {
    @Autowired
    private Queue queue;
    @Autowired
    private JmsTemplate jms;
    @Autowired
    private ObjectMapper mapper;


    public void publish(dummyDTO msg) throws JsonProcessingException {
        String json = mapper.writeValueAsString(msg);
        jms.convertAndSend(queue, json,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setJMSCorrelationID(msg.getHeader().get("POM_OP_ID").toString());
                return message;
            }
        });
    }
}
