package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Address {

    private SimpleStringProperty address;
    private SimpleStringProperty addressTwo = new SimpleStringProperty("None");
    private SimpleStringProperty postalCode;
    private SimpleStringProperty phoneNumber;
    private SimpleIntegerProperty cityId;

    public Address (String newAddress, String newPostalCode, String newPhoneNumber)
    {
        this.address = new SimpleStringProperty(newAddress);
        this.postalCode = new SimpleStringProperty(newPostalCode);
        this.phoneNumber = new SimpleStringProperty(newPhoneNumber);
    }

    public Address (String newAddress, String newPostalCode, String newPhoneNumber, int newCityId)
    {
        this.address = new SimpleStringProperty(newAddress);
        this.postalCode = new SimpleStringProperty(newPostalCode);
        this.phoneNumber = new SimpleStringProperty(newPhoneNumber);
        this.cityId = new SimpleIntegerProperty(newCityId);
    }

    public Address ()
    {
        this.address = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.cityId = new SimpleIntegerProperty();

    }

    //Address Getters/Setters
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty addressProperty()
    {
        return address;
    }

    //Postal Code Getters/Setters
    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public SimpleStringProperty postalCodeProperty()
    {
        return postalCode;
    }

    //Phone Number Getters/Setters
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public SimpleStringProperty phoneNumberProperty ()
    {
        return phoneNumber;
    }


    public String getAddressTwo() {
        return addressTwo.get();
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo.set(addressTwo);
    }

    public int getCityId() {
        return cityId.get();
    }
}
