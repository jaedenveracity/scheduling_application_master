package View_Controller;

import Model.Appointment;
import Model.Database;
import Model.User;
import Model.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainScreenController {

    private ObservableList<Appointment> warningAppointments = FXCollections.observableArrayList();

    @FXML
    private Label currentUserLabel;
    @FXML private Label welcomeLabel;
    @FXML private Button mainExitButton;
    @FXML private Label timeLabel;

    public static void printActiveUser ()
    {
        System.out.println("Active user from MainScreenController: " + UserSession.getInstance().getUserName());
    }

    @FXML
    public void initialize()
    {
        List<Appointment> listAppointments = new ArrayList<>();
        System.out.println(LocalTime.now());
        String appointmentStartTime = null;

        MainScreenController.printActiveUser();
        currentUserLabel.setText(UserSession.getInstance().getUserName());
        welcomeLabel.setText(LoginScreenController.getRb().getString("welcome") + ":");



        Alert test = new Alert(Alert.AlertType.NONE);
        test.setAlertType(Alert.AlertType.INFORMATION);

        try {
            ResultSet appointments = Database.getAllAppointments();

            while (appointments.next())
            {

                Appointment readableAppointment = new Appointment();

                readableAppointment.setAppointmentTitle(appointments.getString("title"));
                readableAppointment.setAppointmentDescription(appointments.getString("description"));
                readableAppointment.setAppointmentLocation(appointments.getString("location"));
                readableAppointment.setAppointmentContact(appointments.getString("contact"));
                readableAppointment.setAppointmentType(appointments.getString("type"));
                readableAppointment.setUrl(appointments.getString("url"));
                readableAppointment.setAppointmentStart(appointments.getString("start"));
                readableAppointment.setAppointmentEnd(appointments.getString("end"));

                String[] dateTimeSplitStart = readableAppointment.getAppointmentStart().split(" ");

                for (String x : dateTimeSplitStart)
                {
                    System.out.println(x);

                    appointmentStartTime = dateTimeSplitStart[1];

                    //split again for hours minutes seconds

                    //Check time vs current localtime
                }



                listAppointments.add(readableAppointment);
            }


        }
        catch (SQLException e)
        {
            System.out.println("Unable to retrieve appointments from database to check for appointments in the next fifteen minutes");
        }





        test.setContentText("Warning: There is an appointment scheduled in the next fifteen minutes!");
        //test.show();

        timeLabel.setText(User.getCurrentTime());
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

    public void appointmentRecordsButtonClicked (ActionEvent actionEvent)
    {
        try {
            Parent appointmentRecordsButtonParent = FXMLLoader.load(getClass().getResource("AppointmentRecords.fxml"));

            Scene appointmentRecordsScene = new Scene(appointmentRecordsButtonParent);

            //This line gets the Stage (Window) information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(appointmentRecordsScene);
            window.show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }

}
