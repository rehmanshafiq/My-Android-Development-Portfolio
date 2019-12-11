package com.shafiq.foodrecipe.model;

public class Food {

    private String name;
    private String description;
    private String price;
    private int foodImage;

    public Food(String name, String description, String price, int foodImage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.foodImage = foodImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getFoodImage() {
        return foodImage;
    }
}
