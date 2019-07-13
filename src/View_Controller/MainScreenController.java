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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        boolean appointmentFifteenMinutes = false;
        StringBuilder nextFifteenAppointmentTimes = new StringBuilder();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm");
        LocalTime time;
        LocalTime currentTime;

        String timeString;
        String currentTimeString;

        String dateString;
        String currentDateString;

        currentTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        currentTimeString = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println("Current local time is: " +  currentTime);

        currentDateString = LocalDate.now().toString();

        MainScreenController.printActiveUser();
        currentUserLabel.setText(UserSession.getInstance().getUserName());
        welcomeLabel.setText(LoginScreenController.getRb().getString("welcome") + ":");

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

                dateString = dateTimeSplitStart[0];

                System.out.println(dateTimeSplitStart[1]);
                time = LocalTime.parse(dateTimeSplitStart[1]);

                timeString = dateTimeSplitStart[1];

                System.out.println("Current time for appointment is: " + time);

               //Get hours and seconds from both current time and appointment and compare
                String[] currentTimeSplit = currentTimeString.split(":");
                String[] appointmentTimeSplit = timeString.split(":");

                int curHour = Integer.parseInt(currentTimeSplit[0]);
                int curMinute = Integer.parseInt(currentTimeSplit[1]);

                int apptHour = Integer.parseInt(appointmentTimeSplit[0]);
                int apptMinute = Integer.parseInt(appointmentTimeSplit[1]);

                //Using LocalTime objects - checking fifteen minutes

                if(currentDateString.equals(dateString)) {

                    System.out.println("Current Date matches an appointment date in our database. We have appointments today!");

                    currentTime = currentTime.minusSeconds(1);

                    if (time.isAfter(currentTime)) {

                        currentTime.plusSeconds(1);
                        LocalTime currentTimePlusFifteen = currentTime.plusMinutes(15);
                        currentTimePlusFifteen.plusSeconds(1);

                        if (time.isBefore(currentTimePlusFifteen)) ;
                        System.out.println("Appointment time is within the next fifteen minutes!");
                        nextFifteenAppointmentTimes.append(timeString);
                        nextFifteenAppointmentTimes.append(" ");
                    }
                }
            }


        }
        catch (SQLException e)
        {
            System.out.println("Unable to retrieve appointments from database to check for appointments in the next fifteen minutes");
        }

        if (nextFifteenAppointmentTimes.length() != 0) {

            Alert test = new Alert(Alert.AlertType.NONE);
            test.setAlertType(Alert.AlertType.INFORMATION);

            test.setHeaderText("Warning!");
            test.setContentText("Warning: There are appointments in the next fifteen minutes starting at these times: " + nextFifteenAppointmentTimes);
            test.show();

        }

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
