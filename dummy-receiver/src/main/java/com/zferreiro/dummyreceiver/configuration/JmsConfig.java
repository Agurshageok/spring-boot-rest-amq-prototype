package com.zferreiro.dummyreceiver.configuration;



import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class JmsConfig {

    @Value("${activemq.test.queue}")
    private String QueueName;
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(QueueName);
    }

//    @Bean
//    public JmsTemplate jmsTemplateCustom(){
//        JmsTemplate jms = new JmsAutoConfiguration();
//        jms.setMessageConverter(jsonJmsMessageConverter());
//        jms.setConnectionFactory(JmsAutoConfiguration);
//        return jms;
//    }



    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jsonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
