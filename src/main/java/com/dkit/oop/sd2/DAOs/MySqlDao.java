package com.dkit.oop.sd2.DAOs;
/** MySqlDao -
 * - implements functionality that is common to all MySQL DAOs
 * - i.e. getConection() and freeConnection()
 * All MySQL DAOs will extend (inherit from) this class in order to
 * gain the connection functionality, thus avoiding inclusion
 * of this code in every DAO class.
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.dkit.oop.sd2.DTOs.RestaurantDTO;
import com.dkit.oop.sd2.Exceptions.DaoException;

public abstract class MySqlDao
{
    public Connection getConnection() throws DaoException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/restaurantoop";
        String username = "root";
        String password = "";
        Connection connection = null;

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Failed to find driver class " + e.getMessage());
            System.exit(1);
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed " + e.getMessage());
            System.exit(2);
        }
        return connection;
    }

    public void freeConnection(Connection connection) throws DaoException
    {
        try
        {
            if (connection != null)
            {
                connection.close();
                connection = null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

//    public abstract List<User> findAllUsersLastNameContains(String subString) throws DaoException;
//

}