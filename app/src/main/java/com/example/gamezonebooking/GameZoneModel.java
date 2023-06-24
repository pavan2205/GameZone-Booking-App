package com.example.gamezonebooking;

import java.util.HashMap;
import java.util.Map;

public class GameZoneModel {
    String name,img_url;
    boolean status;
    Map<String,String>address=new HashMap<String,String>();

    public GameZoneModel(String name, String img_url, boolean status, Map<String, String> address) {
        this.name = name;
        this.img_url = img_url;
        this.status = status;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }
}
