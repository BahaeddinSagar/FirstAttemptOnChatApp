package com.example.firstattemptonchatapp;

public class Message {
    public String UID;
    public String message;
    public String timeStamp;
    public String name;

    public Message(String UID, String message, String timeStamp, String name) {
        this.UID = UID;
        this.message = message;
        this.timeStamp = timeStamp;
        this.name = name;
    }

    public Message() {
    }
}
