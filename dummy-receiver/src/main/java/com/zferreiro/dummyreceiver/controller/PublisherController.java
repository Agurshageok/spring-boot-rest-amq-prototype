package com.zferreiro.dummyreceiver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zferreiro.dummyreceiver.model.dummyDTO;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jms.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PublisherController {

@Autowired
    private Queue queue;
@Autowired
    private JmsTemplate jmsTemplate;
@Autowired
private RestTemplate restTemplate;
@Autowired
private Queue receiverQueue;

@Autowired
private ObjectMapper mapper;

private String POMOperationIdURL ="http://localhost:8081/pom-id";
private String GetWithPomOpIdURL ="http://localhost:8081/pom-id/%S";

@GetMapping("/{msg}")
    public ResponseEntity<String> publish(@PathVariable("msg") String msg){
    jmsTemplate.convertAndSend(queue, msg);
    return new ResponseEntity<>(msg, HttpStatus.OK);
}

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/json/{msg}")
    @ResponseBody
    public dummyDTO publishJson(@PathVariable("msg") String msg) throws JMSException, JsonProcessingException {
        dummyDTO dto = new dummyDTO();
        dto.getDetails().put("Mensaje", msg);
        dto.getHeader().put("TimeStamp", LocalDateTime.now().toString());
        String pomId = restTemplate.getForEntity(POMOperationIdURL, String.class).getBody();
        dto.getHeader().put("POM_OP_ID", pomId);
        jmsTemplate.convertAndSend(queue, dto, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setJMSCorrelationID(pomId);
                return message;
            }
        });
        dummyDTO response = mapper.readValue((String)jmsTemplate.receiveSelectedAndConvert(receiverQueue, "JMSCorrelationID='" + pomId + "'"), dummyDTO.class);
        return response;
    }

    @GetMapping("/json/{POM_OP_ID}")
    public ResponseEntity<dummyDTO> getJson(@PathVariable("POM_OP_ID") String pomId){
        return restTemplate.getForEntity(String.format(GetWithPomOpIdURL,pomId), dummyDTO.class);
     }
}
