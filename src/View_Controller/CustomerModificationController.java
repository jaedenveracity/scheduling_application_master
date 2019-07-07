package View_Controller;

import Model.Customer;
import Model.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerModificationController {

    Customer customerToModify = Customer.getModifiableCustomer();

    @FXML private TextField customerNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField addressTwoTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countryTextField;

    @FXML public void initialize ()
    {
        System.out.println(Customer.getModifiableCustomer());

        customerNameTextField.setText(customerToModify.getCustomerName());
        addressTextField.setText(customerToModify.getAddress());
        phoneNumberTextField.setText(customerToModify.getPhoneNumber());
        postalCodeTextField.setText(customerToModify.getPostalCode());
        cityTextField.setText(customerToModify.getCity());
        countryTextField.setText(customerToModify.getCountry());

    }

    public void cancelButtonClicked (ActionEvent actionEvent)
    {
        try
        {
            Parent modifyCustomerSceneParent = FXMLLoader.load(getClass().getResource("customerRecords.fxml"));
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

    public void saveButtonClicked (ActionEvent actionEvent)
    {

        int countryId;
        int cityId;
        int addressId;
        int customerId;



        String customerName = customerNameTextField.getText();
        String customerAddress = addressTextField.getText();
        String customerPhoneNumber = phoneNumberTextField.getText();
        String customerPostalCode = postalCodeTextField.getText();
        String customerCity = cityTextField.getText();
        String customerCountry = countryTextField.getText();

        Customer modifiableCustomer = Customer.getModifiableCustomer();

        countryId = Database.getCountryId(modifiableCustomer.getCustomerCountry());
        cityId = Database.getCityId(modifiableCustomer.getCustomerCity());
        addressId = Database.getAddressId(modifiableCustomer.getCustomerAddress());
        customerId = Database.getCustomerId(modifiableCustomer);


        modifiableCustomer.setCustomerName(customerName);
        modifiableCustomer.setAddress(customerAddress);
        modifiableCustomer.setPhoneNumber(customerPhoneNumber);
        modifiableCustomer.setPostalCode(customerPostalCode);
        modifiableCustomer.setCity(customerCity);
        modifiableCustomer.setCountry(customerCountry);

        Database.modifyCustomer(modifiableCustomer, countryId, cityId, addressId, customerId);

        try
        {
            Parent modifyCustomerSceneParent = FXMLLoader.load(getClass().getResource("customerRecords.fxml"));
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


}
