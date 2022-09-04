package com.mmg.rickandmorty;

public class Characters {

    private int id;
    private String name;
    private String status;
    private String location;
    private String image;

    public Characters() {
    }

    public Characters(int id, String name, String status, String location, String image) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.location = location;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
