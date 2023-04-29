package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DAOs.Booking.BookingDaoInterface;
import com.dkit.oop.sd2.DAOs.Booking.MySqlBookingDao;
import com.dkit.oop.sd2.DAOs.Restaurant.MySqlRestaurantDao;
import com.dkit.oop.sd2.DAOs.Restaurant.RestaurantDaoInterface;
import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

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
        RestaurantDaoInterface restDao;// RESTAURANT

        BookingDaoInterface bookingDao;// BOOKING


        public ClientHandler(Socket clientSocket, int clientNumber) {
            try {
                restDao = new MySqlRestaurantDao();// RESTAURANT

                bookingDao = new MySqlBookingDao();// BOOKING


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
            // Scanner scanner = new Scanner(System.in);
            String message;
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);


                    /* ============================ TO DISPLAY ALL RESTAURANTS========================= */
                    if (message.startsWith("displayAllRestaurants")) {
                        try {
                            System.out.println();

                            displayAllRestaurantsAsJson();

                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }
                    }

                    /* ========================== TO DISPLAY RESTAURANTS BY ID========================= */
                    else if (message.startsWith("getById")) {

                        String tokens[] = message.split(" ");  // default delimiter is a space

                        int id = Integer.parseInt(tokens[1]);
                        System.out.println("In run() command=" + tokens[0] + ", id from client =" + tokens[1]);
                        String response = getRestaurantByIdAsJSON(id);

                        socketWriter.println(response); // send message to client
                    }


                    /* ============================ TO ADD A RESTAURANTS ============================ */
                    else if (message.startsWith("addRestaurant")) {
                        String[] tokens = message.split(", ");
                        if (tokens.length < 5) {
                            socketWriter.println("Invalid format.");
                            return;
                        }
                        //addRestaurant, Taco Taco, Pablo Escobar, 544991234, 4 //example to insert in the client site

                        String name = tokens[1].trim();
                        String manager = tokens[2].trim();
                        String phone = tokens[3].trim();
                        int rating = Integer.parseInt(tokens[4].trim());

                        RestaurantDTO restaurant = new RestaurantDTO();
                        restaurant.setName(name);
                        restaurant.setManager(manager);
                        restaurant.setPhone(phone);
                        restaurant.setRating(rating);
                        String response1 = addRestaurantAsJson(restaurant);

                        // send message to client
                        socketWriter.println(response1);

                    }

                    /* ============================ DELETE A RESTAURANT BY ID ============================ */
                    else if (message.startsWith("deleteRestaurant")) {

                        try {
                            System.out.println();
                            String tokens[] = message.split(" ");
                            int id = Integer.parseInt(tokens[1]);
                            String response = DeleteRestaurantAsJson(id);
                            socketWriter.println(response);// send message to client

                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }

                    }

                    /* ======================== DISPLAY ALL BOOKING ============================= */
                    else if (message.startsWith("displayAllBookings")) {
                        try {
                            System.out.println();

                            displayAllBookingsAsJson();

                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }
                    } else if (message.startsWith("getBookingById")) {

                        String tokens[] = message.split(" ");  // default delimiter is a space

                        int id = Integer.parseInt(tokens[1]);
                        System.out.println("In run() command=" + tokens[0] + ", id from client =" + tokens[1]);
                        String response1 = getBookingByIdAsJSON(id);

                        socketWriter.println(response1); // send message to client
                    }

                    /* ============================ TO ADD A BOOKING ============================ */
                    else if (message.startsWith("addBooking")) {
                        String[] tokens = message.split(", ");
                        if (tokens.length < 7) {
                            socketWriter.println("Invalid format.");
                            return;
                        }
                        // ei. addBooking, 10, felix, 898989895, 2023-11-03, 20:00:00, 2// Example to insert in the client side.

                        int restaurantId = Integer.parseInt(tokens[1].trim());
                        String customerName = tokens[2].trim();
                        String customerPhone = tokens[3].trim();
                        String bookingDate = tokens[4].trim();
                        String bookingTime = tokens[5].trim();
                        int numGuests = Integer.parseInt(tokens[6].trim());

                        BookingDTO booking = new BookingDTO();
                        booking.setRestaurant_id(restaurantId);
                        booking.setCustomer_name(customerName);
                        booking.setCustomer_phone(customerPhone);
                        booking.setBooking_date(bookingDate);
                        booking.setBooking_time(bookingTime);
                        booking.setNum_guests(numGuests);

                        String response = addBookingAsJson(booking);

                        // send message to client
                        socketWriter.println(response);

                    } else if (message.startsWith("deleteBooking")) {

                        try {
                            System.out.println();
                            String tokens[] = message.split(" ");
                            int id = Integer.parseInt(tokens[1]);
                            String response = DeleteBookingAsJson(id);
                            socketWriter.println(response);// send message to client

                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }

                    } else {
                        socketWriter.println("I'm sorry I don't understand :(");
                    }

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

        /* ============================METHOD TO DISPLAY ALL RESTAURANTS AS JSON========================= */
        public void displayAllRestaurantsAsJson() throws IOException, SQLException {
            RestaurantDaoInterface restDao = new MySqlRestaurantDao();
            List<RestaurantDTO> restaurants = restDao.findAllRestaurants();

            if (restaurants.isEmpty()) {
                System.out.println("No restaurants found in database");
                return;
            }

            JSONArray restaurantArray = new JSONArray();

            // Display restaurant data
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

            this.socketWriter.println(response.toString());

        }


        /* ===================== METHOD TO DISPLAY RESTAURANTS BY ID AS JSON========================= */

        public String getRestaurantByIdAsJSON(int id) throws IOException, SQLException {

            RestaurantDaoInterface restDao = new MySqlRestaurantDao();
            RestaurantDTO restaurant = restDao.findRestaurantById(id);

            String response = null;

            if (restaurant == null) {
                System.out.println("No restaurant found with ID: " + id + ", so, return a JSon String with empty object");
                response = "{}"; // empty object
            } else {

                JSONObject restaurantJsonObject = new JSONObject();
                restaurantJsonObject.put("id", restaurant.getId());
                restaurantJsonObject.put("name", restaurant.getName());
                restaurantJsonObject.put("manager", restaurant.getManager());
                restaurantJsonObject.put("phone", restaurant.getPhone());
                restaurantJsonObject.put("rating", restaurant.getRating());


                response = restaurantJsonObject.toString();

            }

            return response;  // which is JSON String format
        }

        /* ============================METHOD TO ADD A RESTAURANT AS JSON ============================ */
        public String addRestaurantAsJson(RestaurantDTO restaurantDTO) throws IOException, SQLException {

            RestaurantDaoInterface restDao = new MySqlRestaurantDao();
            RestaurantDTO restaurants = restDao.insertRestaurant(restaurantDTO);
            JSONObject restaurantJsonObject = new JSONObject();
            restaurantJsonObject.put("id", restaurants.getId());
            restaurantJsonObject.put("name", restaurants.getName());
            restaurantJsonObject.put("manager", restaurants.getManager());
            restaurantJsonObject.put("phone", restaurants.getPhone());
            restaurantJsonObject.put("rating", restaurants.getRating());
            return restaurantJsonObject.toString();  // which is JSON String format

        }

        /* ============================ DELETE A RESTAURANT BY ID AS JSON============================ */
        public String DeleteRestaurantAsJson(int id) throws IOException, SQLException {

            RestaurantDaoInterface restDao = new MySqlRestaurantDao();
            JSONObject restaurantJsonObject = new JSONObject();
            String response;
            System.out.println();
            if (restDao.deleteRestaurantById(id)) {
                restaurantJsonObject.put("id", id);
                response = String.valueOf(restaurantJsonObject.put("message", "The restaurant with the Id " + id + " was deleted!"));
            } else {
                restaurantJsonObject.put("id", id);
                response = String.valueOf(restaurantJsonObject.put("message", "There was no restaurant for the id you specified"));
            }

            return response;
        }

        /* ============================METHOD TO DISPLAY ALL BOOKING AS JSON========================= */
        public void displayAllBookingsAsJson() throws IOException, SQLException {
            BookingDaoInterface bookingDao = new MySqlBookingDao();
            List<BookingDTO> booking = bookingDao.findAllBookingsWithRestaurantNames();

            if (booking.isEmpty()) {
                System.out.println("No booking found in database");
                return;
            }

            JSONArray bookingArray = new JSONArray();

            // Display restaurant data
            for (BookingDTO bookings : booking) {
                JSONObject bookingJson = new JSONObject();
                bookingJson.put("booking_id", bookings.getBooking_id());
                bookingJson.put("restaurant_id", bookings.getRestaurant_id());
                bookingJson.put("customer_name", bookings.getCustomer_name());
                bookingJson.put("customer_phone", bookings.getCustomer_phone());
                bookingJson.put("booking_date", bookings.getBooking_date());
                bookingJson.put("booking_time", bookings.getBooking_time());
                bookingJson.put("num_guests", bookings.getNum_guests());
                bookingArray.put(bookingJson);
            }

            JSONObject response = new JSONObject();
            response.put("command", "displayAllBookings");
            response.put("bookings", bookingArray);

            this.socketWriter.println(response.toString());

        }

        /* ========================METHOD TO GET A BOOKING BY ID AS JSON======================= */
        public String getBookingByIdAsJSON(int id) throws IOException, SQLException {

            BookingDaoInterface bookingDao = new MySqlBookingDao();
            BookingDTO booking = bookingDao.findBookingId(id);

            String response = null;

            if (booking == null) {
                System.out.println("No booking found with ID: " + id + ", so, return a JSon String with empty object");
                response = "{}"; // empty object
            } else {

                JSONObject bookingJsonObject = new JSONObject();
                bookingJsonObject.put("booking_id", booking.getBooking_id());
                bookingJsonObject.put("restaurant_id", booking.getRestaurant_id());
                bookingJsonObject.put("customer_name", booking.getCustomer_name());
                bookingJsonObject.put("customer_phone", booking.getCustomer_phone());
                bookingJsonObject.put("booking_date", booking.getBooking_date());
                bookingJsonObject.put("booking_time", booking.getBooking_time());
                bookingJsonObject.put("num_guests", booking.getNum_guests());

                response = bookingJsonObject.toString();

            }

            return response;  // which is JSON String format
        }

        /* ============================METHOD TO ADD A BOOKING AS JSON========================= */
        public String addBookingAsJson(BookingDTO bookingDTO) throws IOException, SQLException {
            BookingDaoInterface bookingDao = new MySqlBookingDao();
            BookingDTO booking = bookingDao.insertBooking(bookingDTO);

            JSONObject bookingJsonObject = new JSONObject();
            bookingJsonObject.put("booking_id", booking.getBooking_id());
            bookingJsonObject.put("restaurant_id", booking.getRestaurant_id());
            bookingJsonObject.put("customer_name", booking.getCustomer_name());
            bookingJsonObject.put("customer_phone", booking.getCustomer_phone());
            bookingJsonObject.put("booking_date", booking.getBooking_date());
            bookingJsonObject.put("booking_time", booking.getBooking_time());
            bookingJsonObject.put("num_guests", booking.getNum_guests());

            return bookingJsonObject.toString();  // which is JSON String format
        }

        /* ==========================METHOD TO DELETE A BOOKING AS JSON===================== */
        public String DeleteBookingAsJson(int id) throws IOException, SQLException {

            BookingDaoInterface bookingDao = new MySqlBookingDao();
            JSONObject BookingJsonObject = new JSONObject();
            String response;
            System.out.println();
            if (bookingDao.deleteBookingById(id)) {
                BookingJsonObject.put("id", id);
                response = String.valueOf(BookingJsonObject.put("message", "The booking with the Id " + id + " was deleted!"));
            } else {
                BookingJsonObject.put("id", id);
                response = String.valueOf(BookingJsonObject.put("message", "There was no booking for the id you specified"));
            }

            return response;
        }


    }
}