package com.dkit.oop.sd2.DAOs.Booking;

import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BookingMethods {

    /*============METHOD TO DISPLAY ALL BOOKING DETAILS AD IN WHICH RESTAURANT=============*/
    public static void findAllBookingsWithRestaurantNames(BookingDaoInterface BookingDao) throws SQLException {

        List<BookingDTO> bookings = BookingDao.findAllBookingsWithRestaurantNames();
        for (BookingDTO booking : bookings) {
            System.out.println(booking);
        }
    }

    /*=======================METHOD TO SEARCH RESTAURANTS BY ID =========================*/
    public static void searchBookingById(BookingDaoInterface BookingDao, Scanner scanner) throws SQLException {

        System.out.print("Enter booking ID: ");
        int id = scanner.nextInt();
        BookingDTO booking = BookingDao.findBookingId(id);
        if (booking != null) {
            System.out.println(booking);
        } else {
            System.out.println("Restaurant not found.");
        }
    }

    /*===========================METHOD TO ADD A NEW BOOKING=========================*/

    public static void addNewBooking(BookingDaoInterface bookingDao, Scanner scanner) throws SQLException {
        System.out.print("Enter restaurant id: ");
        int restaurant_id = scanner.nextInt();

        System.out.print("Enter customer name: ");
        String customer_name = scanner.next();

        System.out.print("Enter customer phone number: ");
        String customer_phone = scanner.next();

        System.out.print("Enter booking date (yyyy-mm-dd): ");// 2023-05-05
        String booking_date = scanner.next();

        System.out.print("Enter booking time (hh-mm-ss): ");// 14:30:00
        String booking_time = scanner.next();

        System.out.print("Enter number of guests: ");
        int num_guests = scanner.nextInt();

        BookingDTO bookingDTO = new BookingDTO(0, restaurant_id, null, customer_name, customer_phone, booking_date,booking_time, num_guests);
        bookingDTO = bookingDao.insertBooking(bookingDTO);

        System.out.println("New booking was added successfully");
    }

    /*===================METHOD TO DELETE ANY RESTAURANT BY ID=========================*/
    public static void deleteBookingById(BookingDaoInterface bookingDao, Scanner scanner) throws SQLException {

        System.out.print("Enter Booking ID: ");
        int id = scanner.nextInt();
        boolean booking = bookingDao.deleteBookingById(id);
        if (booking) {
            System.out.println("Booking deleted successfully.");
        } else {
            System.out.println("Booking with Id " + id + " not found.");
        }
    }

    /*=================METHOD TO SORT ALL BOOKING BY RATING ========================*/
    public static void viewBookingSortedByRating(BookingDaoInterface bookingDao) throws SQLException {

        List<BookingDTO> bookings = bookingDao.findBookingsUsingFilter((Comparator.comparing(BookingDTO::getBooking_date).reversed()));
        for (BookingDTO booking : bookings) {
            System.out.println(booking);
        }
    }

    /*=================METHOD TO SORT ALL BOOKING BY TIME ========================*/
    public static void viewBookingsSortedByTime(BookingDaoInterface bookingDao) throws SQLException {

        List<BookingDTO> bookings = bookingDao.findBookingsUsingFilter(Comparator.comparing(BookingDTO::getBooking_time));
        for (BookingDTO booking : bookings) {
            System.out.println(booking);
        }
    }

    /*=====================METHOD TO UPDATE BOOKING DATE ========================*/
    public static void updateBookingDate(BookingDaoInterface bookingDao, Scanner scanner) throws SQLException  {

        System.out.println("Enter the booking ID:");
        int bookingId = scanner.nextInt();

        System.out.println("Enter the new booking date (yyyy-MM-dd):");
        String bookingDate = scanner.next();

        try {
            bookingDao.updateBookingDate(bookingId, bookingDate);
            System.out.println("Booking date updated successfully.");

        } catch (DaoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }


    /*============METHOD TO DISPLAY ALL BOOKING AS JSON FORMAT ====================*/
    public static String findAllBookingsAsJson(BookingDaoInterface bookingDao) throws SQLException {

        List<BookingDTO> bookings = bookingDao.findAllBookingsWithRestaurantNames();
        //Gson gson = new Gson(); Displaying the Json data in line
        Gson gson = new GsonBuilder().setPrettyPrinting().create();// Displaying the data en Json format

        try {
            return gson.toJson(bookings);
        } catch (Exception e) {
            throw new DaoException("Error converting restaurants to JSON");
        }
    }

    /*========METHOD TO FIND A BOOKING BY ID AND DISPLAYED AS JSON FORMAT==========*/
    public static String findBookingByIdAsJson(BookingDaoInterface bookingDao, Scanner scanner) throws SQLException {

        System.out.print("Enter booking by ID: ");
        int id = scanner.nextInt();
        BookingDTO bookings = bookingDao.findBookingId(id);
        if (bookings != null) {
            // Gson gson = new Gson(); Displaying the Json data in line
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Displaying the data as Json format
            try {
                return gson.toJson(bookings);
            } catch (Exception e) {
                throw new DaoException("Error converting booking to JSON");
            }
        } else {
            return "Booking not found.";
        }
    }
}
