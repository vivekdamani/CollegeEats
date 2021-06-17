package com.example.collegeeats.model;

public class Items {

    private String name,id,image;
    private int price;
    private boolean avail;

    public Items() {
    }

    public Items(String name, String id, String image, int price, boolean avail) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.price = price;
        this.avail = avail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvail() {
        return avail;
    }

    public void setAvail(boolean avail) {
        this.avail = avail;
    }
}
