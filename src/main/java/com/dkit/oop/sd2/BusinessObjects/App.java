package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlBookingDao;
import com.dkit.oop.sd2.DAOs.MySqlRestaurantDao;
import com.dkit.oop.sd2.DAOs.RestaurantDaoInterface;
import com.dkit.oop.sd2.DAOs.BookingDaoInterface;
import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class App {
    private static Set<Integer> cache = new HashSet<>(); // Feature 6

    public static void main(String[] args) throws SQLException, IOException {


        App Menu = new App();
        Menu.start();

    }

    private void start() throws IOException {

        DisplayMenuSystem();

    }

    private void DisplayMenuSystem() {


        final String MenuDriver =

                          "╔══════════════════════════════════════╗\n"
                        + "║               Menu System            ║\n"
                        + "╠══════════════════════════════════════╣\n"
                        + "║        Please select an option:      ║\n"
                        + "║ 1. Restaurant Methods                ║\n"
                        + "║ 2. Booking Methods                   ║\n"
                        + "║ 3. Exit                              ║\n"
                        + "╚══════════════════════════════════════╝";


        final int Restaurant = 1;
        final int Booking = 2;
        final int Exit = 3;

        Scanner scanner = new Scanner(System.in);

        int option = 0;
        do {
            System.out.println("\n" + MenuDriver);
            System.out.println("Please enter your choice:");

            try {
                String input = scanner.nextLine();
                option = Integer.parseInt(input);

                switch (option) {
                    case Restaurant:
                        displayRestaurantMenu();
                        break;

                    case Booking:
                        displayBookingMenu();
                        break;


                    case Exit:// END OF THE MANU DRIVEN.
                        System.out.println("End of the application");
                        break;
                    default:
                        System.out.println("Incorrect input. Please re-enter the option");
                        break;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Incorrect input. Please re-enter the option");
            }

        } while (option != Exit);
    }


    private void displayRestaurantMenu() {

        RestaurantDaoInterface restDao = new MySqlRestaurantDao();

        final String MenuRestaurant =

                          "╔══════════════════════════════════════╗\n"
                        + "║         Restaurant Methods           ║\n"
                        + "╠══════════════════════════════════════╣\n"
                        + "║        Please select an option:      ║\n"
                        + "║ 1. View all restaurants              ║\n"
                        + "║ 2. Search for a restaurant by ID     ║\n"
                        + "║ 3. Add a new restaurant              ║\n"
                        + "║ 4. Delete a restaurant by ID         ║\n"
                        + "║ 5. View restaurants sorted by rating ║\n"
                        + "║ 6. Find all restaurants as JSON      ║\n"
                        + "║ 7. Find a restaurant by ID as JSON   ║\n"
                        + "║ 8. Return to main menu               ║\n"
                        + "╚══════════════════════════════════════╝";

        final int AllRestaurant = 1;
        final int Restaurant_by_ID = 2;
        final int AddRestaurant = 3;
        final int DeleteRestaurant = 4;
        final int View_restaurants_sorted_by_rating = 5;
        final int Find_all_restaurants_as_JSON = 6;
        final int Find_a_restaurant_by_ID_as_JSON = 7;
        final int Exit = 8;

        /*===========================MENU RESTAURANT==================================*/
        int option = 0;
        do {
            System.out.println("\n" + MenuRestaurant);

            /*========================= ACCEPT USER INPUT ============================*/
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your choice:");

            try {
                String value = scanner.nextLine();
                option = Integer.parseInt(value);
                // option = keyboard.nextInt();
                switch (option) {

                    case AllRestaurant:
                        viewAllRestaurants(restDao);
                        System.out.println();
                        break;

                    case Restaurant_by_ID:
                        searchRestaurantById(restDao, scanner);
                        System.out.println();
                        break;

                    case AddRestaurant:
                        addNewRestaurant(restDao, scanner);
                        System.out.println();
                        break;

                    case DeleteRestaurant:
                        deleteRestaurantById(restDao, scanner);
                        System.out.println();
                        break;

                    case View_restaurants_sorted_by_rating:
                        viewRestaurantsSortedByRating(restDao);
                        System.out.println();
                        break;

                    case Find_all_restaurants_as_JSON:
                        System.out.println(findAllRestaurantsAsJson(restDao));
                        System.out.println();
                        break;

                    case Find_a_restaurant_by_ID_as_JSON:
                        System.out.println(findRestaurantByIdAsJson(restDao, scanner));
                        System.out.println();

                        break;

                    case Exit:// END OF THE MANU DRIVEN.
                        System.out.println("End of the application");
                        break;
                    default:
                        System.out.println("Incorrect input. Please re-enter the option");
                        break;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Incorrect input. Please re-enter the option");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } while (option != Exit);
    }

    private void displayBookingMenu() {

        BookingDaoInterface BookingDao = new MySqlBookingDao();

        final String MenuBooking =

                          "╔══════════════════════════════════════╗\n"
                        + "║            Booking Methods           ║\n"
                        + "╠══════════════════════════════════════╣\n"
                        + "║        Please select an option:      ║\n"
                        + "║ 1. View all bookings                 ║\n"
                        + "║ 2. Search Booking by ID              ║\n"
                        + "║ 3. Add a new booking                 ║\n"
                        + "║ 4. Delete a booking by ID            ║\n"
                        + "║ 5. View bookings sorted by date      ║\n"
                        + "║ 6. Find all booking as JSON          ║\n"
                        + "║ 7. Find a booking by ID as JSON      ║\n"
                        + "║ 8. Return to main menu               ║\n"
                        + "╚══════════════════════════════════════╝";

        final int View_all_bookings = 1;
        final int Booking_by_ID = 2;
        final int Add_a_new_booking = 3;
        final int DeleteBooking = 4;
        final int View_booking_sorted_by_date = 5;
        final int Find_all_booking_as_JSON = 6;
        final int Find_a_booking_by_ID_as_JSON = 7;
        final int Exit = 8;


        /*===============================MENU BOOKING==================================*/
        int option = 0;
        do {
            System.out.println("\n" + MenuBooking);

            /*========================= ACCEPT USER INPUT ============================*/
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter your choice:");

            try {
                String value = scanner.nextLine();
                option = Integer.parseInt(value);
                // option = keyboard.nextInt();
                switch (option) {

                    case View_all_bookings:
                        findAllBookingsWithRestaurantNames(BookingDao);
                        System.out.println();

                        break;

                    case Booking_by_ID:
                        searchBookingById(BookingDao,scanner);
                        System.out.println();
                        break;

                    case Add_a_new_booking:
                        addNewBooking(BookingDao,scanner);
                         System.out.println();
                        break;

                    case DeleteBooking:
                        deleteBookingById(BookingDao,scanner);
                        System.out.println();
                        break;

                    case View_booking_sorted_by_date:
                        viewBookingSortedByRating(BookingDao);
                         System.out.println();
                        break;

                    case Find_all_booking_as_JSON:
                        System.out.println(findAllBookingsAsJson(BookingDao));
                        System.out.println();
                        break;

                    case Find_a_booking_by_ID_as_JSON:
                        System.out.println(findBookingByIdAsJson(BookingDao, scanner));
                        System.out.println();

                        break;

                    case Exit:// END OF THE MANU DRIVEN.
                        System.out.println("End of the application");
                        break;
                    default:
                        System.out.println("Incorrect input. Please re-enter the option");
                        break;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Incorrect input. Please re-enter the option");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } while (option != Exit);
    }


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




    //=========================================================================================
    //                                   BOOKING METHODS
    //=========================================================================================

    /*==============METHOD TO DISPLAY ALL BOOKING DETAILS AD IN WHICH RESTAURANT===============*/
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

        System.out.print("Enter restaurant ID: ");
        int id = scanner.nextInt();
        boolean restaurant = bookingDao.deleteBookingById(id);
        if (restaurant) {
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