package com.zferreiro.dummyreceiver.controller;

import com.zferreiro.dummyreceiver.model.dummyDTO;
import org.apache.activemq.ActiveMQSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PublisherController {

@Autowired
    private Queue queue;
@Autowired
    private JmsTemplate jmsTemplate;

@Autowired
    MessageConverter jsonJmsMessageConverter;

@GetMapping("/{msg}")
    public ResponseEntity<String> publish(@PathVariable("msg") String msg){
    jmsTemplate.convertAndSend(queue, msg);
    return new ResponseEntity<>(msg, HttpStatus.OK);
}

    @GetMapping("/json/{msg}")
    @ResponseBody
    public dummyDTO publishJson(@PathVariable("msg") String msg){
    jmsTemplate.setMessageConverter(jsonJmsMessageConverter);
    dummyDTO dto = new dummyDTO();
    dto.getDetails().put("Mensaje", msg);
    dto.getHeader().put("TimeStamp", LocalDateTime.now().toString());

        jmsTemplate.convertAndSend(queue, dto);
    return dto;
    }

}
