package View_Controller;

import Model.User;
import Model.UserSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void customerRecordsButtonClicked (ActionEvent actionEvent)
    {
        try {
            Parent customerRecordsButtonParent = FXMLLoader.load(getClass().getResource("CustomerRecords.fxml"));

            Scene customerRecordsScene = new Scene(customerRecordsButtonParent);

            //This line gets the Stage (Window) information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(customerRecordsScene);
            window.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
