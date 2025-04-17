package com.example.chatnest;

import android.provider.ContactsContract;

import java.util.Date;

public class MessageResponse {
    private String message;
    private MessageRequest data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageRequest getData() {
        return data;
    }

    public void setData(MessageRequest data) {
        this.data = data;
    }
}
