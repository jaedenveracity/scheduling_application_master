package View_Controller;

import Model.User;
import Model.UserSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainScreenController {

    @FXML
    Label currentUserLabel;
    Button mainExitButton;

    public static void printActiveUser ()
    {
        System.out.println("Active user from MainScreenController: " + UserSession.getInstance().getUserName());
    }

    @FXML
    public void initialize()
    {
        MainScreenController.printActiveUser();
        currentUserLabel.setText(UserSession.getInstance().getUserName());
    }

    public void exitButtonClicked (ActionEvent actionEvent)
    {
        Platform.exit();
        System.exit(0);
    }

}
