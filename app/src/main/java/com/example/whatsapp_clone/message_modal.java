package com.example.whatsapp_clone;

public class message_modal {
    String email,lastmsg,messageid;
    Long msgtime;

    public message_modal(String email, String lastmsg, Long msgtime) {
        this.email = email;
        this.lastmsg = lastmsg;
        this.msgtime = msgtime;
    }

    public message_modal(String email, String lastmsg) {
        this.email = email;
        this.lastmsg = lastmsg;
    }

    public message_modal() {}

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public Long getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(Long msgtime) {
        this.msgtime = msgtime;
    }
}
