package com.dkit.oop.sd2;

import com.dkit.oop.sd2.DAOs.Restaurant.MySqlRestaurantDao;
import com.dkit.oop.sd2.DAOs.Restaurant.RestaurantDaoInterface;
import com.dkit.oop.sd2.DAOs.Restaurant.RestaurantMethods;
import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RestaurantMethodsTest
{
    RestaurantDaoInterface restDao = new MySqlRestaurantDao();

    @Test
    public void testViewAllRestaurants() throws SQLException {
        System.out.println("testViewAllRestaurants");
        List<RestaurantDTO> restaurants = restDao.findAllRestaurants();
         assertNotNull(restaurants );
        assertFalse(restaurants .isEmpty());
    }

    @Test
    public void TestSearchRestaurantById() throws SQLException {
        System.out.println("testSearchBookingById");
        int id = 4;
        RestaurantDTO restaurant = restDao.findRestaurantById(id);
        assertNotNull(restaurant);
    }

    @Test
    public void testAddNewRestaurant() throws SQLException {
        System.out.println("testAddNewRestaurant");
        RestaurantDTO restaurant  = new RestaurantDTO( 9,"Chipotle",
                                               "Mike Davis", "888887142", 4.3);
        RestaurantDTO restaurant1  = restDao.insertRestaurant(restaurant);
        assertNotNull(restaurant1);
    }


    @Test
    public void testUpdateRestaurantPhone() throws SQLException {
        System.out.println("testUpdateRestaurantPhone");
        restDao.updatePhone("Popeyes", 879863698);
        RestaurantDTO restaurant2  = restDao.findRestaurantById(8);

        assertEquals("879863698",restaurant2.getPhone());
    }

    @Test
    public void testFindAllRestaurantsAsJson() throws SQLException {
        System.out.println("testFindAllRestaurantsAsJson");
        String json = RestaurantMethods.findAllRestaurantsAsJson(restDao);
        assertNotNull(json);
        assertFalse(json.isEmpty());
    }

    @Test
    public void testFindRestaurantByIdAsJson() throws SQLException {
        System.out.println("testFindRestaurantByIdAsJson");
        String json = RestaurantMethods.findRestaurantByIdAsJson(restDao, new Scanner("4"));
        assertNotNull(json);
        assertFalse(json.isEmpty());
    }



}
