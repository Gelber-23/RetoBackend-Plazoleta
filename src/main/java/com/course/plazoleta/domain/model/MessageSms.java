package com.course.plazoleta.domain.model;

public class MessageSms {

    private String phone;
    private String message;

    public MessageSms() {
    }

    public MessageSms(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
