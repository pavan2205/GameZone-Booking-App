package com.example.gamezonebooking;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class games  {
    String name,desc,img;
    ArrayList<String> gamezones;

    public games(){}

    public games(String name, String desc, String img, ArrayList<String> gamezones) {
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.gamezones = gamezones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<String> getGamezones() {
        return gamezones;
    }

    public void setGamezones(ArrayList<String> gamezones) {
        this.gamezones = gamezones;
    }
}
