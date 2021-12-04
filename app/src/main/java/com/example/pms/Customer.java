package com.example.pms;

public class Customer {
    int id;
    String name;
    String cnum;
    String pnum;
    String start_date;
    String end_date;
    String rdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        if(!(start_date.equals("null")))
            start_date = start_date.substring(0, start_date.indexOf(" "));
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        if(!(end_date.equals("null")))
            end_date = end_date.substring(0, end_date.indexOf(" "));
        this.end_date = end_date;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        if(!(rdate.equals("null")))
            rdate = rdate.substring(0, rdate.indexOf(" "));
        this.rdate = rdate;
    }
}