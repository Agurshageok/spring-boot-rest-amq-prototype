package com.zferreiro.dummydatamanager.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zferreiro.dummydatamanager.model.DTORowMapper;
import com.zferreiro.dummydatamanager.model.dummyDTO;
import com.zferreiro.dummydatamanager.producer.Producer;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@EnableJms
public class MessageConsumer {

    @Autowired
    private Producer producer;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String query = "SELECT * FROM DTO WHERE ID = ?";

    @JmsListener(destination = "${activemq.name.tolisten}")
    public void listener(ActiveMQTextMessage msg) throws JMSException, JsonProcessingException {
        String timestamp = LocalDateTime.now().toString();
        dummyDTO msg2  = mapper.readValue(msg.getText(), dummyDTO.class);
        msg2.getDetails().put("data_manager", "passed on data manager");
        msg2.getHeader().put("added_on_data_manager", "true");
        int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM DTO", Integer.class);
        int id = result +1;
        int result1 = jdbcTemplate.update("INSERT INTO DTO VALUES (?, ?, ?, ?)", id, msg2.getDetails().toString(), msg2.getHeader().toString(), getPOMOperationId(id));
        List<Map<String, Object>> result3 = jdbcTemplate.queryForList("SELECT * FROM DTO");
        Map<String, Object> result2 = jdbcTemplate.queryForMap(query, id);
        dummyDTO dtos =  jdbcTemplate.queryForObject(query,new Object[]{id},new DTORowMapper());
        logger.info("Results: {}",result3);
        logger.info("Final Result: {}",result2);
        logger.info("RowMapperResult: {}", dtos != null ? dtos.toString() : "No data found");
        producer.publish(msg2);

    }

    private String getPOMOperationId(int id) {
        Integer uno = id;
        Integer dos = id+1;
        Integer tres = id+2;
        Integer cuatro = id+3;
        String ids =  uno.toString()+dos.toString()+tres.toString()+cuatro.toString();
        return "0000"+ids+"abcd";
    }
}
