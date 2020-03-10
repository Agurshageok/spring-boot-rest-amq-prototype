package com.zferreiro.dummydatamanager.producer;

import com.zferreiro.dummydatamanager.model.dummyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class Producer {
    @Autowired
    private Queue queue;
    @Autowired
    private JmsTemplate jms;
    @Autowired
    private MessageConverter jsonJmsMessageConverter;

    public void publish(dummyDTO msg){
//        jms.setMessageConverter(jsonJmsMessageConverter);
        jms.convertAndSend(queue, msg);
    }
}
