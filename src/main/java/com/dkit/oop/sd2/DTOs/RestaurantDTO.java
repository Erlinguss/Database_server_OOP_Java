package com.dkit.oop.sd2.DTOs;

public class RestaurantDTO {
    private int id;
    private String name;
    private String manager;
    private String phone;
    private double rating;

    public RestaurantDTO(int id, String name, String manager, String phone, double rating) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.phone = phone;
        this.rating = rating;
    }

    public RestaurantDTO()
    {
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", phone='" + phone + '\'' +
                ", rating=" + rating +
                '}';
    }


}