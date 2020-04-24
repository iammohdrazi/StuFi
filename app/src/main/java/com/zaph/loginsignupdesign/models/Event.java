package com.zaph.loginsignupdesign.models;

import java.io.Serializable;

public class Event implements Serializable {

    private String hostname;
    private String hostphone;
    private String hostgender;
    private String hostid;
    private String hostemail;
    private String hostcourse;
    private String hostyear;
    private String eventname;
    private String eventvenue;
    private String eventcategory;
    private String eventfee;
    private String eventpayment;
    private String joiningcriteria;
    private String eventprize;
    private String eventdescription;
    private String eventdate;
    private String eventtime;


    public String getHostname() {
        return hostname;
    }

    public String getHostphone() {
        return hostphone;
    }

    public String getHostgender() {
        return hostgender;
    }

    public String getHostid() {
        return hostid;
    }

    public String getHostemail() {
        return hostemail;
    }

    public String getHostcourse() {
        return hostcourse;
    }

    public String getHostyear() {
        return hostyear;
    }

    public String getEventname() {
        return eventname;
    }

    public String getEventvenue() {
        return eventvenue;
    }

    public String getEventcategory() {
        return eventcategory;
    }

    public String getEventfee() {
        return eventfee;
    }

    public String getEventpayment() {
        return eventpayment;
    }

    public String getJoiningcriteria() {
        return joiningcriteria;
    }

    public String getEventprize() {
        return eventprize;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public String getEventdate() {
        return eventdate;
    }

    public String getEventtime() {
        return eventtime;
    }
}
