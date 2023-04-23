package com.dkit.oop.sd2.DAOs;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MySqlBookingDao extends MySqlDao implements UserDaoInterface{


    @Override
    public List<BookingDTO> findAllBookingsWithRestaurantNames() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<BookingDTO> bookings = new ArrayList<>();

        try {
            // Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT booking.booking_id, booking.restaurant_id, restaurant.name, booking.customer_name, booking.customer_phone, booking.booking_date, booking.num_guests " +
                    "FROM booking " +
                    "JOIN restaurant ON booking.restaurant_id = restaurant.id";
            ps = connection.prepareStatement(query);

            // Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int booking_id = resultSet.getInt("booking_id");
                int restaurant_id = resultSet.getInt("restaurant_id");
                String restaurant_name = resultSet.getString("name");
                String customer_name = resultSet.getString("customer_name");
                String customer_phone = resultSet.getString("customer_phone");
                String booking_date = resultSet.getString("booking_date");
                int num_guests = resultSet.getInt("num_guests");
               BookingDTO booking = new BookingDTO(booking_id, restaurant_id, restaurant_name, customer_name, customer_phone, booking_date, num_guests);
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
            String query = "SELECT booking.booking_id, booking.restaurant_id, restaurant.name, booking.customer_name, booking.customer_phone, booking.booking_date, booking.num_guests " +
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
                int num_guests = resultSet.getInt("num_guests");
                booking = new BookingDTO(id, restaurant_id, restaurant_name,customer_name, customer_phone, booking_date, num_guests);
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


    public boolean deleteBookingById(int id) throws DaoException {
        String sql = "DELETE FROM booking WHERE id=?";

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
    public List<RestaurantDTO> findAllRestaurants() throws SQLException {
        return null;
    }

    @Override
    public RestaurantDTO findRestaurantById(int id) throws SQLException {
        return null;
    }

    @Override
    public RestaurantDTO updatePhone(String name, int phone) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteRestaurantById(int id) throws SQLException {
        return false;
    }

    @Override
    public List<RestaurantDTO> findAllManagerContains(String subString) throws DaoException {
        return null;
    }

    @Override
    public RestaurantDTO insertRestaurant(RestaurantDTO restaurantDTO) throws SQLException {
        return null;
    }

    @Override
    public List<RestaurantDTO> findRestaurantsUsingFilter(Comparator<RestaurantDTO> comparator) throws SQLException {
        return null;
    }


}
