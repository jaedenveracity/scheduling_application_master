package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City
{
    private SimpleStringProperty city;

    public City (String newCity)
    {
        this.city = new SimpleStringProperty(newCity);
    }

    public City ()
    {
        this.city = new SimpleStringProperty();
    };

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty()
    {
        return city;
    }
}

