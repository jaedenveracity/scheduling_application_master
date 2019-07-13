package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

    private SimpleStringProperty appointmentTitle;
    private SimpleStringProperty appointmentDescription;
    private SimpleStringProperty appointmentLocation;
    private SimpleStringProperty appointmentContact;
    private SimpleStringProperty appointmentType;
    private SimpleStringProperty url;
    private SimpleStringProperty appointmentStart;
    private SimpleStringProperty appointmentEnd;

    static private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    static private Appointment modifiableAppointment = null;

    //Must instantiate the property objects before using - as they are not primitive and the objects must be created that encapsulate the primitive strings and have listeners built in
    public Appointment ()
    {

        this.appointmentTitle = new SimpleStringProperty();
        this.appointmentDescription = new SimpleStringProperty();
        this.appointmentLocation = new SimpleStringProperty();
        this.appointmentContact = new SimpleStringProperty();
        this.appointmentType = new SimpleStringProperty();
        this.url = new SimpleStringProperty();
        this.appointmentStart = new SimpleStringProperty();
        this.appointmentEnd = new SimpleStringProperty();
    }

    public Appointment (String newTitle, String newDescription, String newLocation, String newContact, String newType, String newUrl, String newStart, String newEnd)
    {
        this.appointmentTitle = new SimpleStringProperty(newTitle);
        this.appointmentDescription = new SimpleStringProperty(newDescription);
        this.appointmentLocation = new SimpleStringProperty(newLocation);
        this.appointmentContact = new SimpleStringProperty(newContact);
        this.appointmentType = new SimpleStringProperty(newType);
        this.url = new SimpleStringProperty(newUrl);
        this.appointmentStart = new SimpleStringProperty(newStart);
        this.appointmentEnd = new SimpleStringProperty(newEnd);
    }

    public Appointment (String newTitle, String newDescription, String newLocation, String newContact)
    {
        this.appointmentTitle = new SimpleStringProperty(newTitle);
        this.appointmentDescription = new SimpleStringProperty(newDescription);
        this.appointmentLocation = new SimpleStringProperty(newLocation);
        this.appointmentContact = new SimpleStringProperty(newContact);
    }

    public static Appointment getModifiableAppointment() {
        return modifiableAppointment;
    }

    public static void setModifiableAppointment(Appointment modifiableAppointment) {
        Appointment.modifiableAppointment = modifiableAppointment;
    }

    public static void addAppointment(Appointment newAppointment)
    {
        allAppointments.add(newAppointment);
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        Appointment.allAppointments = allAppointments;
    }

    public static void checkAppointmentConflicts (Appointment newAppointment)
    {

        String newApptStart = newAppointment.getAppointmentStart();
        String newApptEnd = newAppointment.getAppointmentEnd();

        String[] newApptStartSplit = newApptStart.split(" ");

        String newApptStartDate = newApptStartSplit[0];
        String newApptStartTime = newApptStartSplit[1];



        try {
            ResultSet appointments = Database.getAllAppointments();

            while (appointments.next())
            {


            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not retrieve all appointments from database");
        }


    }

    public String getAppointmentTitle() {
        return appointmentTitle.get();
    }

    public SimpleStringProperty appointmentTitleProperty() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle.set(appointmentTitle);
    }

    public String getAppointmentDescription() {
        return appointmentDescription.get();
    }

    public SimpleStringProperty appointmentDescriptionProperty() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription.set(appointmentDescription);
    }

    public String getAppointmentLocation() {
        return appointmentLocation.get();
    }

    public SimpleStringProperty appointmentLocationProperty() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation.set(appointmentLocation);
    }

    public String getAppointmentContact() {
        return appointmentContact.get();
    }

    public SimpleStringProperty appointmentContactProperty() {
        return appointmentContact;
    }

    public void setAppointmentContact(String appointmentContact) {
        this.appointmentContact.set(appointmentContact);
    }

    public String getAppointmentType() {
        return appointmentType.get();
    }

    public SimpleStringProperty appointmentTypeProperty() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    public String getAppointmentStart() {
        return appointmentStart.get();
    }

    public void setAppointmentStart(String appointmentStart) {
        this.appointmentStart.set(appointmentStart);
    }

    public SimpleStringProperty appointmentStartProperty() {
        return appointmentStart;
    }

    public String getAppointmentEnd() {
        return appointmentEnd.get();
    }

    public void setAppointmentEnd(String appointmentEnd) {
        this.appointmentEnd.set(appointmentEnd);
    }

    public SimpleStringProperty appointmentEndProperty() {
        return appointmentEnd;
    }

    public String getUrl() {
        return url.get();
    }

    public SimpleStringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }
}


