package com.example.vango_user;

public class tripList {
    private String start;
    private String stop;
    private String firstTrip;
    private String lastTrip;
    private Double price;

    public tripList() {

    }

    public tripList(String start, String stop, String firstTrip, String lastTrip, Double price){
        this.start = start;
        this.stop = stop;
        this.firstTrip = firstTrip;
        this.lastTrip = lastTrip;
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public String getFirstTrip() {
        return firstTrip;
    }

    public String getLastTrip() {
        return lastTrip;
    }

    public Double getPrice() {
        return price;
    }
}
