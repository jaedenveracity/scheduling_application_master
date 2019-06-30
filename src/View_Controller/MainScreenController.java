package View_Controller;

import Model.User;
import Model.UserSession;
import javafx.fxml.FXML;

public class MainScreenController {

    public static void printActiveUser ()
    {
        System.out.println("Active user from MainScreenController: " + UserSession.getInstance().getUserName());
    }

    @FXML
    public void initialize()
    {
        MainScreenController.printActiveUser();
    }
}
