package Model;

public class Address {

    String address;
    String addressTwo = "None";
    String postalCode;
    String phoneNumber;

    public Address (String newAddress, String newPostalCode, String newPhoneNumber)
    {
        this.address = newAddress;
        this.postalCode = newPostalCode;
        this.phoneNumber = newPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddressTwo() {
        return addressTwo;
    }
}
