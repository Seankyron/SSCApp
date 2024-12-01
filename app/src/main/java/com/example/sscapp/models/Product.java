package com.example.sscapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private double price;
    private String description;
    private int imageResId;
    private String status;

    public Product(String name, double price, String description, int imageResId, String status) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResId = imageResId;
        this.status = status;
    }

    protected Product(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        imageResId = in.readInt();
        status = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeInt(imageResId);
        dest.writeString(status);
    }
}
