package com.zferreiro.dummydatamanager.model;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DTORowMapper implements RowMapper<dummyDTO> {
    @Override
    public dummyDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        dummyDTO dto = new dummyDTO();

        dto.getDetails().put("result", resultSet.getObject("DETAILS"));
        dto.getHeader().put("result", resultSet.getObject("HEADERS"));

        return dto;
    }
}
