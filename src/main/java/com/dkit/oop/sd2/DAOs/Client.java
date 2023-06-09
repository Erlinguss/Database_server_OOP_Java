

package com.dkit.oop.sd2.DAOs;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client message: The Client is running and has connected to the server");

            String command = "";

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers

            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply


            while (!command.equalsIgnoreCase("exit")) {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║            MAIN MENU                 ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ Please choose an option:             ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ 1. Restaurant Menu                   ║");
                System.out.println("║ 2. Booking Menu                      ║");
                System.out.println("║ 3. Exit                              ║");
                System.out.println("╚══════════════════════════════════════╝");
                System.out.print("> ");

                command = in.nextLine();

                switch (command.toLowerCase()) {
                    case "1":
                        restaurantMenu(socketWriter, socketReader, in);
                        break;
                    case "2":
                        bookingMenu(socketWriter, socketReader, in);
                        break;
                    case "3":
                        command = "exit";
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            }

            socket.close();
            System.out.println("Client message: The Client has stopped running");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void restaurantMenu(PrintWriter socketWriter, Scanner socketReader, Scanner in) {

        try {
            String command = "";

            while (!command.equalsIgnoreCase("exit")) {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║            RESTAURANT MENU           ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ Please choose an option:             ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ 1. Display all restaurants           ║");
                System.out.println("║ 2. Get restaurant by ID              ║");
                System.out.println("║ 3. Add a restaurant                  ║");
                System.out.println("║ 4. Delete a restaurant               ║");
                System.out.println("║ 5. Exit                              ║");
                System.out.println("╚══════════════════════════════════════╝");
                System.out.print("> ");

                command = in.nextLine();


                switch (command.toLowerCase()) {
                    case "1":
                        /* ========================= COMMAND TO DISPLAY ALL RESTAURANTS========================= */
                        socketWriter.println("displayAllRestaurants");
                        String jsonString1 = socketReader.nextLine();
                        // Parse JSON response
                        JSONObject response1 = new JSONObject(jsonString1);
                        System.out.println("Client message: Response from server Time: " + jsonString1);
                        break;

                    case "2":
                        /* ========================= COMMAND TO DISPLAY RESTAURANTS BY ID======================= */
                        System.out.println("Please enter a Restaurant ID: ");
                        String strId = in.nextLine();

                        String request = "getById " + strId;
                        socketWriter.println(request);               // Send request to server

                        String jsonString2 = socketReader.nextLine(); // Wait for response from server
                        if (jsonString2.equals("{}"))
                            System.out.println("There was no Restaurant for the ID you specified");
                        else {
                            System.out.println(jsonString2);
                        }
                        // in.nextLine(); // Consume newline character
                        break;

                    case "3":
                        /* ===========================COMMAND TO ADD A RESTAURANTS ============================ */
                        System.out.println("Please enter restaurant details in the format: addRestaurant, Name, Manager, Phone, Rating");
                        System.out.print("> ");
                        String restaurantDetails = in.nextLine();
                        String addCommand = "addRestaurant " + restaurantDetails;
                        socketWriter.println(addCommand);              // Send request to server

                        String response = socketReader.nextLine(); // Wait for response from server
                        System.out.println(response);
                        // in.nextLine();
                        break;  //addRestaurant, Taco Taco, Pablo Escobar, 544991234, 4 //example to insert in the client site

                    case "4":
                        /* =======================COMMAND TO DELETE A RESTAURANT BY ID ====================== */
                        System.out.println("Enter ID of the restaurant to delete: ");
                        System.out.print("> ");
                        int id = Integer.parseInt(in.nextLine());
                        String deleteCommand = "deleteRestaurant " + id;
                        socketWriter.println(deleteCommand);      // Send request to server

                        String deleteResponse = socketReader.nextLine();// Wait for response from server
                        if (deleteResponse.equals("{}")) {
                            System.out.println("There is no restaurant with the specified ID.");
                        } else {
                            System.out.println("Restaurant deleted successfully!");
                        }
                        break;

                    case "5":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }


        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Incorrect input. Please re-enter the option");
        }

    }

    private void bookingMenu(PrintWriter socketWriter, Scanner socketReader, Scanner in) {

        try {

            String command = "";

            while (!command.equalsIgnoreCase("exit")) {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║            BOOKING MENU              ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ Please choose an option:             ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ 1. Display all bookings              ║");
                System.out.println("║ 2. Get booking by ID                 ║");
                System.out.println("║ 3. Add a booking                     ║");
                System.out.println("║ 4. Delete a booking                  ║");
                System.out.println("║ 5. Exit                              ║");
                System.out.println("╚══════════════════════════════════════╝");
                System.out.print("> ");

                command = in.nextLine();


                switch (command.toLowerCase()) {

                    case "1":

                        /* ========================= COMMAND TO DISPLAY ALL BOOKINGS========================= */
                        socketWriter.println("displayAllBookings");
                        String jsonString3 = socketReader.nextLine();
                        // Parse JSON response
                        JSONObject response2 = new JSONObject(jsonString3);
                        System.out.println("Client message: Response from server Time: " + jsonString3);
                        break;

                    case "2":
                        /* ========================= COMMAND TO DISPLAY RESTAURANTS BY ID======================= */
                        System.out.println("Please enter a Booking ID: ");
                        String stringId = in.nextLine();

                        String request1 = "getBookingById " + stringId;
                        socketWriter.println(request1);               // Send request to server

                        String jsonString4 = socketReader.nextLine(); // Wait for response from server
                        if (jsonString4.equals("{}"))
                            System.out.println("There was no Booking for the ID you specified");
                        else {
                            System.out.println(jsonString4);
                        }
                        // in.nextLine();
                        break;

                    case "3":
                        /* ===========================COMMAND TO ADD A BOOKING ============================ */
                        System.out.println("Please enter restaurant details in the format:addBooking, Restaurant ID, Customer Name, Customer Phone, Booking Date (YYYY-MM-DD), Booking Time (HH:MM), Number of Guests");
                        System.out.print("> ");
                        String bookingDetails = in.nextLine();
                        String addCommandBooking = "addBooking " + bookingDetails;
                        socketWriter.println(addCommandBooking);              // Send request to server

                        String responseBooking = socketReader.nextLine(); // Wait for response from server
                        System.out.println(responseBooking);
                        // in.nextLine();
                        break;   // addBooking, 10, felix, 898989895, 2023-11-03, 20:00:00, 2

                    case "4":
                        /* =======================COMMAND TO DELETE A BOOKING BY ID ====================== */
                        System.out.println("Enter ID of the booking to delete: ");
                        System.out.print("> ");
                        int bookingId = Integer.parseInt(in.nextLine());
                        String deleteCommandBooking = "deleteBooking " + bookingId;
                        socketWriter.println(deleteCommandBooking);      // Send request to server

                        String deleteResponseBooking = socketReader.nextLine();// Wait for response from server
                        if (deleteResponseBooking.equals("{}")) {
                            System.out.println("There is no booking with the specified ID.");
                        } else {
                            System.out.println("Booking deleted successfully!");
                        }
                        // in.nextLine();
                        break;


                    case "5":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }

        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Incorrect input. Please re-enter the option");
        }
    }


}












