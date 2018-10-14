package com.mituo.logservice.dto;

public class StatusDTO {

    private String status;
    private String message;
    private Object data;

    public StatusDTO(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public StatusDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
