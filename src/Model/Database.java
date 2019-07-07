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

            //Country Table Insertion
            prepStmt = conn.prepareStatement("INSERT INTO country (country, createDate, createdBy, lastUpdateBy) values(?,?,?,?)");
            prepStmt.setString(1, newCustomer.getCustomerCountry().getCountry());
            prepStmt.setObject(2, param);
            prepStmt.setString(3, UserSession.getInstance().getUserName());
            prepStmt.setString(4, UserSession.getInstance().getUserName());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted into country table");

            //City Table Insertion
            prepStmt = conn.prepareStatement("Insert into city (city, countryId, createDate, createdBy, lastUpdateBy) values(?,?,?,?,?)");
            prepStmt.setString(1, newCustomer.getCustomerCity().getCity());
            prepStmt.setInt(2, Database.getCountryId(newCustomer.getCustomerCountry()));//Database.getCountryId(newCustomer.getCustomerCountry()));
            prepStmt.setObject(3, param);
            prepStmt.setString(4, UserSession.getInstance().getUserName());
            prepStmt.setString(5, UserSession.getInstance().getUserName());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted into city table");

            //Address Table Insertion
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

            //Customer Table Insertion
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
            query = "select * from customer cu join address ad ON cu.addressId = ad.addressId JOIN city ci ON ad.cityId = ci.cityId JOIN country ct ON ci.countryId = ct.countryId";
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

    public static int getCustomerId(Customer customerName)
    {
        Connection conn;
        String query;
        PreparedStatement ps;
        ResultSet customers = null;
        int customerId = 0;

        try
        {
            conn = Database.checkDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM customer where customerName = ?");
            ps.setString(1,customerName.getCustomerName());

            customers = ps.executeQuery();

            while (customers.next())
            {
                customerId = customers.getInt("customerId");
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customerId;

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

    public static int getCountryId(Country countryName)
    {
        Connection conn;
        String query;
        PreparedStatement ps;
        ResultSet countries = null;
        int countryId = 0;

        try {
            conn = Database.checkDataSource().getConnection();
            ps = conn.prepareStatement("Select * FROM country where country = ?");
            ps.setString(1, countryName.getCountry());

            countries = ps.executeQuery();

            while (countries.next())
            {
                countryId = countries.getInt("countryId");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return countryId;
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

    public static int deleteCustomer (String customerName)
    {
        //TODO: Verify closure of all these resources
        Connection conn = null;
        String query;
        PreparedStatement ps = null;

        ResultSet returnedResultsCustomer = null;
        ResultSet returnedResultsAddress = null;
        ResultSet returnedResultsCity = null;

        int deleteCustomerId;
        int customerDeletedCorrectly = 0;

        int deleteAddressId;
        int addressDeletedCorrectly = 0;

        int deleteCityId;
        int cityDeletedCorrectly = 0;

        int deleteCountryId;
        int countryDeletedCorrectly = 0;

        try {
            conn = Database.checkDataSource().getConnection();
            ps = conn.prepareStatement("SELECT customerId, addressId  FROM customer WHERE customerName = ?");
            ps.setString(1, customerName);

            returnedResultsCustomer = ps.executeQuery();

            returnedResultsCustomer.first();

            deleteCustomerId = returnedResultsCustomer.getInt("customerId");
            deleteAddressId = returnedResultsCustomer.getInt ("addressId");

            ps = conn.prepareStatement("SELECT cityId FROM address where addressId = ?");
            ps.setInt(1, deleteAddressId);

            returnedResultsAddress = ps.executeQuery();
            returnedResultsAddress.first();

            deleteCityId = returnedResultsAddress.getInt("cityId");

            ps = conn.prepareStatement("SELECT countryId FROM city where cityId = ?");
            ps.setInt(1, deleteCityId);

            returnedResultsCity = ps.executeQuery();
            returnedResultsCity.first();

            deleteCountryId = returnedResultsCity.getInt("countryId");

            System.out.println("Customer to be deleted: " + Integer.toString(deleteCustomerId));
            System.out.println("Address to be deleted: " + Integer.toString(deleteAddressId));
            System.out.println("City to be deleted: " + Integer.toString(deleteCityId));
            System.out.println("Country to be deleted: " + Integer.toString(deleteCountryId));

            ps = conn.prepareStatement("DELETE FROM customer where customerId = ?");
            ps.setInt(1, deleteCustomerId);

            customerDeletedCorrectly = ps.executeUpdate();

            if (customerDeletedCorrectly == 1){
                System.out.println("Customer deleted successfully.");
            }

            ps = conn.prepareStatement("DELETE FROM address where addressId = ?");
            ps.setInt(1, deleteAddressId);

            addressDeletedCorrectly = ps.executeUpdate();

            if (addressDeletedCorrectly == 1){
                System.out.println("Address deleted successfully.");
            }

            ps = conn.prepareStatement("DELETE FROM city WHERE cityId = ?");
            ps.setInt(1, deleteCityId);

            cityDeletedCorrectly = ps.executeUpdate();

            if (cityDeletedCorrectly == 1)
            {
                System.out.println("City deleted successfully.");
            }

            ps = conn.prepareStatement("DELETE FROM country where countryId = ?");
            ps.setInt(1, deleteCountryId);

            countryDeletedCorrectly = ps.executeUpdate();

            if (countryDeletedCorrectly == 1)
            {
                System.out.println("Country deleted successfully.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (returnedResultsCustomer != null)
            {
                try {
                    returnedResultsCustomer.close();
                }
                catch (SQLException e)
                {
                    System.out.println("Unable to close ResultSet 'returnedResults'.");
                }
            }

            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException e)
                {
                    System.out.println("Unable to close the prepared statement connection 'ps'.");
                }
            }

            if (conn != null)
            {
                try{
                    conn.close();
                }
                catch (SQLException e)
                {
                    System.out.println("Unable to close the SQL connection 'conn'.");
                }
            }
        }



        return 0;
    }

    public static int modifyCustomer (Customer modifiableCustomer, int countryId, int cityId, int addressId, int customerId)
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

            //Country Table Insertion
            prepStmt = conn.prepareStatement("UPDATE country SET country = ? WHERE countryId = ?");
            prepStmt.setString(1, modifiableCustomer.getCountry());
            prepStmt.setInt (2, countryId);

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) updated in country table");

            //City Table Insertion
            prepStmt = conn.prepareStatement("UPDATE city SET city = ? WHERE cityId = ?");
            prepStmt.setString(1, modifiableCustomer.getCity());
            prepStmt.setInt (2, cityId);

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) updated in city table");

            //Address Table Insertion
            prepStmt = conn.prepareStatement("UPDATE address SET address = ? WHERE addressId = ?");
            prepStmt.setString(1, modifiableCustomer.getAddress());
            prepStmt.setInt (2, addressId);

            result = prepStmt.executeUpdate();

            prepStmt = conn.prepareStatement("UPDATE address SET postalCode = ? WHERE addressId = ?");
            prepStmt.setString(1, modifiableCustomer.getPostalCode());
            prepStmt.setInt (2, addressId);

            result = prepStmt.executeUpdate();

            prepStmt = conn.prepareStatement("UPDATE address SET phone = ? WHERE addressId = ?");
            prepStmt.setString(1, modifiableCustomer.getPhoneNumber());
            prepStmt.setInt (2, addressId);

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) updated in address table");

            //Customer Table Insertion
            prepStmt = conn.prepareStatement("UPDATE customer SET customerName = ? WHERE customerId = ?");
            prepStmt.setString(1, modifiableCustomer.getCustomerName());
            prepStmt.setInt (2, customerId);

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) updated in customer table");



        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;

    }

    public static void main(String[] args)
    {
        Country newCountry = new Country();
        newCountry.setCountry("US");

        Database.getCountryId(newCountry);
    }



}
