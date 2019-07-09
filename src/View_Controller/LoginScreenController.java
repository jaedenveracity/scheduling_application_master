package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import Model.User;
import Model.Database;
import Model.UserSession;
import javafx.stage.Stage;

import java.time.*;


public class LoginScreenController {

    @FXML private TextField usernameLoginField;
    @FXML private PasswordField passwordLoginField;

    @FXML private Label loginIssueLabel;

    User curUser = null;
    int loginAttempts = 0;
    File appendfile = new File("login_attempts.txt");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    Locale locale = Locale.getDefault();


    public void loginButtonClicked (ActionEvent actionEvent)
    {

        BufferedWriter out = null;
        System.out.println(locale);

        //Retrieve user login information
        try {

            out = new BufferedWriter(new FileWriter(appendfile, true));

            String current_user = usernameLoginField.getText();
            String current_pass = passwordLoginField.getText();

            if (current_user.trim().isEmpty() || current_pass.trim().isEmpty()) {
                throw new IllegalArgumentException();
            }
            System.out.println(TimeZone.getDefault().getID());

            System.out.println("Current User: " + current_user);
            System.out.println("Current Pass: " + current_pass);

            curUser = User.loginUserCheck(current_user, current_pass);

            //No user found in database - authentication failed
            if (curUser == null) {
                loginIssueLabel.setVisible(true);

                if (appendfile.length() != 0) {
                    out.newLine();
                }

                out.write(LocalDate.now() + ":" + LocalTime.now().format(dtf) + " - User login failure with attempted Username: " + current_user + "' Location: " + TimeZone.getDefault().getID());
                out.flush();

            }

            //User was found and a new User object created for the session
            else {

                if (appendfile.length() != 0) {
                    out.newLine();
                }

                out.write(LocalDate.now() + ":" + LocalTime.now().format(dtf) + " - User login was successful with Username: '" + current_user + "' Location: " + TimeZone.getDefault().getID());
                out.close();

                UserSession.setInstance(curUser.getUserName());

                System.out.println("Current active session user: " + UserSession.getInstance().toString());

                Parent mainScreenButtonParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));

                Scene mainScreenScene = new Scene(mainScreenButtonParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                window.setScene(mainScreenScene);
                window.show();



            }

        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Username and/or Password field(s) cannot be empty");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Unable to append to a file the user login attempt");
            e.printStackTrace();
        }
        finally {
            loginAttempts++;
        }
    }

    @FXML
    public void initialize()
    {
        //Set failed user attempt to default not show
        loginIssueLabel.setVisible(false);
    }


}
