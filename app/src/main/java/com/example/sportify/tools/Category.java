package com.example.sportify.tools;

public class Category {
    private String name;
    private String Category;
    private int position;

    public Category() {
    }

    public Category(String name, String category, int position) {
        this.name = name;
        Category = category;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return Category;
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}