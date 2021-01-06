package com.example.sportify.ui;

public class CartItem {
    private String title;
    private String price;
    private String quantity;
    private String image_url;
    private String total;

    /*
    empty constructor
     */
    public CartItem() {
    }

    public CartItem(String title, String price, String quantity, String image_url, String total) {
        this.image_url = image_url;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}