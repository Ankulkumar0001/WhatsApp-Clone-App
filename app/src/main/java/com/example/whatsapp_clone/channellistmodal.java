package com.example.whatsapp_clone;

public class channellistmodal {
    int channelimage;
    String channelname;
    String channelfollowers;

    public channellistmodal(int channelimage, String channelname, String channelfollowers) {
        this.channelimage = channelimage;
        this.channelname = channelname;
        this.channelfollowers = channelfollowers;
    }

    public int getChannelimage() {
        return channelimage;
    }

    public void setChannelimage(int channelimage) {
        this.channelimage = channelimage;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getChannelfollowers() {
        return channelfollowers;
    }

    public void setChannelfollowers(String channelfollowers) {
        this.channelfollowers = channelfollowers;
    }
}
