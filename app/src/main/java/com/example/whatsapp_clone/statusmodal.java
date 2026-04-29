package com.example.whatsapp_clone;

public class statusmodal {
    int statusimage;
    int userphoto;
    String statusname;

    public statusmodal(int statusimage, int userphoto, String statusname) {
        this.statusimage = statusimage;
        this.userphoto = userphoto;
        this.statusname = statusname;
    }

    public int getStatusimage() {
        return statusimage;
    }

    public void setStatusimage(int statusimage) {
        this.statusimage = statusimage;
    }

    public int getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(int userphoto) {
        this.userphoto = userphoto;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
}
