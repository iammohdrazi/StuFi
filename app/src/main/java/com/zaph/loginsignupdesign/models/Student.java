package com.zaph.loginsignupdesign.models;

import android.icu.lang.UCharacter;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private String phone;
    private String gender;
    private String _id;
    private String email;
    private String branch;
    private String year;
    private String attendance;
    private String eventname;
    private String eventvenue;
    private String eventcategory;
    private String eventfee;
    private String eventpayment;
    private String joiningcriteria;
    private String eventprize;
    private String eventdescription;

    public Student(String name,
                   String phone,
                   String gender,
                   String _id,
                   String email,
                   String branch,
                   String year,
                   String attendance,
                   String eventname,
                   String eventcategory,
                   String eventvenue,
                   String eventfee,
                   String eventpayment,
                   String joiningcriteria,
                   String eventprize,
                   String eventdescription) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this._id = _id;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.attendance = attendance;
        this.eventname = eventname;
        this.eventcategory = eventcategory;
        this.eventvenue = eventvenue;
        this.eventfee = eventfee;
        this.eventpayment = eventpayment;
        this.joiningcriteria = joiningcriteria;
        this.eventprize = eventprize;
        this.eventdescription = eventdescription;
    }

    public Student(String name,
                   String phone,
                   String gender,
                   String studentid,
                   String email,
                   String course,
                   String year,
                   String eventname,
                   String eventvenue,
                   String category,
                   String eventfee,
                   String eventpayment,
                   String joiningcriteria,
                   String eventprize,
                   String eventdescription) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {return gender;
    }

    void setGender() {
        this.gender = gender;
    }

    public String get_id() {
        return _id;
    }

    void set_id() {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    void setEmail() {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    void setBranch() {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    void setYear() {
        this.year = year;
    }

    String getAttendance() {
        return attendance;
    }

    void setAttendance() {
        this.attendance = attendance;
    }


    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventvenue() {
        return eventvenue;
    }

    public void setEventvenue(String eventvenue) {
        this.eventvenue = eventvenue;
    }

    public String getEventcategory() {
        return eventcategory;
    }

    public void setEventcategory(String eventcategory) {
        this.eventcategory = eventcategory;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEventfee() {
        return eventfee;
    }

    public void setEventfee(String eventfee) {
        this.eventfee = eventfee;
    }

    public String getEventpayment() {
        return eventpayment;
    }

    public void setEventpayment(String eventpayment) {
        this.eventpayment = eventpayment;
    }

    public String getJoiningcriteria() {
        return joiningcriteria;
    }

    public void setJoiningcriteria(String joiningcriteria) {
        this.joiningcriteria = joiningcriteria;
    }

    public String getEventprize() {
        return eventprize;
    }

    public void setEventprize(String eventprize) {
        this.eventprize = eventprize;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }
}
