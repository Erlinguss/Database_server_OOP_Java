package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.Booking.BookingDaoInterface;
import com.dkit.oop.sd2.DAOs.Booking.BookingMethods;
import com.dkit.oop.sd2.DAOs.Booking.MySqlBookingDao;
import com.dkit.oop.sd2.DAOs.Restaurant.MySqlRestaurantDao;
import com.dkit.oop.sd2.DAOs.Restaurant.RestaurantDaoInterface;
import com.dkit.oop.sd2.DAOs.Restaurant.RestaurantMethods;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class App {
    private static Set<Integer> IdCache = new HashSet<>();

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
                        + "║ 6. View restaurants sorted by name   ║\n"
                        + "║ 7. Find restaurants by specific name ║\n"
                        + "║ 8. Update restaurant phone           ║\n"
                        + "║ 9. Find all restaurants as JSON      ║\n"
                        + "║ 10. Find a restaurant by ID as JSON  ║\n"
                        + "║ 11. Return to main menu              ║\n"
                        + "╚══════════════════════════════════════╝";

        final int AllRestaurant = 1;
        final int Restaurant_by_ID = 2;
        final int AddRestaurant = 3;
        final int DeleteRestaurant = 4;
        final int View_restaurants_sorted_by_rating = 5;
        final int  View_restaurants_sorted_by_name= 6;
        final int find_restaurants_by_specific_name=7;
        final int  Update_restaurant_phone = 8;
        final int Find_all_restaurants_as_JSON = 9;
        final int Find_a_restaurant_by_ID_as_JSON = 10;
        final int Exit = 11;

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
                        RestaurantMethods.viewAllRestaurants(restDao);
                        System.out.println();
                        break;

                    case Restaurant_by_ID:
                        RestaurantMethods.searchRestaurantById(restDao, scanner);
                        System.out.println();
                        break;

                    case AddRestaurant:
                        RestaurantMethods.addNewRestaurant(restDao, scanner);
                        System.out.println();
                        break;

                    case DeleteRestaurant:
                        RestaurantMethods.deleteRestaurantById(restDao, scanner);
                        System.out.println();
                        break;

                    case View_restaurants_sorted_by_rating:
                        RestaurantMethods.viewRestaurantsSortedByRating(restDao);
                        System.out.println();
                        break;

                    case View_restaurants_sorted_by_name:
                        RestaurantMethods.viewRestaurantsSortedByName(restDao);
                        System.out.println();
                        break;
                    case find_restaurants_by_specific_name:
                        RestaurantMethods.findRestaurantsByName(restDao, scanner);
                        System.out.println();
                        break;

                    case Update_restaurant_phone:
                        RestaurantMethods.updateRestaurantPhone(restDao, scanner);
                        System.out.println();
                        break;

                    case Find_all_restaurants_as_JSON:
                        System.out.println( RestaurantMethods.findAllRestaurantsAsJson(restDao));
                        System.out.println();
                        break;

                    case Find_a_restaurant_by_ID_as_JSON:
                        System.out.println( RestaurantMethods.findRestaurantByIdAsJson(restDao, scanner));
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
                        + "║ 6. View bookings sorted by time      ║\n"
                        + "║ 7. Update bookings by date           ║\n"
                        + "║ 8. Find all booking as JSON          ║\n"
                        + "║ 9. Find a booking by ID as JSON      ║\n"
                        + "║10. Return to main menu               ║\n"
                        + "╚══════════════════════════════════════╝";

        final int View_all_bookings = 1;
        final int Booking_by_ID = 2;
        final int Add_a_new_booking = 3;
        final int DeleteBooking = 4;
        final int View_booking_sorted_by_date = 5;
        final int View_booking_sorted_by_time = 6;
        final int Update_bookings_by_date = 7;
        final int Find_all_booking_as_JSON = 8;
        final int Find_a_booking_by_ID_as_JSON = 9;
        final int Exit = 10;


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
                        BookingMethods.findAllBookingsWithRestaurantNames(BookingDao);
                        System.out.println();

                        break;

                    case Booking_by_ID:
                        BookingMethods.searchBookingById(BookingDao,scanner);
                        System.out.println();
                        break;

                    case Add_a_new_booking:
                        BookingMethods.addNewBooking(BookingDao,scanner);
                         System.out.println();
                        break;

                    case DeleteBooking:
                        BookingMethods.deleteBookingById(BookingDao,scanner);
                        System.out.println();
                        break;

                    case View_booking_sorted_by_date:
                        BookingMethods.viewBookingSortedByDate(BookingDao);
                         System.out.println();
                        break;

                    case View_booking_sorted_by_time:
                        BookingMethods.viewBookingsSortedByTime(BookingDao);
                        System.out.println();
                        break;

                    case Update_bookings_by_date:
                        BookingMethods.updateBookingDate( BookingDao, scanner);
                        System.out.println();
                        break;

                      case Find_all_booking_as_JSON:
                        System.out.println(BookingMethods.findAllBookingsAsJson(BookingDao));
                        System.out.println();
                        break;

                    case Find_a_booking_by_ID_as_JSON:
                        System.out.println(BookingMethods.findBookingByIdAsJson(BookingDao, scanner));
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

}