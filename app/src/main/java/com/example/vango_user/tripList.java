package com.example.vango_user;

public class tripList {
    private String start;
    private String destination;
    private String firstTrip;
    private String lastTrip;
    private int price;

    public tripList() {

    }

    public tripList(String start, String destination, String firstTrip, String lastTrip, int price){
        this.start = start;
        this.destination = destination;
        this.firstTrip = firstTrip;
        this.lastTrip = lastTrip;
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public String getFirstTrip() {
        return firstTrip;
    }

    public String getLastTrip() {
        return lastTrip;
    }

    public int getPrice() {
        return price;
    }
}
