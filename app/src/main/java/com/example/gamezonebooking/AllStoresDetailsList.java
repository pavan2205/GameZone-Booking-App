package com.example.gamezonebooking;

class AllStoresDetailsList {
     String image;
     String name;
     String Address;
     boolean openOrClose;

    AllStoresDetailsList(String image, String name, String Address, boolean openOrClose){
        this.image = image;
        this.name = name;
        this.Address = Address;
        this.openOrClose = openOrClose;
    }
}
