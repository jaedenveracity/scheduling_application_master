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

    public String getCustomerName() {
        return customerName;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public City getCustomerCity() {
        return customerCity;
    }

    public Country getCustomerCountry() {
        return customerCountry;
    }


}
