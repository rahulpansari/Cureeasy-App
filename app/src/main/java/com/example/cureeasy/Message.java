package com.example.cureeasy;

public class Message {
    String to;
    String from;
    String message;

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public Message()
    {

    }

    public Message(String t,String f,String m)
    {
        to=t;
        from=f;
        message=m;
    }
}
