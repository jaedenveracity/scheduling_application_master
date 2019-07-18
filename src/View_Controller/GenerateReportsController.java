package View_Controller;

import Model.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateReportsController {

    @FXML ListView<String> monthTypeListView;
    @FXML ListView<String> presentationTypeListView;
    @FXML ListView<String> typeCountListView;

    ObservableList<String> monthTypeData = FXCollections.observableArrayList();
    ObservableList<String> presentationTypeData = FXCollections.observableArrayList();
    ObservableList<String> typeCountData = FXCollections.observableArrayList();

    //User Schedule Report
    @FXML ListView<String> appointmentTitleListView;
    @FXML ListView<String> appointmentStartListView;
    @FXML ListView<String> appointmentEndListView;

    ObservableList<String> appointmentTitleData = FXCollections.observableArrayList();
    ObservableList<String> appointmentStartData = FXCollections.observableArrayList();
    ObservableList<String> appointmentEndData = FXCollections.observableArrayList();

    //Month Appointment Count Report
    @FXML ListView<String> monthAppointmentCountListView;
    @FXML ListView<String> appointmentCountListView;

    ObservableList<String> monthAppointmentCountData = FXCollections.observableArrayList();
    ObservableList<String> appointmentCountData = FXCollections.observableArrayList();

    public void initialize()
    {
        ResultSet monthlyPresentationTypesResultSet = null;
        monthlyPresentationTypesResultSet = Database.reportMonthlyPresentationTypes();

        ResultSet consultantAppointmentSchedule = null;
        consultantAppointmentSchedule = Database.reportConsultantSchedule();

        ResultSet monthlyAppointmentCount = null;
        monthlyAppointmentCount = Database.reportMonthlyAppointmentCount();

        try {
                while (monthlyPresentationTypesResultSet.next()) {

                String checkedMonth = monthlyPresentationTypesResultSet.getString("month");
                String checkedType = monthlyPresentationTypesResultSet.getString("type");
                String checkedTotal = monthlyPresentationTypesResultSet.getString("total");

                monthTypeData.add(checkedMonth);
                presentationTypeData.add(checkedType);
                typeCountData.add(checkedTotal);

                while (consultantAppointmentSchedule.next()) {
                    String checkedTitle = consultantAppointmentSchedule.getString("title");
                    String checkedStart = consultantAppointmentSchedule.getString("start");
                    String checkedEnd = consultantAppointmentSchedule.getString("end");

                    appointmentTitleData.add(checkedTitle);
                    appointmentStartData.add(checkedStart);
                    appointmentEndData.add(checkedEnd);
                }

                while (monthlyAppointmentCount.next())
                {
                    String curMonth = monthlyAppointmentCount.getString("month");
                    String appointmentCount = monthlyAppointmentCount.getString("num_appointments");

                    monthAppointmentCountData.add(curMonth);
                    appointmentCountData.add(appointmentCount);

                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }




        monthTypeListView.setItems(monthTypeData);
        presentationTypeListView.setItems(presentationTypeData);
        typeCountListView.setItems(typeCountData);

        appointmentTitleListView.setItems(appointmentTitleData);
        appointmentStartListView.setItems(appointmentStartData);
        appointmentEndListView.setItems(appointmentEndData);

        monthAppointmentCountListView.setItems(monthAppointmentCountData);
        appointmentCountListView.setItems(appointmentCountData);

    }
}
