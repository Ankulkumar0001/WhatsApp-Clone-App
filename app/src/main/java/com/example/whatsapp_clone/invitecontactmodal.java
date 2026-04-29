package com.example.whatsapp_clone;

public class invitecontactmodal {

    String contactname;
    String  contactmobilenumber;

    public invitecontactmodal(String contactname, String contactmobilenumber) {
        this.contactname = contactname;
        this.contactmobilenumber = contactmobilenumber;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactmobilenumber() {
        return contactmobilenumber;
    }

    public void setContactmobilenumber(String contactmobilenumber) {
        this.contactmobilenumber = contactmobilenumber;
    }
}
