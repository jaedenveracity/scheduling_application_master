package Model;

import View_Controller.Main;
import Model.Database;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User
{
    private int userId;
    private String userName;
    private String password;
    private boolean activeUser;

    public User (){};

    public User (int newUserId, String newUserName)
    {
        this.userId = newUserId;
        this.userName = newUserName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String addUser (int newUserId, String newUserName)
    {


        return "Sweet";
    }

    public static User loginUserCheck (String testUserName, String testUserPass)
    {
        User foundUser = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {

            conn = Database.checkDataSource().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM user");



            while(rs.next())
            {
                int id = rs.getInt("userId");
                String dbUsername = rs.getString("userName");
                String dbPassword = rs.getString("password");

                System.out.println("User with id: " + id + " and username: " + dbUsername + " and password: " + dbPassword);

                System.out.println("Test username: '" + testUserName + "' equals the db username: '" + dbUsername + "': " + dbUsername.equals(testUserName));


                if (testUserName.equals(dbUsername) && testUserPass.equals(dbPassword))
                {
                    System.out.println("User found with id: " + id + " and Username: " + dbUsername);
                    foundUser = new User(id, testUserName);
                }

                else {
                    throw new Exception();
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("Username not found, please try again.");
        }

        //Close Resources


        return foundUser;
    }

}

