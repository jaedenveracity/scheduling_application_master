package Model;

public class Address {

    String address;
    String addressTwo = "None";
    String postalCode;
    String phoneNumber;
    int cityId;

    public Address (String newAddress, String newPostalCode, String newPhoneNumber)
    {
        this.address = newAddress;
        this.postalCode = newPostalCode;
        this.phoneNumber = newPhoneNumber;
    }

    public Address (String newAddress, String newPostalCode, String newPhoneNumber, int newCityId)
    {
        this.address = newAddress;
        this.postalCode = newPostalCode;
        this.phoneNumber = newPhoneNumber;
        this.cityId = newCityId;
    }

    public Address ()
    {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public int getCityId() {
        return cityId;
    }
}
