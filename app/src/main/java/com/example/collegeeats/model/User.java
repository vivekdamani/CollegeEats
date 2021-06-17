package com.example.collegeeats.model;

import java.util.List;

public class User {

    private String name,address,mobile,email,uid;
    private List<Orders> pastorders;


    public User() {
    }

    public User(String name, String address, String mobile, String email, String uid, List<Orders> pastorders) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.uid = uid;
        this.pastorders = pastorders;
    }

    public List<Orders> getPastorders() {
        return pastorders;
    }

    public void setPastorders(List<Orders> pastorders) {
        this.pastorders = pastorders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
