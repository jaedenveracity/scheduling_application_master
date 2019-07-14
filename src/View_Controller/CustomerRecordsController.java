package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML private TableColumn<Customer, String> customerCountryTableColumn;

    @FXML private Label invalidCustomerDataLabel;

    //To hold customer data retrieved from SQL
    private ObservableList<Customer> data;

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

    public void addCustomerButtonClicked (ActionEvent actionEvent)
    {
        String newUserName = customerNameTextField.getText();
        String newUserAddress = customerAddressTextField.getText();
        String newUserPhone = customerPhoneTextField.getText();
        String newUserCity = customerCityTextField.getText();
        String newUserPostal = customerPostalTextField.getText();
        String newUserCountry = customerCountryTextField.getText();

            try {
                if (newUserName.isEmpty() || newUserAddress.isEmpty() || newUserPhone.isEmpty() || newUserCity.isEmpty() || newUserPostal.isEmpty() || newUserCountry.isEmpty()) {
                    invalidCustomerDataLabel.setVisible(true);
                    throw new Exception();
                }


                int cityId;
                int addressId;
                int countryId;


                //Create city first add to database, find that city's id and set the address fk to that, then create address next and add to database, find that addresses id and set the customer fk to that

                Country newCountry = new Country(newUserCountry);

                countryId = Database.getCountryId(newCountry);

                City newCity = new City(newUserCity);

                cityId = Database.getCityId(newCity);

                Address newAddress = new Address(newUserAddress, newUserPostal, newUserPhone, cityId);

                addressId = Database.getAddressId(newAddress);

                Customer newCustomer = new Customer(newUserName, newCountry, newCity, newAddress, addressId);

                Database.addCustomer(newCustomer);
                data.add(newCustomer);

                customerTableView.refresh();

                customerNameTextField.clear();
                customerAddressTextField.clear();
                customerPhoneTextField.clear();
                customerCityTextField.clear();
                customerPostalTextField.clear();
                customerCountryTextField.clear();

                invalidCustomerDataLabel.setVisible(false);

            }
            catch(Exception e)
            {
                System.out.println("One or more fields are empty - please enter all fields and try again");
            }

    }

    public void refreshButtonClicked (ActionEvent actionEvent)
    {
        customerTableView.refresh();

        //TODO: Use similar syntax as the initialize to refresh the data
    }

    public void deleteCustomerButtonClicked (ActionEvent actionEvent)
    {
        Customer tempCustomer = customerTableView.getSelectionModel().getSelectedItem();

        System.out.println(tempCustomer.getCustomerName());
        data.remove(tempCustomer);

        //TODO: Syntax to delete customer from database

        Database.deleteCustomer(tempCustomer.getCustomerName());
    }

    public void modifyCustomerButtonClicked (ActionEvent actionEvent)
    {
        Customer.setModifiableCustomer(customerTableView.getSelectionModel().getSelectedItem());

        try
        {
            Parent modifyCustomerSceneParent = FXMLLoader.load(getClass().getResource("CustomerModification.fxml"));
            Scene modifyCustomerScene = new Scene(modifyCustomerSceneParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(modifyCustomerScene);
            window.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize ()
    {
        customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerCityTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
        customerPostalTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        customerCountryTableColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));

        invalidCustomerDataLabel.setVisible(false);

        data = FXCollections.observableArrayList();

        try {
            ResultSet customers = Database.getAllCustomers();

            while (customers.next())
            {
                Customer readableCustomer = new Customer();

                /*
                Address readableAddress = new Address();
                City readableCity = new City();
                Country readableCountry = new Country();

                readableCustomer.setCustomerAddress(readableAddress);
                //readableCustomer.setCustomerCity(readableCity)
                */

                readableCustomer.setCustomerName(customers.getString("customerName"));
                readableCustomer.setAddress(customers.getString("address"));
                readableCustomer.setPhoneNumber(customers.getString("phone"));
                readableCustomer.setPostalCode(customers.getString("postalCode"));
                readableCustomer.setCity(customers.getString("city"));
                readableCustomer.setCountry(customers.getString("country"));



                //System.out.println(readableCustomer.getCustomerName());
                data.add(readableCustomer);




            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            customerTableView.setItems(data);
        }
                //readableCustomer.getCustomerAddress().setAddress(addresses.getString("address"));


    }
}




