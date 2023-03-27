
package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import org.json.JSONArray;
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
                    else if (message.startsWith("displayAllRestaurants")) {
                        try {
                            System.out.println();
//                            List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
//                            System.out.println(restaurants);
                            //Gson gson = new Gson();
                            displayAllRestaurants();
//                            socketWriter.println(restaurants);

                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }
                    }

                    /* ========================== TO DISPLAY RESTAURANTS BY ID========================= */
                    else if (message.startsWith("getById")) {

                        String tokens[] = message.split(" ");  // default delimiter is a space

                        // = Integer.parseInt(message.substring(22));

                        int id =  Integer.parseInt( tokens[1] );
                        System.out.println("In run() command=" + tokens[0] + ", id from client =" + tokens[1]);
                        String response = getRestaurantByIdAsJSON(id);


                        socketWriter.println(response); // send message to client

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
        public void displayAllRestaurants() throws IOException, SQLException {
            UserDaoInterface restDao = new MySqlUserDao();
            List<RestaurantDTO> restaurants = restDao.findAllRestaurants();

            if(restaurants.isEmpty()) {
                System.out.println("No restaurants found in database");
                return;
            }

            JSONArray restaurantArray = new JSONArray();

            // Display restaurant info
            for (RestaurantDTO restaurant : restaurants) {
                JSONObject restaurantJson = new JSONObject();
                restaurantJson.put("id", restaurant.getId());
                restaurantJson.put("name", restaurant.getName());
                restaurantJson.put("manager", restaurant.getManager());
                restaurantJson.put("phone", restaurant.getPhone());
                restaurantJson.put("rating", restaurant.getRating());
                restaurantArray.put(restaurantJson);

            }

            JSONObject response = new JSONObject();
            response.put("command", "displayAllRestaurants");
            response.put("restaurants", restaurantArray);

            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(response.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseMsg = in.readLine();

            System.out.println(responseMsg);
        }


        public String getRestaurantByIdAsJSON(int id) throws IOException, SQLException {
            Scanner input = new Scanner(System.in);


            UserDaoInterface restDao = new MySqlUserDao();
            RestaurantDTO restaurant = restDao.findRestaurantById(id);

            String response = null;

            if (restaurant == null) {
                System.out.println("No restaurant found with ID: " + id + ", so, return a JSon String wit empty object");
                response = "{}"; // empty object
            } else {



                JSONObject restaurantJsonObject = new JSONObject();
                restaurantJsonObject.put("id",restaurant.getId() );
                restaurantJsonObject.put("name",restaurant.getName() );
                restaurantJsonObject.put("manager",restaurant.getManager() );
                restaurantJsonObject.put("phone",restaurant.getPhone() );
                restaurantJsonObject.put("rating",restaurant.getRating() );


                response = restaurantJsonObject.toString();

//                System.out.println("Restaurant details:");
//                System.out.println(restaurant.toString());
            }

            return response;  // which is JSON String format
        }

    }
}
