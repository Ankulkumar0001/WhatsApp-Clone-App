package com.example.whatsapp_clone;

public class All_Users_Modal {

    String name;
    String lastmsg;
    String msgtime;
    String imageurl;
    String email;
    String password;
    String mobile;
    String about;
    String link;

    public All_Users_Modal() {}
    public All_Users_Modal(String name, String lastmsg, String msgtime, String userimage, String email, String password, String mobile, String about, String link) {
        this.name = name;
        this.lastmsg = lastmsg;
        this.msgtime = msgtime;
        this.imageurl = userimage;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.about = about;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }


    public String getimageurl() {
        return imageurl;
    }

    public void setimageurl(String userimageurl) {
        this.imageurl = userimageurl;
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
