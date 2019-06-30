package Model;

import Model.Appointment;
import java.util.Set;

public class UserSession {

    private static UserSession instance;

    private String userName;

    private UserSession(String curUserName)
    {
        this.userName = curUserName;
    }

    public static void setInstance (String userName)
    {
        if (instance == null)
        {
            instance = new UserSession(userName);
        }
    }

    public static UserSession getInstance ()
    {
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void cleanUserSession()
    {
        userName = null;
    }

    @Override
    public String toString()
    {
        return UserSession.getInstance().getUserName();
    }
}
