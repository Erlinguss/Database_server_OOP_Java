package com.dkit.oop.sd2.DTOs;

public class BookingDTO {

    private int booking_id;
    private int restaurant_id;
    private String restaurant_name;
    private String customer_name;
    private String customer_phone;
    private String booking_date;
    private String booking_time;
    private int num_guests;

    public BookingDTO(int booking_id, int restaurant_id, String restaurant_name,String customer_name, String customer_phone, String booking_date,String booking_time, int num_guests) {

        this.booking_id = booking_id;
        this.restaurant_id = restaurant_id;
        this.restaurant_name = restaurant_name;
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.booking_date= booking_date;
        this.booking_time= booking_time;
        this.num_guests = num_guests;
    }

    public BookingDTO()
    {
    }


    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }
    public int getNum_guests() {
        return num_guests;
    }

    public void setNum_guests(int num_guests) {
        this.num_guests = num_guests;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "booking_id=" + booking_id +
                ", restaurant_id=" + restaurant_id +
                ", restaurant_name=" + restaurant_name +
                ", customer_name='" + customer_name + '\'' +
                ", customer_phone='" + customer_phone + '\'' +
                ", booking_Date='" + booking_date + '\'' +
                ", booking_Time='" + booking_time + '\'' +
                ", num_guests=" + num_guests +
                '}';
    }
}
