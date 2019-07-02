package View_Controller;

import Model.Customer;
import Model.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerRecordsController {

    @FXML private TextField customerNameTextField;
    @FXML private TextField customerAddressTextField;
    @FXML private TextField customerPhoneTextField;
    @FXML private TextField customerCityTextField;
    @FXML private TextField customerPostalTextField;
    @FXML private TextField customerCountryTextField;

    //Customer TableView
    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, String> customerNameTableColumn;
    @FXML private TableColumn<Customer, String> customerPhoneTableColumn;
    @FXML private TableColumn<Customer, String> customerAddressTableColumn;
    @FXML private TableColumn<Customer, String> customerCityTableColumn;
    @FXML private TableColumn<Customer, String> customerPostalTableColumn;

    //To hold customer data retrieved from SQL
    private ObservableList<Customer> data;

    public void addCustomerButtonClicked (ActionEvent actionEvent)
    {
        String newUserName = customerNameTextField.getText();
        String newUserAddress = customerAddressTextField.getText();
        String newUserPhone = customerPhoneTextField.getText();
        String newUserCity = customerCityTextField.getText();
        String newUserPostal = customerPostalTextField.getText();
        String newUserCountry = customerCountryTextField.getText();

        Customer newCustomer = new Customer(newUserName, newUserPhone, newUserAddress, newUserCity, newUserPostal, newUserCountry);

        Database.addCustomer(newCustomer);

    }

    @FXML
    public void initialize ()
    {
        customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhone"));
        customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        customerCityTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerCity"));
        customerPostalTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPostal"));

        data = FXCollections.observableArrayList();

        try {
            ResultSet customers = Database.getAllCustomers();

            while (customers.next())
            {
                Customer readableCustomer = new Customer();
                readableCustomer.setCustomerName(customers.getString("customerName"));

                data.add(readableCustomer);

                System.out.println(readableCustomer.getCustomerName());
            }
            customerTableView.setItems(data);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}


