
package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true)    // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection,
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }


    public class ClientHandler implements Runnable { // each ClientHandler communicates with one Client
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;
        UserDaoInterface restDao;

        public ClientHandler(Socket clientSocket, int clientNumber) {
            try {
                restDao = new MySqlUserDao();

                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber; // ID number that we are assigning to this client

                this.socket = clientSocket; // store socket ref for closing

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String message;
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    if (message.startsWith("Time")) {
                        LocalTime time = LocalTime.now();
                        socketWriter.println(time); // sends current time to client

                    } else if (message.startsWith("Echo")) {
                        message = message.substring(5); // strip off the 'Echo ' part
                        socketWriter.println(message); // send message to client

                    }

                    /* ========================== TO DISPLAY ALL RESTAURANTS========================= */
//                    else if (message.startsWith("Find restaurant"))
//                    {
//                        LocalTime time =  LocalTime.now();
//                        // sends current time to client
//                        try {
//                            List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
//                            System.out.println(restaurants);
//                            //Gson gson = new Gson();
//                            socketWriter.println(restaurants);
//
//                        } catch (SQLException exception) {
//                            throw new RuntimeException(exception);
//                        }
//                    }

                    //====================================working perfectly====================
                    else if (message.startsWith("Find restaurant")) {
                        try {
                            List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
                            System.out.println(restaurants);
                            //Gson gson = new Gson();
                            //displayRestaurantById();
                            socketWriter.println(restaurants);

                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }
                    }

                    /* ========================== TO DISPLAY RESTAURANTS BY ID========================= */
                    else if (message.startsWith("Find restaurant by id")) {
                        int id = 0;
                        // = Integer.parseInt(message.substring(22));
                        displayRestaurantById( id);


                    } else {
                        socketWriter.println("I'm sorry I don't understand :(");
                    }
                    // socket.close();

                }

            } catch (IOException | SQLException exception) {
                throw new RuntimeException(exception);
            }

            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Server: (ClientHandler): Exception: " + e);
            }
        }


        public void displayRestaurantById(int id) throws IOException, SQLException {
            Scanner input = new Scanner(System.in);
           System.out.print("Enter the ID of the restaurant to display: ");
            id = input.nextInt();

            UserDaoInterface restDao = new MySqlUserDao();
            List<RestaurantDTO> restaurants = (List<RestaurantDTO>) restDao.findRestaurantById(id);

            if(restaurants.isEmpty()) {
                System.out.println("No restaurant found with ID: " + id);
                return;
            }

            RestaurantDTO restaurant = restaurants.get(0);

            JSONObject request = new JSONObject();
            request.put("command", "getById");
            request.put("id", id);

            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            JSONObject restaurantJson = new JSONObject(response);
            int restaurantId = restaurantJson.getInt("id");
            String name = restaurantJson.getString("name");
            String manager = restaurantJson.getString("manager");
            String phone = restaurantJson.getString("phone");
            double rating = restaurantJson.getDouble("rating");

           restaurant.setId(restaurantId);
            restaurant.setName(name);
            restaurant.setManager(manager);
            restaurant.setPhone(phone);
            restaurant.setRating(rating);

            System.out.println("Restaurant details:");
            System.out.println(restaurant.toString());
        }

    }
}
