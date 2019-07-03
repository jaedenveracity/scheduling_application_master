package Model;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.protocol.Resultset;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.sql.Date;

public class Database {

    static private final String url = "jdbc:mysql://52.206.157.109:3306/U05odG";
    static private final String username = "U05odG";
    static private final String password = "53688562774";



    public static boolean checkDbConnection () {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected yay");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public static DataSource checkDataSource ()
    {

        MysqlDataSource mysqlDs = null;
        try{

            mysqlDs = new MysqlDataSource();
            mysqlDs.setUrl(url);
            mysqlDs.setUser(username);
            mysqlDs.setPassword(password);

            return mysqlDs;
        }
        catch (Exception e)
        {
            e.printStackTrace();

           return mysqlDs;
        }

    }

    public static boolean addCustomer (Customer newCustomer)//, String userPhone, String userAddress, String userCity, String userPostal, String userCountry)
    {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String query = null;
        int result;
        Date curDate = new Date(Calendar.getInstance().getTime().getTime());
        Object param = new java.sql.Timestamp(curDate.getTime());

        try
        {
            conn = Database.checkDataSource().getConnection();

            prepStmt = conn.prepareStatement("Insert into city (city, countryId, createDate, createdBy, lastUpdateBy) values(?,?,?,?,?)");
            prepStmt.setString(1, newCustomer.getCustomerCity().getCity());
            prepStmt.setInt(2, 1);
            prepStmt.setObject(3, param);
            prepStmt.setString(4, UserSession.getInstance().getUserName());
            prepStmt.setString(5, UserSession.getInstance().getUserName());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted into city table");

            prepStmt = conn.prepareStatement("Insert into address (address, postalCode, phone, createDate, createdBy, lastUpdateBy, cityId, address2) values(?,?,?,?,?,?, ?, ?)");
            prepStmt.setString(1, newCustomer.getCustomerAddress().getAddress());
            prepStmt.setString(2, newCustomer.getCustomerAddress().getPostalCode());
            prepStmt.setString(3, newCustomer.getCustomerAddress().getPhoneNumber());
            prepStmt.setObject(4, param);
            prepStmt.setString(5, UserSession.getInstance().getUserName());
            prepStmt.setString(6, UserSession.getInstance().getUserName());
            prepStmt.setInt(7, Database.getCityId(newCustomer.getCustomerCity()));
            prepStmt.setString(8, newCustomer.getCustomerAddress().getAddressTwo());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted into address table");


            prepStmt = conn.prepareStatement("Insert into customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) values(?, ?, ?, ?, ?, ?)");
            prepStmt.setString(1, newCustomer.getCustomerName());
            prepStmt.setInt (2, Database.getAddressId(newCustomer.getCustomerAddress()));
            prepStmt.setInt(3, 1);
            prepStmt.setObject(4, param);
            prepStmt.setString(5, UserSession.getInstance().getUserName());
            prepStmt.setString(6, UserSession.getInstance().getUserName());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted into customer table");


            conn.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static ResultSet getAllCustomers()
    {

        Connection conn;
        String query;
        ResultSet customers = null;

        try {
            conn = Database.checkDataSource().getConnection();
            query = "select * from customer cu join address ad ON cu.addressId = ad.addressId JOIN city ci ON ad.cityId = ci.cityId;";
            customers = conn.createStatement().executeQuery(query);


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        assert customers != null : "Unable to store customer information and return the resultset from database";

        return customers;
    }

    public static ResultSet getAllAddress()
    {

        Connection conn;
        String query;
        ResultSet addresses = null;

        try {
            conn = Database.checkDataSource().getConnection();
            query = "Select * from address";
            addresses = conn.createStatement().executeQuery(query);


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        assert addresses != null : "Unable to store address information and return the resultset from database";

        return addresses;
    }

    public static int getCityId(City cityName)
    {

        Connection conn;
        String query;
        PreparedStatement ps;
        ResultSet cities = null;
        int cityId = 0;

        try {
            conn = Database.checkDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM city WHERE city = ?");
            ps.setString(1, cityName.getCity());

            cities = ps.executeQuery();

            while (cities.next())
            {
                cityId = cities.getInt("cityId");
            }



        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return cityId;
    }

    public static int getAddressId(Address addressName)
    {

        Connection conn;
        String query;
        PreparedStatement ps;
        ResultSet cities = null;
        int addressId = 0;

        try {
            conn = Database.checkDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM address WHERE address = ?");
            ps.setString(1, addressName.getAddress());

            cities = ps.executeQuery();

            while (cities.next())
            {
                addressId = cities.getInt("addressId");
            }



        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return addressId;
    }

    public static void main(String[] args) {

        City newYork = new City("New York");

       int testedId =  Database.getCityId(newYork);

        System.out.println(testedId);

        Address testAddress = new Address();
        testAddress.setAddress("123 Main");

        int testedAddressId = Database.getAddressId(testAddress);

        System.out.println(testedAddressId);
    }

}
