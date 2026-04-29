package com.example.whatsapp_clone;

public class callslogmodal {
    int calluserimage;

    String callsusername;
    String callstime;

    public callslogmodal(int calluserimage, String callsusername, String callstime) {
        this.calluserimage = calluserimage;
        this.callsusername = callsusername;
        this.callstime = callstime;
    }

    public int getCalluserimage() {
        return calluserimage;
    }

    public void setCalluserimage(int calluserimage) {
        this.calluserimage = calluserimage;
    }

    public String getCallsusername() {
        return callsusername;
    }

    public void setCallsusername(String callsusername) {
        this.callsusername = callsusername;
    }

    public String getCallstime() {
        return callstime;
    }

    public void setCallstime(String callstime) {
        this.callstime = callstime;
    }
}
