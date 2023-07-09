package com.example.gamezonebooking;

import java.util.ArrayList;

public class AdminProfileModel {

    String email;
    String storename;
    String contact;
    String address;
    String city;
    String state;
    String pincode;
    String screencount;
    String ps5count;
    String ps4count;
    String xboxcount;
    String poolcount;
    ArrayList<String>games;
    ArrayList<String>images;

    AdminProfileModel(){}

    public AdminProfileModel(String storename, String contact, String address, String city, String state, String pincode, String screencount, String ps5count, String ps4count, String xboxcount, String poolcount, ArrayList<String> games, ArrayList<String> images,String email) {
        this.storename = storename;
        this.contact = contact;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.screencount = screencount;
        this.ps5count = ps5count;
        this.ps4count = ps4count;
        this.xboxcount = xboxcount;
        this.poolcount = poolcount;
        this.games = games;
        this.images = images;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getScreencount() {
        return screencount;
    }

    public void setScreencount(String screencount) {
        this.screencount = screencount;
    }

    public String getPs5count() {
        return ps5count;
    }

    public void setPs5count(String ps5count) {
        this.ps5count = ps5count;
    }

    public String getPs4count() {
        return ps4count;
    }

    public void setPs4count(String ps4count) {
        this.ps4count = ps4count;
    }

    public String getXboxcount() {
        return xboxcount;
    }

    public void setXboxcount(String xboxcount) {
        this.xboxcount = xboxcount;
    }

    public String getPoolcount() {
        return poolcount;
    }

    public void setPoolcount(String poolcount) {
        this.poolcount = poolcount;
    }

    public ArrayList<String> getGames() {
        return games;
    }

    public void setGames(ArrayList<String> games) {
        this.games = games;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
