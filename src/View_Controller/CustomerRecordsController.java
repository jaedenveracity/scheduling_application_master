package View_Controller;

import Model.Customer;
import Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomerRecordsController {

    @FXML TextField customerNameTextField;
    @FXML TextField customerAddressTextField;
    @FXML TextField customerPhoneTextField;
    @FXML TextField customerCityTextField;
    @FXML TextField customerPostalTextField;
    @FXML TextField customerCountryTextField;

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

}


