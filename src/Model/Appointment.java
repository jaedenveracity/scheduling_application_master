package Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {

    private SimpleStringProperty appointmentTitle;
    private SimpleStringProperty appointmentDescription;
    private SimpleStringProperty appointmentLocation;
    private SimpleStringProperty appointmentContact;
    private SimpleStringProperty appointmentType;
    private SimpleStringProperty appointmentUrl;

    static private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    //Must instantiate the property objects before using - as they are not primitive and the objects must be created that encapsulate the primitive strings and have listeners built in
    public Appointment ()
    {

        this.appointmentTitle = new SimpleStringProperty();
        this.appointmentDescription = new SimpleStringProperty();
        this.appointmentLocation = new SimpleStringProperty();
        this.appointmentContact = new SimpleStringProperty();
        //this.appointmentType = new SimpleStringProperty();
        //this.appointmentUrl = new SimpleStringProperty();
    }

    public Appointment (String newTitle, String newDescription, String newLocation, String newContact)
    {
        this.appointmentTitle = new SimpleStringProperty(newTitle);
        this.appointmentDescription = new SimpleStringProperty(newDescription);
        this.appointmentLocation = new SimpleStringProperty(newLocation);
        this.appointmentContact = new SimpleStringProperty(newContact);
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

    public String getAppointmentUrl() {
        return appointmentUrl.get();
    }

    public SimpleStringProperty appointmentUrlProperty() {
        return appointmentUrl;
    }

    public void setAppointmentUrl(String appointmentUrl) {
        this.appointmentUrl.set(appointmentUrl);
    }
}


