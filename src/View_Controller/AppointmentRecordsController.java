package View_Controller;

import Model.*;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentRecordsController {

    @FXML private ComboBox<String> customerComboBox = new ComboBox<>();
    @FXML private ComboBox<String> customerComboBoxSelector = new ComboBox<>();

    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField contactTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField urlTextField;
    @FXML private TextField startTextField;
    @FXML private TextField endTextField;

    //Appointment TableView
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, String> appointmentTitleTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentLocationTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentContactTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentUrlTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentStartTableColumn;
    @FXML private TableColumn<Appointment, String> appointmentEndTableColumn;

    @FXML private Label noCustomerSelectedLabel;
    @FXML private Label modifyAppointmentErrorLabel;

    //To hold customer data retrieved from SQL
    private ObservableList<Appointment> data;

    private ObservableList<Appointment> chosenCustomerData;

    public void mainPageButtonClicked (ActionEvent actionEvent)
    {
        //Load main screen
        try {
            Parent mainScreenButtonParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));

            Scene mainScreenScene = new Scene(mainScreenButtonParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreenScene);
            window.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addAppointmentButtonClicked (ActionEvent actionEvent)
    {
        String newTitle = titleTextField.getText();
        String newDescription = descriptionTextField.getText();
        String newLocation = locationTextField.getText();
        String newContact = contactTextField.getText();
        String newType = typeTextField.getText();
        String newUrl = urlTextField.getText();
        String newStart = startTextField.getText();
        String newEnd = endTextField.getText();
        String chosenCustomerName = null;
        int customerId;
        int userId;
        boolean appointmentInDatabase;

        String[] startAppointmentSplit = newStart.split(" ");

        String[] startAppointmentTimeSplit = startAppointmentSplit[1].split(":");

        if (Integer.parseInt(startAppointmentTimeSplit[0]) >= 17)
        {
            System.out.println("Start time is outside of business hours - our organization closes at 17:00 (5:00 PM)");
            throw new IllegalArgumentException("Cannot create a meeting outside of business hours");
        }


        Appointment newAppointment = new Appointment(newTitle, newDescription, newLocation, newContact, newType, newUrl, newStart, newEnd);

        System.out.println("New appointment was created successfully with title: " + newTitle);

        assert newAppointment != null : "Appointment object creation failed.";

        //Get customer selected, if none selected throw exception, and retrieve customer Id from Database
        try
        {

            Appointment.checkAppointmentConflicts(newAppointment);

            chosenCustomerName = customerComboBox.getSelectionModel().getSelectedItem();

            System.out.println("The customer for the new appointment is: " + chosenCustomerName);

            if (chosenCustomerName.isEmpty())
            {
                throw new IllegalArgumentException();
            }
            else {
                customerId = Database.getCustomerId(chosenCustomerName);
                userId = Database.getUserId(UserSession.getInstance());

                System.out.println("The chosen customer is: " + chosenCustomerName + ", with a database id of: " + Integer.toString(customerId));

                //Add appointment to database and temp data structure tying to specific customer retrieved in previous step
                appointmentInDatabase = Database.addAppointment(newAppointment, chosenCustomerName);

                if (appointmentInDatabase)
                {
                    data.add(newAppointment);

                    if (noCustomerSelectedLabel.isVisible())
                    {
                        noCustomerSelectedLabel.setVisible(false);
                    }
                }
            }
        }
        catch (OverlappingAppointmentException e) {
            System.out.println("Appointment conflicts with an existing - please try again.");
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            }
        catch (NullPointerException e){
            System.out.println("No customer selected for the appointment - please choose a customer and try again.");
            noCustomerSelectedLabel.setVisible(true);
        }
        catch (MysqlDataTruncation e)
        {
            System.out.println("Date/Times were not inputted in correct format.");
        }


    }

    public void specificCustomerChosen (ActionEvent actionEvent)
    {
        String chosenCustomerName = customerComboBoxSelector.getSelectionModel().getSelectedItem();
        int chosenCustomerId;

        System.out.println(chosenCustomerName);

        chosenCustomerId = Database.getCustomerId(chosenCustomerName);

        //Set selected customer to variable, then get that customers ID, then get all appointments for that customer id, then add appointments to an observablelist, and set tableview to this observable list

        try {
            chosenCustomerData = Database.getCustomerAppointments(chosenCustomerId);

            System.out.println(chosenCustomerData);

            appointmentTableView.setItems(chosenCustomerData);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void clearSelectionsButtonClicked (ActionEvent actionEvent)
    {
        customerComboBoxSelector.valueProperty().set(null);
        appointmentTableView.setItems(data);
    }

    public void modifyAppointmentButtonClicked (ActionEvent actionEvent)
    {
        try {
            Appointment.setModifiableAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            System.out.println("Modifiable appointment set as: " + Appointment.getModifiableAppointment());

            if (Appointment.getModifiableAppointment() == null) {
                throw new IllegalArgumentException();
            }
            else
            {
                modifyAppointmentErrorLabel.setVisible(false);

                try
                {
                    Parent modifyAppointmentSceneParent = FXMLLoader.load(getClass().getResource("AppointmentModification.fxml"));
                    Scene modifyAppointmentScene = new Scene(modifyAppointmentSceneParent);

                    //This line gets the Stage information
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                    window.setScene(modifyAppointmentScene);
                    window.show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            modifyAppointmentErrorLabel.setVisible(true);

        }



    }

    public void deleteAppointmentButtonClicked (ActionEvent actionEvent)
    {
        Appointment toDeleteAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        int appointmentId = Database.getAppointmentId(toDeleteAppointment);

        System.out.println("selected appointment to delete: " + toDeleteAppointment + ", with an appointmentId of: " + appointmentId);

        Database.deleteAppointment(appointmentId);
        data.remove(toDeleteAppointment);
    }

    public void initialize()
    {
        noCustomerSelectedLabel.setVisible(false);
        modifyAppointmentErrorLabel.setVisible(false);

        try {
            ResultSet customers = Database.getAllCustomers();

            while (customers.next()) {

                String curCustomerName = customers.getString("customerName");

                Customer.getCustomerNames().add(curCustomerName);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(Customer.getCustomerNames());
        customerComboBox.setItems(Customer.getCustomerNames());
        customerComboBoxSelector.setItems(Customer.getCustomerNames());

        //Set to equate to the model in order to allow reflection to occur with javabeans (getters, setters, encapsulation)
        appointmentTitleTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentTitle"));
        appointmentDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDescription"));
        appointmentLocationTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentLocation"));
        appointmentContactTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentContact"));
        appointmentTypeTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentType"));
        appointmentUrlTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("url"));
        appointmentStartTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentStart"));
        appointmentEndTableColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentEnd"));


        data = FXCollections.observableArrayList();

        //Note* When working with the resultset - always use the attribute (SQL) names when retrieving data
        try
        {
            ResultSet appointments = Database.getAllAppointments();

            while (appointments.next())
            {
                Appointment readableAppointment = new Appointment();

                System.out.println(appointments.getString("title"));
                System.out.println(appointments.getString("url"));

                readableAppointment.setAppointmentTitle(appointments.getString("title"));
                readableAppointment.setAppointmentDescription(appointments.getString("description"));
                readableAppointment.setAppointmentLocation(appointments.getString("location"));
                readableAppointment.setAppointmentContact(appointments.getString("contact"));
                readableAppointment.setAppointmentType(appointments.getString("type"));
                readableAppointment.setUrl(appointments.getString("url"));
                readableAppointment.setAppointmentStart(appointments.getString("start"));
                readableAppointment.setAppointmentEnd(appointments.getString("end"));

                data.add(readableAppointment);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            appointmentTableView.setItems(data);
        }




    }
}
