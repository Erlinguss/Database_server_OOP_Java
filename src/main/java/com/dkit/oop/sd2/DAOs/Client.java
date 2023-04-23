

package com.dkit.oop.sd2.DAOs;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
                System.out.println("║            RESTAURANT MENU           ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ Please choose an option:             ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ 1. Display all restaurants           ║");
                System.out.println("║ 2. Get restaurant by ID              ║");
                System.out.println("║ 3. Add a restaurant                  ║");
                System.out.println("║ 4. Delete a restaurant               ║");
                System.out.println("║ 5. Display all bookings              ║");
                System.out.println("║ 6. Delete booking                    ║");
                System.out.println("║ 7. Exit                              ║");
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
                        break;

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
                       // in.nextLine();
                        break;


                    /* ================================================================================ */

                    /* ================================================================================ */

                    case "5":

                    /* ========================= COMMAND TO DISPLAY ALL BOOKINGS========================= */
                    socketWriter.println("displayAllBookings");
                    String jsonString3 = socketReader.nextLine();
                    // Parse JSON response
                    JSONObject response2 = new JSONObject(jsonString3);
                    System.out.println("Client message: Response from server Time: " + jsonString3);
                    break;

                    case "6":
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

                    /* ================================================================================ */

                    /* ================================================================================ */



                    case "7":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }
}


