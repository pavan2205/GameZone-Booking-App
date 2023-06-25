package com.example.gamezonebooking;

public class gamezones {
    String name;
    String address;
    String image;
    String status;


    public gamezones(){}

    public gamezones(String name, String address, String image, String status) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.status = status;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
