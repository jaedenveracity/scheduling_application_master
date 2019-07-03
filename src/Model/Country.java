package Model;

public class Country {

    String country;

    public Country (String newCountry)
    {
        this.country = newCountry;
    }

    public Country(){}

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

}
