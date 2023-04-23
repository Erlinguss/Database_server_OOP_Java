package com.dkit.oop.sd2.DAOs.Restaurant;

import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class RestaurantMethods {

    /*==========================METHOD TO DISPLAY ALL RESTAURANTS=========================*/
    public static void viewAllRestaurants(RestaurantDaoInterface restDao) throws SQLException {

        List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    /*=======================METHOD TO SEARCH RESTAURANTS BY ID =========================*/
    public static void searchRestaurantById(RestaurantDaoInterface restDao, Scanner scanner) throws SQLException {

        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        RestaurantDTO restaurant = restDao.findRestaurantById(id);
        if (restaurant != null) {
            System.out.println(restaurant);
        } else {
            System.out.println("Restaurant not found.");
        }
    }

    /*===========================METHOD TO ADD A NEW RESTAURANT=========================*/
    public static void addNewRestaurant(RestaurantDaoInterface restDao, Scanner scanner) throws SQLException {

        System.out.print("Enter restaurant name: ");
        String name = scanner.next();
        System.out.print("Enter restaurant manager: ");
        String manager = scanner.next();
        System.out.print("Enter restaurant phone number: ");
        String phone = scanner.next();
        System.out.print("Enter restaurant rating: ");
        double rating = scanner.nextDouble();
        RestaurantDTO restaurantDTO = new RestaurantDTO(0, name, manager, phone, rating);
        restaurantDTO = restDao.insertRestaurant(restaurantDTO);
        System.out.println("New restaurant was added successfully");
    }

    /*===================METHOD TO DELETE ANY RESTAURANT BY ID=========================*/
    public static void deleteRestaurantById(RestaurantDaoInterface restDao, Scanner scanner) throws SQLException {

        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        boolean restaurant = restDao.deleteRestaurantById(id);
        if (restaurant) {
            System.out.println("Restaurant deleted successfully.");
        } else {
            System.out.println("Restaurant with Id " + id + " not found.");
        }
    }


    /*=================METHOD TO SORT ALL RESTAURANTS BY RATING ========================*/
    public static void viewRestaurantsSortedByRating(RestaurantDaoInterface restDao) throws SQLException {

        List<RestaurantDTO> restaurants = restDao.findRestaurantsUsingFilter(Comparator.comparingDouble(RestaurantDTO::getRating).reversed());
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    /*=================METHOD TO SORT ALL RESTAURANTS BY NAME ========================*/
    public static void viewRestaurantsSortedByName(RestaurantDaoInterface restDao) throws SQLException {

        List<RestaurantDTO> restaurants = restDao.findRestaurantsUsingFilter(Comparator.comparing(RestaurantDTO::getName));
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    /*====================METHOD TO FIND A RESTAURANT BY NAME ========================*/
    public static void findRestaurantsByName(RestaurantDaoInterface restDao, Scanner scanner) throws SQLException {

        System.out.println("Enter the restaurant name:");
        String restaurantName = scanner.nextLine();

        try {
            List<RestaurantDTO> restaurants = restDao.findAllRestaurantContains(restaurantName);
            if (restaurants.isEmpty()) {
                throw new DaoException("No restaurants found with name " + restaurantName);
            }
            for (RestaurantDTO restaurant : restaurants) {
                System.out.println(restaurant);
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    /*========================METHOD TO UPDATE RESTAURANT PHONE ========================*/
    public static void updateRestaurantPhone(RestaurantDaoInterface restDao, Scanner scanner) throws SQLException  {

        System.out.println("Enter the name of the restaurant:");
        String restaurantName = scanner.nextLine();

        System.out.println("Enter the new phone number:");
        int newPhoneNumber = scanner.nextInt();

        try {
            restDao.updatePhone(restaurantName, newPhoneNumber);
            System.out.println("Phone number updated successfully.");
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    /*============METHOD TO DISPLAY ALL RESTAURANTS AS JSON FORMAT ====================*/
    public static String findAllRestaurantsAsJson(RestaurantDaoInterface restDao) throws SQLException {

        List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
        //Gson gson = new Gson(); Displaying the Json data in line
        Gson gson = new GsonBuilder().setPrettyPrinting().create();// Displaying the data en Json format

        try {
            return gson.toJson(restaurants);
        } catch (Exception e) {
            throw new DaoException("Error converting restaurants to JSON");
        }
    }

    /*========METHOD TO FIND A RESTAURANT BY ID AND DISPLAYED AS JSON FORMAT==========*/
    public static String findRestaurantByIdAsJson(RestaurantDaoInterface restDao, Scanner scanner) throws SQLException {

        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        RestaurantDTO restaurant = restDao.findRestaurantById(id);
        if (restaurant != null) {
            // Gson gson = new Gson(); Displaying the Json data in line
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Displaying the data as Json format
            try {
                return gson.toJson(restaurant);
            } catch (Exception e) {
                throw new DaoException("Error converting restaurant to JSON");
            }
        } else {
            return "Restaurant not found.";
        }
    }


}
