package com.example.sportify.tools;

public class CartItem {
    private String title;
    private String price;
    private String quantity;
    private String image_url;

    /*
    empty constructor
     */
    public CartItem() {
    }

    public CartItem(String title, String price, String quantity, String image_url) {
        this.image_url = image_url;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage_url() {
        return this.image_url;
    }
}