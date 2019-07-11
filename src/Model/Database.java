package Model;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.sql.Date;

public class Database {

    static private final String url = "jdbc:mysql://52.206.157.109:3306/U05odG";
    static private final String username = "U05odG";
    static private final String password = "53688562774";

    //Instances cannot be created
    private Database ()
    {

    }

    public static int getUserId (UserSession activeUser)
    {
        Connection conn;
        String query;
        PreparedStatement ps;
        ResultSet userIdResultSet = null;
        int userId = 0;

        try {
            conn = Database.checkDataSource().getConnection();
            ps = conn.prepareStatement("SELECT userId FROM user where userName = ?");
            ps.setString(1, activeUser.getUserName());

            userIdResultSet = ps.executeQuery();

            while (userIdResultSet.next())
            {
                userId = userIdResultSet.getInt("userId");
            }




        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return userId;

    }

    public static ObservableList<Appointment> getCustomerAppointments(int selectedCustomerId) throws SQLException
    {
        Connection conn;
        PreparedStatement ps;
        ResultSet customerAppointments = null;
        ObservableList<Appointment> customerAppointmentsList = FXCollections.observableArrayList();

        conn = Database.checkDataSource().getConnection();
        ps = conn.prepareStatement("SELECT * FROM appointment WHERE customerId = ?");
        ps.setInt(1, selectedCustomerId);

        customerAppointments = ps.executeQuery();

        while (customerAppointments.next())
        {

            String appointmentTitle;
            String appointmentDescription;
            String appointmentLocation;
            String appointmentContact;
            String appointmentType;
            String appointmentUrl;
            String appointmentStart;
            String appointmentEnd;

            appointmentTitle = customerAppointments.getString("title");
            appointmentDescription = customerAppointments.getString("description");
            appointmentLocation = customerAppointments.getString("location");
            appointmentContact = customerAppointments.getString("contact");
            appointmentType = customerAppointments.getString("type");
            appointmentUrl = customerAppointments.getString("url");
            appointmentStart = customerAppointments.getString("start");
            appointmentEnd = customerAppointments.getString("end");

            Appointment nextAppointment = new Appointment(appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentUrl, appointmentStart, appointmentEnd);

            customerAppointmentsList.add(nextAppointment);


        }

        return customerAppointmentsList;



    }

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

    public static boolean addAppointment (Appointment newAppointment, String customerSelected) throws MysqlDataTruncation
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

            prepStmt = conn.prepareStatement("INSERT INTO appointment (customerId, title, description, location, contact, createDate, createdBy, lastUpdateBy, userId, type, url, start, end) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            prepStmt.setInt(1, Database.getCustomerId(customerSelected));
            prepStmt.setString(2, newAppointment.getAppointmentTitle());
            prepStmt.setString(3, newAppointment.getAppointmentDescription());
            prepStmt.setString(4, newAppointment.getAppointmentLocation());
            prepStmt.setString(5, newAppointment.getAppointmentContact());
            prepStmt.setObject(6, param);
            prepStmt.setString(7, UserSession.getInstance().getUserName());
            prepStmt.setString(8, UserSession.getInstance().getUserName());
            prepStmt.setInt(9, Database.getUserId(UserSession.getInstance()));
            prepStmt.setString(10, "null");
            prepStmt.setString(11, "null");
            prepStmt.setString(12, newAppointment.getAppointmentStart());
            prepStmt.setString(13, newAppointment.getAppointmentEnd());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted into appointment table");

            return true;


        }
        catch (SQLException e)
        {
            e.printStackTrace();

            return false;
        }
    }

    public static int deleteAppointment (int appointmentId)
    {
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String query = null;
        int result = 0;

        try
        {
            conn = Database.checkDataSource().getConnection();
            prepStmt = conn.prepareStatement ("DELETE FROM appointment WHERE appointmentId = ?");
            prepStmt.setInt(1, appointmentId);
            result = prepStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return result;
        }

        return result;

    }

    public static int getAppointmentId (Appointment toDeleteAppointment)
    {
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String query = null;
        boolean result;
        int appointmentId = 0;

        try
        {
            conn = Database.checkDataSource().getConnection();
            prepStmt = conn.prepareStatement("SELECT appointmentId FROM appointment WHERE title = ? AND description = ? AND location = ? AND contact = ?");
            prepStmt.setString(1, toDeleteAppointment.getAppointmentTitle());
            prepStmt.setString(2, toDeleteAppointment.getAppointmentDescription());
            prepStmt.setString(3, toDeleteAppointment.getAppointmentLocation());
            prepStmt.setString(4, toDeleteAppointment.getAppointmentContact());
            rs = prepStmt.executeQuery();

            while (rs.next())
            {
                appointmentId = rs.getInt("appointmentId");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }

        return appointmentId;
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

            return true;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
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
    //Overloaded method to take String parameter instead
    public static int getCustomerId(String customerName)
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
            ps.setString(1,customerName);

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

    public static ResultSet getAllAppointments() throws SQLException
    {
        Connection conn;
        String query;
        ResultSet appointments = null;

        conn = Database.checkDataSource().getConnection();
        query = "Select * from appointment";
        appointments = conn.createStatement().executeQuery(query);

        assert appointments != null : "Unable to store appointment information and return the resultset from database";

        return appointments;
    }

    public static void main(String[] args)
    {
        Country newCountry = new Country();
        newCountry.setCountry("US");

        Database.getCountryId(newCountry);
    }



}

/*
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

 */