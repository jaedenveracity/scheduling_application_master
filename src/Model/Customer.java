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


    public Customer(String userName, String userPhone, String userAddress, String userCity, String userPostal, String userCountry)
    {
        this.customerAddress = new Address(userAddress, userPostal, userPhone);
        this.customerCity = new City(userCity);
        this.customerCountry = new Country(userCountry);
        this.customerName = userName;
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
}
