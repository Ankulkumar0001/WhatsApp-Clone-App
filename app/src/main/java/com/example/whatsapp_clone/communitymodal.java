package com.example.whatsapp_clone;

public class communitymodal {

    int communityimage;
    String  communityname;
    String communitylastmsg;
    String communitychattime;

    public communitymodal(int communityimage, String communityname, String communitylastmsg, String communitychattime) {
        this.communityimage = communityimage;
        this.communityname = communityname;
        this.communitylastmsg = communitylastmsg;
        this.communitychattime = communitychattime;
    }


    public int getCommunityimage() {
        return communityimage;
    }

    public void setCommunityimage(int communityimage) {
        this.communityimage = communityimage;
    }

    public String getCommunityname() {
        return communityname;
    }

    public void setCommunityname(String communityname) {
        this.communityname = communityname;
    }

    public String getCommunitylastmsg() {
        return communitylastmsg;
    }

    public void setCommunitylastmsg(String communitylastmsg) {
        this.communitylastmsg = communitylastmsg;
    }

    public String getCommunitychattime() {
        return communitychattime;
    }

    public void setCommunitychattime(String communitychattime) {
        this.communitychattime = communitychattime;
    }
}
