package com.dkit.oop.sd2.DAOs;

/** OOP Feb 2022
 * UserDaoInterface
 *
 * Declares the methods that all UserDAO types must implement,
 * be they MySql User DAOs or Oracle User DAOs etc...
 *
 * Classes from the Business Layer (users of this DAO interface)
 * should use reference variables of this interface type to avoid
 * dependencies on the underlying concrete classes (e.g. MySqlUserDao).
 *
 * More sophisticated implementations will use a factory
 * method to instantiate the appropriate DAO concrete classes
 * by reading database configuration information from a
 * configuration file (that can be changed without altering source code)
 *
 * Interfaces are also useful when testing, as concrete classes
 * can be replaced by mock DAO objects.
 */

import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;

import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public interface UserDaoInterface {


    List<BookingDTO> findBookingsUsingFilter(Comparator<BookingDTO> comparator) throws SQLException;

    // RESTAURANT
    List<RestaurantDTO> findAllRestaurants() throws SQLException;

    RestaurantDTO findRestaurantById(int id) throws SQLException;

    RestaurantDTO updatePhone(String name, int phone) throws DaoException;

    boolean deleteRestaurantById(int id) throws SQLException;

    List<RestaurantDTO> findAllManagerContains(String subString) throws DaoException;

    RestaurantDTO insertRestaurant(RestaurantDTO restaurantDTO) throws SQLException;

    List<RestaurantDTO> findRestaurantsUsingFilter(Comparator<RestaurantDTO> comparator) throws SQLException;


    //BOOKING

    List<BookingDTO> findAllBookingsWithRestaurantNames() throws SQLException;

    BookingDTO findBookingId(int id)throws SQLException;

    BookingDTO insertBooking(BookingDTO bookingDTO) throws DaoException;

    boolean deleteBookingById(int id) throws SQLException;

}
