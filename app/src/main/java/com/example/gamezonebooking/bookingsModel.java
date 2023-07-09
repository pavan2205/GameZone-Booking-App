package com.example.gamezonebooking;

import java.util.ArrayList;

public class bookingsModel {
    String storename;
    Double price;
    int consoles;
    String game;
    String consoleType;
    String date;
    String duration;
    String timeslot;
    ArrayList<String>screens;
    String useremail;

    bookingsModel(){}

    public bookingsModel(String storename, Double price, int consoles, String game, String consoleType, String date, String duration, String timeslot, ArrayList<String> screens,String useremail) {
        this.storename = storename;
        this.price = price;
        this.consoles = consoles;
        this.game = game;
        this.consoleType = consoleType;
        this.date = date;
        this.duration = duration;
        this.timeslot = timeslot;
        this.screens = screens;
        this.useremail=useremail;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getConsoles() {
        return consoles;
    }

    public void setConsoles(int consoles) {
        this.consoles = consoles;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getConsoleType() {
        return consoleType;
    }

    public void setConsoleType(String consoleType) {
        this.consoleType = consoleType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public ArrayList<String> getScreens() {
        return screens;
    }

    public void setScreens(ArrayList<String> screens) {
        this.screens = screens;
    }
}
