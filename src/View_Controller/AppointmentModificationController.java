package View_Controller;

import Model.Appointment;
import Model.Customer;
import Model.Database;
import Model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentModificationController {

    @FXML ComboBox<String> customerComboBox = new ComboBox<>();

    @FXML TextField titleTextField;
    @FXML TextField descriptionTextField;
    @FXML TextField locationTextField;
    @FXML TextField contactTextField;
    @FXML TextField typeTextField;
    @FXML TextField urlTextField;
    @FXML TextField startTextField;
    @FXML TextField endTextField;

    @FXML Label currentCustomerLabel;


    public void initialize()
    {
        int appointmentId = Database.getAppointmentId(Appointment.getModifiableAppointment());
        String customerName = Database.getCustomerFromAppointmentId(appointmentId);

        System.out.println("Customer that the appointment belongs to: " + customerName);

        customerComboBox.getItems().clear();
        customerComboBox.setItems(Customer.getCustomerNames());
        customerComboBox.setPromptText("Optional");

        titleTextField.setText(Appointment.getModifiableAppointment().getAppointmentTitle());
        descriptionTextField.setText(Appointment.getModifiableAppointment().getAppointmentDescription());
        locationTextField.setText(Appointment.getModifiableAppointment().getAppointmentLocation());
        contactTextField.setText(Appointment.getModifiableAppointment().getAppointmentContact());
        typeTextField.setText(Appointment.getModifiableAppointment().getAppointmentType());
        urlTextField.setText(Appointment.getModifiableAppointment().getUrl());
        startTextField.setText(Appointment.getModifiableAppointment().getAppointmentStart());
        endTextField.setText(Appointment.getModifiableAppointment().getAppointmentEnd());

        currentCustomerLabel.setText(customerName);

    }

    public void saveAppointmentButtonClicked (ActionEvent actionEvent)
    {
        int appointmentId = Database.getAppointmentId(Appointment.getModifiableAppointment());
        int userId = Database.getUserId(UserSession.getInstance());
        String customerName = Database.getCustomerFromAppointmentId(appointmentId);
        int customerId = Database.getCustomerId(customerName);

        System.out.println("Database appointment id is: " + appointmentId);
        System.out.println("Database user id is: " + userId);

        String newAppointmentTitle = titleTextField.getText();
        String newAppointmentDescription = descriptionTextField.getText();
        String newAppointmentLocation = locationTextField.getText();
        String newAppointmentContact = contactTextField.getText();
        String newAppointmentType = typeTextField.getText();
        String newAppointmentUrl = urlTextField.getText();
        String newAppointmentStart = startTextField.getText();
        String newAppointmentEnd = endTextField.getText();

        if (customerComboBox.getSelectionModel().isEmpty())
        {
            System.out.println("Updated customer not selected for chosen appointment");

        }
        else
        {
            String updatedCustomerName = customerComboBox.getSelectionModel().getSelectedItem();
            customerId =  Database.getCustomerId(updatedCustomerName);
        }

        System.out.println("Database customer id is: " + customerId);

        Appointment updatableAppointment = new Appointment(newAppointmentTitle, newAppointmentDescription, newAppointmentLocation, newAppointmentContact, newAppointmentType, newAppointmentUrl, newAppointmentStart, newAppointmentEnd);

        Database.updateAppointment(updatableAppointment, appointmentId, userId, customerId);

    }

    public void cancelAppointmentButtonClicked (ActionEvent actionEvent)
    {
        Customer.getCustomerNames().clear();
        try
        {
            Parent modifyAppointmentSceneParent = FXMLLoader.load(getClass().getResource("AppointmentRecords.fxml"));
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
