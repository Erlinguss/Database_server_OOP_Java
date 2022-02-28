package com.dkit.oop.sd2.DAOs;

/** OOP Feb 2022
 *
 * Data Access Object (DAO) for User table with MySQL-specific code
 * This 'concrete' class implements the 'UserDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 * The Business Logic layer is only permitted to access the database by calling
 * methods provided in the Data Access Layer - i.e. by callimng the DAO methods.
 *
 */


import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MySqlUserDao extends MySqlDao implements UserDaoInterface
{

    @Override
    public List<RestaurantDTO> findAllRestaurants() throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<RestaurantDTO> restaurants = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT * FROM restaurant";
            ps = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String manager = resultSet.getString("manager");
                String phone = resultSet.getString("phone");
                double rating = resultSet.getDouble("rating");
                RestaurantDTO u = new RestaurantDTO(id, name, manager, phone, rating);
                restaurants .add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllRestaurantSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllRestaurant() " + e.getMessage());
            }
        }
        return restaurants;     // may be empty
    }

    @Override
    public RestaurantDTO findRestaurantById(int id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RestaurantDTO  restaurant = null;
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM restaurant WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);


            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {

                id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String manager = resultSet.getString("manager");
                String phone = resultSet.getString("phone");
                double rating = resultSet.getDouble("rating");
                restaurant= new RestaurantDTO(id, name, manager, phone, rating);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findRestaurantById() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findRestaurantById() " + e.getMessage());
            }
        }
        return restaurant;     // reference to the restaurant object, or null value
    }



    @Override
    public List<RestaurantDTO> findAllManagerContains(String subString) throws DaoException {

        String query = "SELECT * FROM restaurant WHERE name LIKE ?";
        List<RestaurantDTO> restList = new ArrayList<>();

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + subString + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                RestaurantDTO rest = new RestaurantDTO();
                rest.setId(resultSet.getInt("id"));
                rest.setName(resultSet.getString("name"));
                rest.setManager(resultSet.getString("manager"));
                rest.setPhone(resultSet.getString("phone"));
                rest.setRating(resultSet.getDouble("rating"));

          restList.add(rest);



            }
        } catch (SQLException e) {
            throw new DaoException("Error finding all managers that contains: " + e.getMessage());
        }

        return restList;
    }

    @Override
    public RestaurantDTO insertRestaurant(RestaurantDTO restaurantDTO)  throws DaoException {
        String sql = "INSERT INTO restaurant ( name, manager, phone, rating) VALUES (?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, restaurantDTO.getName());
            statement.setString(2, restaurantDTO.getManager());
            statement.setString(3, restaurantDTO.getPhone());
            statement.setDouble(4, restaurantDTO.getRating());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error adding restaurant: " + e.getMessage());
        }

        return null;
    }


    @Override
    public RestaurantDTO updatePhone(String name, int phone) throws DaoException {
        String sql = "UPDATE restaurant SET phone = ? WHERE name = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, phone);
            statement.setString(2, name);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error updating phone: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteRestaurantById(int id)  throws DaoException {


        String sql = "DELETE FROM restaurant WHERE id=?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error deleting restaurant by id: " + e.getMessage());
        }
    }

    @Override
    public List<RestaurantDTO> findRestaurantsUsingFilter(Comparator<RestaurantDTO> comparator) throws SQLException {
        List<RestaurantDTO> restaurants = findAllRestaurants();
        restaurants.sort(comparator);
        return restaurants;
    }


}

