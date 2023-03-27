/**
 * CLIENT                                                  February 2021
 * <p>
 * This Client program asks the user to input commands to be sent to the server.
 * <p>
 * There are only two valid commands in the protocol: "Time" and "Echo"
 * <p>
 * If user types "Time" the server should reply with the current server time.
 * <p>
 * If the user types "Echo" followed by a message, the server will echo back the message.
 * e.g. "Echo Nice to meet you"
 * <p>
 * If the user enters any other input, the server will not understand, and
 * will send back a message to the effect.
 * <p>
 * NOte: You must run the server before running this the client.
 * (Both the server and the client will be running together on this computer)
 */
package com.dkit.oop.sd2.DAOs;

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

            System.out.println("Please enter a command:   \n>");
            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers


            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply


            /* ========================= COMMAND TO DISPLAY ALL RESTAURANTS========================= */
            if (command.startsWith("displayAllRestaurants"))   //we expect the server to return all restaurants
            {
                socketWriter.println(command);
                String JsonString = socketReader.nextLine();
                System.out.println("Client message: Response from server Time: " + JsonString);
            }


            /* ========================= COMMAND TO DISPLAY RESTAURANTS BY ID======================= */
            else if (command.startsWith("getById"))   //we expect the server to return restaurant by id
            {
                System.out.println("Please enter a Restaurant id:\n>");
                String strId = in.nextLine();

                String request = command + " " + strId;
                socketWriter.println(request);               // Send request to server

                String JsonString = socketReader.nextLine(); // Wait for response from server
                if (JsonString.equals("{}"))
                    System.out.println("There was no Restaurant for the id you specified");
                else {
                    System.out.println(JsonString);
                }
            }

            /* ===========================COMMAND TO ADD A RESTAURANTS ============================ */
            else if (command.startsWith("addRestaurant")) {
                System.out.println("Please enter restaurant details in the format: Name, Manager, Phone, Rating\n>");
                String restaurantDetails = in.nextLine();
                String request = command + " " + restaurantDetails;
                socketWriter.println(request);              // Send request to server

                String response = socketReader.nextLine(); // Wait for response from server
                System.out.println(response);
            }

            /* =======================COMMAND TO DELETE A RESTAURANT BY ID ====================== */
            else if (command.startsWith("deleteRestaurant")) {
                System.out.print("Enter ID of the restaurant to delete: ");
                int id = Integer.parseInt(in.nextLine());
                String deleteCommand = "deleteRestaurant " + id;
                socketWriter.println(deleteCommand);      // Send request to server

                String response = socketReader.nextLine();// Wait for response from server
                if (response.equals("{}")) {
                    System.out.println("There is no restaurant with the specified ID.");
                } else {
                    System.out.println("Restaurant deleted successfully!");
                }
            } else    // the user has entered a valid command or an invalid command
            {
                System.out.println("Invalid command");
            }
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }


}