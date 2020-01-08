package com.shafiq.foodrecipe.model;

public class Food {

    private String name;
    private String description;
    private String price;
    private String foodImage;

    public Food() {

    }

    public Food(String name, String description, String price, String foodImage) {
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

    public String getFoodImage() {
        return foodImage;
    }
}
