package com.donor.oncall;

/**
 * Created by prashanth on 4/4/16.
 */
public class RequestItem {

    private String name,gender,time,adressInfo,bloodgrp,age,units;

    public RequestItem() {
    }

    public RequestItem(String name,String gender,String time,String adressInfo,String bloodgrp,String age,String units) {
        this.name = name;
        this.gender = gender;
        this.time = time;
        this.adressInfo = adressInfo;
        this.bloodgrp = bloodgrp;
        this.age = age;
        this.units = units;
    }

    public String getName() {
        return "Mr."+ name;
    }


    public String getTime() {
        return time;
    }
    public String getGender() {
        return gender;
    }
    public String getAdressInfo() {
        return adressInfo;
    }
    public String getBloodgrp() {
        return bloodgrp;
    }
    public String getAge() {
        return age;
    }
    public String getUnits() {
        return units;
    }
}
