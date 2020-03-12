package com.zferreiro.dummydatamanager.configuration;


import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Queue;

@Configuration
public class JmsConfig {

    @Value("${activemq.name.toPublish}")
    private String queueName;
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }
    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jsonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
