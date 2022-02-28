package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlUserDao;
import com.dkit.oop.sd2.DAOs.UserDaoInterface;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) throws SQLException {
        UserDaoInterface restDao = new MySqlUserDao();  //"IUserDao" -> "I" stands for for

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. View all restaurants");
            System.out.println("2. Search for a restaurant by ID");
            System.out.println("3. Add a new restaurant");
            System.out.println("4. Delete a restaurant by ID");
            System.out.println("5. View all restaurants sorted by rating");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewAllRestaurants();
                    break;
                case 2:
                    searchRestaurantById(scanner);
                    break;
                case 3:
                    addNewRestaurant(scanner);
                    break;
                case 4:
                    deleteRestaurantById(scanner);
                    break;
                case 5:
                    viewRestaurantsSortedByRating();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void viewAllRestaurants() throws SQLException {
        UserDaoInterface restDao = new MySqlUserDao();
        List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    private static void searchRestaurantById(Scanner scanner) throws SQLException {
        UserDaoInterface restDao = new MySqlUserDao();
        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        RestaurantDTO restaurant = restDao.findRestaurantById(id);
        if (restaurant != null) {
            System.out.println(restaurant);
        } else {
            System.out.println("Restaurant not found.");
        }
    }


    private static void addNewRestaurant(Scanner scanner) throws SQLException {
        UserDaoInterface restDao = new MySqlUserDao();
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
        System.out.println("New restaurant added with ID: " + restaurantDTO.getId());
    }

    private static void deleteRestaurantById(Scanner scanner) throws SQLException {
        UserDaoInterface restDao = new MySqlUserDao();
        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        restDao.deleteRestaurantById(id);
        System.out.println("Restaurant deleted successfully.");
    }

    private static void viewRestaurantsSortedByRating() throws SQLException {
        UserDaoInterface restDao = new MySqlUserDao();
        List<RestaurantDTO> restaurants = restDao.findRestaurantsUsingFilter(Comparator.comparingDouble(RestaurantDTO::getRating).reversed());
        for (RestaurantDTO restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

}
