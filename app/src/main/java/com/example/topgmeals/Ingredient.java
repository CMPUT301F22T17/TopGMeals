package com.example.topgmeals;

import java.util.Date;

public class Ingredient {
    private String description;
    private Date bestBefore;
    private String location;
    private int amount;
    private String unit;
    private String category;


    public Ingredient(String description, Date bestBefore, String location, int amount, String unit,
                      String category) {
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
