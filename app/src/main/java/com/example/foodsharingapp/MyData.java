package com.example.foodsharingapp;

public class MyData {

    private String description;
    private String title;
    private String image;
    private String logo;
    private String price;
    private String time;
    private String type;
    private String image2;

    public MyData(String description, String title, String image, String logo, String price, String time,String type, String image2) {
        this.description = description;
        this.title = title;
        this.image = image;
        this.logo = logo;
        this.price = price;
        this.time = time;
        this.type = type;
        this.image2 = image2;
    }

    public MyData(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
