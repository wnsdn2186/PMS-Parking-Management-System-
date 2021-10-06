package com.example.pms;

public class Customer {
    String name;
    String cnum;
    String register_date;
    String pnum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public String getStart_date() {
        return register_date;
    }

    public void setStart_date(String register_date) {
        this.register_date = register_date;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }
}