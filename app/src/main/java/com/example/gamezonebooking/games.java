package com.example.gamezonebooking;

import android.os.Parcelable;

import java.util.ArrayList;

public class games {
    String id,name,desc,img;
    ArrayList<gamezones> gamezones;

    public games(){}

    public games(String id, String name, String desc, String img, ArrayList<gamezones> gamezones) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.gamezones = gamezones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<gamezones> getGamezone() {
        return gamezones;
    }

    public void setGamezone(ArrayList<gamezones> gamezones) {
        this.gamezones = gamezones;
    }
}
