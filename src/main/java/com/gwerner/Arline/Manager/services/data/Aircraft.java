package com.gwerner.Arline.Manager.services.data;


import java.time.LocalDate;



public class Aircraft {
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Integer maxRange) {
        this.maxRange = maxRange;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    private String Name;
    private Double price;
    private Integer maxRange;
    private String airline;
    private LocalDate startTime;

    public Aircraft(String name, Double price, Integer maxRange, String airline, LocalDate startTime) {
        Name = name;
        this.price = price;
        this.maxRange = maxRange;
        this.airline = airline;
        this.startTime = startTime;
    }
    public Aircraft() {
    }
}
