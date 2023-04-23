package com.dkit.oop.sd2.DAOs.Booking;

import com.dkit.oop.sd2.DAOs.MySqlDao;
import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MySqlBookingDao extends MySqlDao implements BookingDaoInterface {


    @Override
    public List<BookingDTO> findAllBookingsWithRestaurantNames() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<BookingDTO> bookings = new ArrayList<>();

        try {
            // Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT booking.booking_id, booking.restaurant_id, restaurant.name, booking.customer_name, booking.customer_phone, booking.booking_date, booking.booking_time, booking.num_guests " +
                    "FROM booking " +
                    "JOIN restaurant ON booking.restaurant_id = restaurant.id";
            ps = connection.prepareStatement(query);

            // Using a PreparedStatement to execute SQL
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int booking_id = resultSet.getInt("booking_id");
                int restaurant_id = resultSet.getInt("restaurant_id");
                String restaurant_name = resultSet.getString("name");
                String customer_name = resultSet.getString("customer_name");
                String customer_phone = resultSet.getString("customer_phone");
                String booking_date = resultSet.getString("booking_date");
                String booking_time = resultSet.getString("booking_time");
                int num_guests = resultSet.getInt("num_guests");
               BookingDTO booking = new BookingDTO(booking_id, restaurant_id, restaurant_name, customer_name, customer_phone, booking_date, booking_time, num_guests);
               // BookingDTO booking = new BookingDTO(booking_id, restaurant_id, customer_name, customer_phone, booking_date, num_guests);

                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllBookingsWithRestaurantNames() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllBookingsWithRestaurantNames() " + e.getMessage());
            }
        }
        return bookings; // may be empty
    }

    @Override
    public BookingDTO findBookingId(int id) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        BookingDTO booking = null;
        try {
            connection = this.getConnection();
            String query = "SELECT booking.booking_id, booking.restaurant_id, restaurant.name, booking.customer_name, booking.customer_phone, booking.booking_date, booking.booking_time, booking.num_guests " +
                    "FROM booking " +
                    "INNER JOIN restaurant " +
                    "ON booking.restaurant_id = restaurant.id " +
                    "WHERE booking.booking_id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                 id = resultSet.getInt("booking_id");
                int restaurant_id = resultSet.getInt("restaurant_id");
                String restaurant_name = resultSet.getString("name");
                String customer_name= resultSet.getString("customer_name");
                String customer_phone = resultSet.getString("customer_phone");
                String booking_date = resultSet.getString("booking_date");
                String booking_time = resultSet.getString("booking_time");
                int num_guests = resultSet.getInt("num_guests");
                booking = new BookingDTO(id, restaurant_id, restaurant_name,customer_name, customer_phone, booking_date, booking_time, num_guests);
            }
        } catch (SQLException e) {
            throw new DaoException("Error finding booking by id: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("Error finding booking by id: " + e.getMessage());
            }
        }
        return booking; // reference to the booking object, or null value
    }


    @Override
    public BookingDTO insertBooking(BookingDTO bookingDTO) throws DaoException {
        String sql = "INSERT INTO booking (restaurant_id, customer_name, customer_phone, booking_date, booking_time, num_guests) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, bookingDTO.getRestaurant_id());
            statement.setString(2, bookingDTO.getCustomer_name());
            statement.setString(3, bookingDTO.getCustomer_phone());
            statement.setString(4, bookingDTO.getBooking_date());
            statement.setString(5, bookingDTO.getBooking_time());
            statement.setInt(6, bookingDTO.getNum_guests());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bookingDTO.setBooking_id(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            throw new DaoException("Error adding booking: " + e.getMessage());
        }

        return bookingDTO;
    }


        public boolean deleteBookingById(int id) throws DaoException {
        String sql = "DELETE FROM booking WHERE booking_id=?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new DaoException("Error deleting booking by id: " + e.getMessage());
        }
    }

    @Override
    public BookingDTO updateBookingDate(int booking_id, String booking_date) throws DaoException {
        String sql = "UPDATE booking SET booking_date = ? WHERE booking_id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, booking_date);
            statement.setInt(2, booking_id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Booking with ID " + booking_id+ " not found.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error updating booking date: " + e.getMessage());
        }

        return null;
    }

    /*=================METHOD TO SORT ALL BOOKING BY FILTERS========================*/
    @Override
    public List<BookingDTO> findBookingsUsingFilter(Comparator<BookingDTO> comparator) throws SQLException {
        List<BookingDTO> bookings = findAllBookingsWithRestaurantNames();
        bookings.sort(comparator);
        return  bookings;
    }





}
