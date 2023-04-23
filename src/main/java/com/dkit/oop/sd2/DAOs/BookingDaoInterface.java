package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.BookingDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public interface BookingDaoInterface {

    //BOOKING

    List<BookingDTO> findAllBookingsWithRestaurantNames() throws SQLException;

    BookingDTO findBookingId(int id)throws SQLException;

    BookingDTO insertBooking(BookingDTO bookingDTO) throws DaoException;

    boolean deleteBookingById(int id) throws SQLException;

    List<BookingDTO> findBookingsUsingFilter(Comparator<BookingDTO> comparator) throws SQLException;
}
