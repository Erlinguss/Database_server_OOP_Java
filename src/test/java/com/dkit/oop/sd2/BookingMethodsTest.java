package com.dkit.oop.sd2;

import com.dkit.oop.sd2.DAOs.Booking.BookingDaoInterface;
import com.dkit.oop.sd2.DAOs.Booking.BookingMethods;
import com.dkit.oop.sd2.DAOs.Booking.MySqlBookingDao;
import com.dkit.oop.sd2.DTOs.BookingDTO;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookingMethodsTest {
     BookingDaoInterface bookingDao = new MySqlBookingDao();

    @Test
    public void testFindAllBookingsWithRestaurantNames() throws SQLException {
        System.out.println("testFindAllBookingsWithRestaurantNames");
        List<BookingDTO> bookings = bookingDao.findAllBookingsWithRestaurantNames();
        assertNotNull(bookings);
        assertFalse(bookings.isEmpty());
    }

    @Test
    public void testSearchBookingById() throws SQLException {
        System.out.println("testSearchBookingById");
        int id = 2;
        BookingDTO booking = bookingDao.findBookingId(id);
        assertNotNull(booking);
    }

    @Test
    public void testAddNewBooking() throws SQLException {
        System.out.println("testAddNewBooking");
        BookingDTO bookingDTO = new BookingDTO(4, 5, null,
                "Lucas Brown", "897889782", "2023-05-04",
                "12:00:00", 3);
        BookingDTO booking1 = bookingDao.insertBooking(bookingDTO);
        assertNotNull(booking1);
    }

    @Test
    public void testUpdateBookingDate() throws SQLException {
        System.out.println("testUpdateBookingDate");
        bookingDao.updateBookingDate(2, "2023-05-01");
        BookingDTO booking = bookingDao.findBookingId(2);
        assertEquals("2023-05-01", booking.getBooking_date());
    }

    @Test
    public void testFindAllBookingsAsJson() throws SQLException {
        System.out.println("testFindAllBookingsAsJson");
        String json = BookingMethods.findAllBookingsAsJson(bookingDao);
        assertNotNull(json);
        assertFalse(json.isEmpty());
    }

    @Test
    public void testFindBookingByIdAsJson() throws SQLException {
        System.out.println("testFindBookingByIdAsJson");
        int id = 1;
        String json = BookingMethods.findBookingByIdAsJson(bookingDao, new Scanner("1"));
        assertNotNull(json);
        assertFalse(json.isEmpty());
    }


}