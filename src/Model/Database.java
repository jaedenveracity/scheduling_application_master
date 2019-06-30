package Model;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

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
}
