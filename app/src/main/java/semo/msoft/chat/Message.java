package semo.msoft.chat;

import java.util.Date;

/**
 * Created by MinwooPark on 2016-11-07.
 */

public class Message {
    private String content;
    private String sender;
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
