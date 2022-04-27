package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dashboard_Data implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("mrp")
    @Expose
    private String mrp;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("subcategory")
    @Expose
    private String subcategory;

    @SerializedName("file")
    @Expose
    private String file;

    public Dashboard_Data(String id, String price,String mrp, String title, String category, String subcategory, String file) {
        this.id = id;
        this.price = price;
        this.mrp = mrp;
        this.title = title;
        this.category = category;
        this.subcategory = subcategory;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getMrp() {
        return mrp;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getFile() {
        return file;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
