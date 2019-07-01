package Model;

import com.mysql.cj.jdbc.MysqlDataSource;

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

            prepStmt = conn.prepareStatement("Insert into customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) values(?, ?, ?, ?, ?, ?)");
            prepStmt.setString(1, newCustomer.getCustomerName());
            prepStmt.setInt (2, 1);
            prepStmt.setInt(3, 1);
            prepStmt.setObject(4, param);
            prepStmt.setString(5, UserSession.getInstance().getUserName());
            prepStmt.setString(6, UserSession.getInstance().getUserName());

            result = prepStmt.executeUpdate();

            System.out.println(result + " record(s) inserted.");

            conn.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
