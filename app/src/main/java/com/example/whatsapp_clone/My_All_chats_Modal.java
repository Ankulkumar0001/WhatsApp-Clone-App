package com.example.whatsapp_clone;

public class My_All_chats_Modal {

    String email;
    Boolean ispined;
    Boolean ismute;
    Boolean isblock;

    public My_All_chats_Modal(String email, Boolean ispined, Boolean ismute, Boolean isblock) {
        this.email = email;
        this.ispined = ispined;
        this.ismute = ismute;
        this.isblock = isblock;
    }
    public My_All_chats_Modal(){}



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Boolean getIspined() {
        return ispined;
    }

    public void setIspined(Boolean ispined) {
        this.ispined = ispined;
    }

    public Boolean getIsmute() {
        return ismute;
    }

    public void setIsmute(Boolean ismute) {
        this.ismute = ismute;
    }

    public Boolean getIsblock() {
        return isblock;
    }

    public void setIsblock(Boolean isblock) {
        this.isblock = isblock;
    }
}
