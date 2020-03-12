package com.zferreiro.dummydatamanager.controller;

import com.zferreiro.dummydatamanager.model.dummyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller {

    private static int INT = 0;

    @Autowired private JdbcTemplate jdbcTemplate;

    private static String QUERY_FOR_POM_ID= "SELECT * FROM DTO WHERE POM_OPERATION_ID = ?";

    @GetMapping("/pom-id")
    public ResponseEntity<String> getPomId(){
        return new ResponseEntity<String>(getPOMOperationId(INT), HttpStatus.OK);
    }

    @GetMapping("/pom-id/{pom-id}")
    public ResponseEntity<Object> getJsonFromBDD(@PathVariable("pom-id") String pomId){
        Map<String, Object> list =null;
//                jdbcTemplate.queryF(QUERY_FOR_POM_ID,pomId);
        Object dto = !list.isEmpty()?  list:null;
        return new ResponseEntity<>(dto != null ? dto.toString() : null,HttpStatus.OK);
    }


    public String getPOMOperationId(int id) {
        Integer uno = id;
        Integer dos = id+1;
        Integer tres = id+2;
        Integer cuatro = id+3;
        String ids =  uno.toString()+dos.toString()+tres.toString()+cuatro.toString();
        INT++;
        return "0000"+ids+"ABCD";
    }
}
