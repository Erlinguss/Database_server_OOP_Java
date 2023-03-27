package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlUserDao;
import com.dkit.oop.sd2.DAOs.UserDaoInterface;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.*;
public class App
{
    private static Set<Integer> cache = new HashSet<>(); // Feature 6
    public static void main(String[] args) throws SQLException {


        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║               Menu System            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║        Please select an option:      ║");
            System.out.println("║ 1. View all restaurants              ║");
            System.out.println("║ 2. Search for a restaurant by ID     ║");
            System.out.println("║ 3. Add a new restaurant              ║");
            System.out.println("║ 4. Delete a restaurant by ID         ║");
            System.out.println("║ 5. View restaurants sorted by rating ║");
            System.out.println("║ 6. Find all restaurants as JSON      ║");
            System.out.println("║ 7. Find a restaurant by ID as JSON   ║");
            System.out.println("║ 8. Exit                              ║");
            System.out.println("╚══════════════════════════════════════╝");

            try {
                UserDaoInterface restDao = new MySqlUserDao();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        viewAllRestaurants(restDao);
                        System.out.println();
                        break;
                    case 2:
                        searchRestaurantById(restDao, scanner);
                        System.out.println();
                        break;
                    case 3:
                        addNewRestaurant(restDao,scanner);
                        System.out.println();
                        break;
                    case 4:
                        deleteRestaurantById(restDao,scanner);
                        System.out.println();
                        break;
                    case 5:
                        viewRestaurantsSortedByRating(restDao);
                        System.out.println();
                        break;
                    case 6:
                        System.out.println(findAllRestaurantsAsJson(restDao));
                        System.out.println();
                        break;
                    case 7:
                        System.out.println(findRestaurantByIdAsJson(restDao,scanner));
                        System.out.println();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        System.out.println();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                System.out.println();
                scanner.next();
            }
        }
    }


    /*==========================METHOD TO DISPLAY ALL RESTAURANTS=========================*/
    public static void viewAllRestaurants( UserDaoInterface restDao) throws SQLException {

        List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    /*=======================METHOD TO SEARCH RESTAURANTS BY ID =========================*/
    public  static void searchRestaurantById(UserDaoInterface restDao,Scanner scanner) throws SQLException {

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
    public  static void addNewRestaurant(UserDaoInterface restDao,Scanner scanner) throws SQLException {

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
    public static void deleteRestaurantById( UserDaoInterface restDao, Scanner scanner) throws SQLException {

        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        boolean restaurant  = restDao.deleteRestaurantById(id);
        if (restaurant){
        System.out.println("Restaurant deleted successfully.");
    } else {
            System.out.println("Restaurant with Id "+id+" not found.");
        }
    }



    /*=================METHOD TO SORT ALL RESTAURANTS BY RATING ========================*/
    public static void viewRestaurantsSortedByRating(UserDaoInterface restDao) throws SQLException {

        List<RestaurantDTO> restaurants = restDao.findRestaurantsUsingFilter(Comparator.comparingDouble(RestaurantDTO::getRating).reversed());
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    /*============METHOD TO DISPLAY ALL RESTAURANTS AS JSON FORMAT ====================*/
    public static String findAllRestaurantsAsJson(UserDaoInterface restDao) throws SQLException {

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
    public static String findRestaurantByIdAsJson(UserDaoInterface restDao,Scanner scanner) throws SQLException {

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
