package com.example.imagica;

import java.util.List;

public class User {
    private String id;
    private String username;
    private int total_photos;
    private int totoal_collections;
    private String name;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public int getTotoal_collections() {
        return totoal_collections;
    }

    public void setTotoal_collections(int totoal_collections) {
        this.totoal_collections = totoal_collections;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", total_photos=" + total_photos +
                ", totoal_collections=" + totoal_collections +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
