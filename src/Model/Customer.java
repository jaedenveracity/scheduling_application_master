package Model;

import java.sql.*;
import Model.Database;
import Model.City;
import Model.Address;

public class Customer
{
    String customerName;
    Address customerAddress;
    City customerCity;
    Country customerCountry;
    int addressId;


    public Customer(String userName, String userPhone, String userAddress, String userCity, String userPostal, String userCountry)
    {
        this.customerAddress = new Address(userAddress, userPostal, userPhone);
        this.customerCity = new City(userCity);
        this.customerCountry = new Country(userCountry);
        this.customerName = userName;
    }

    public Customer(String userName, String userPhone, String userAddress, String userCity, String userPostal, String userCountry, int addressId)
    {
        this.customerAddress = new Address(userAddress, userPostal, userPhone);
        this.customerCity = new City(userCity);
        this.customerCountry = new Country(userCountry);
        this.customerName = userName;
        this.addressId = addressId;
    }

    public Customer(String userName, City newCity, Address newAddress)
    {
        this.customerName = userName;
        this.customerAddress = newAddress;
        this.customerCity = newCity;
    }

    public Customer(String userName, City newCity, Address newAddress, int addressId)
    {
        this.customerName = userName;
        this.customerAddress = newAddress;
        this.customerCity = newCity;
        this.addressId = addressId;
    }


    public Customer()
    {

    };

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public City getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(City customerCity) {
        this.customerCity = customerCity;
    }

    public Country getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(Country customerCountry) {
        this.customerCountry = customerCountry;
    }

    public void setAddress (String newAddress)
    {
        this.getCustomerAddress().setAddress(newAddress);
    }

    public String getAddress ()
    {
        return this.getCustomerAddress().getAddress();
    }

    public int getAddressId() {
        return addressId;
    }
}
