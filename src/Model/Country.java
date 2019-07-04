package Model;

import javafx.beans.property.SimpleStringProperty;

public class Country {

    SimpleStringProperty country;

    public Country (String newCountry)
    {
        this.country = new SimpleStringProperty(newCountry);
    }

    public Country()
    {
        this.country = new SimpleStringProperty();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty()
    {
        return country;
    }

}
