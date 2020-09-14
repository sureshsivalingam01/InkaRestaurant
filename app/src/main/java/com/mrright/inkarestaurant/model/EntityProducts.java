package com.mrright.inkarestaurant.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class EntityProducts {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "productDescription")
    private String productDescription;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "stock")
    private int stock;

    @ColumnInfo(name = "day")
    private int day;

    @ColumnInfo(name = "night")
    private int night;

    public EntityProducts(int id, String productName, String productDescription, double price, int stock, int day, int night) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.stock = stock;
        this.day = day;
        this.night = night;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }
}