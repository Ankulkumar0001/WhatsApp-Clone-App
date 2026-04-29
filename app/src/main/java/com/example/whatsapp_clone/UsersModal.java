package com.example.whatsapp_clone;

public class UsersModal {
    String name;
    String email;
    String password;
    String imageurl;
    String msgtime;
    String lastmsg;
    String mobile;
    String about;
    String link;


    public UsersModal(){}

    public UsersModal(String name,String email,String password,String imageurl,String msgtime,String lastmsg,String mobile,String about,String link) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageurl = imageurl;
        this.msgtime = msgtime;
        this.lastmsg = lastmsg;
        this.mobile = mobile;
        this.about =  about;
        this.link =  link;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
