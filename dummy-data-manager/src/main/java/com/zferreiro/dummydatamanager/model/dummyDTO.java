package com.zferreiro.dummydatamanager.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class dummyDTO implements Serializable {
    private LinkedHashMap<String, Object> header;
    private LinkedHashMap<String, Object> details;

    public dummyDTO(LinkedHashMap<String, Object> header, LinkedHashMap<String, Object> details) {
        this.header = header;
        this.details = details;
    }

    public dummyDTO() {
        this.header = new LinkedHashMap<>();
        this.details = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Object> getHeader() {

        return header;
    }

    public void setHeader(LinkedHashMap<String, Object> header) {
        this.header = header;
    }

    public LinkedHashMap<String, Object> getDetails() {
       return details;
    }

    public void setDetails(LinkedHashMap<String, Object> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "dummyDTO{" +
                "header=" + header +
                ", details=" + details +
                '}';
    }
}
