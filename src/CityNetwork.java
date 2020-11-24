import java.util.List;

public class CityNetwork extends Graph<Country,Double> {
    private Graph<Country, Double> cityNetwork;

    public CityNetwork() {
        cityNetwork = new Graph<Country, Double>();
    }

    public List<Country> getCountriesByBorders (Country newCountry, int n)
    {
        return this.breadthFirstSearch_Layers(newCountry,n);
    }

    public Country getCountry (String city)
    {
        for(Country countryUser : vertices())
        {
            if (countryUser.getCountry().equalsIgnoreCase(city))
            {
                return countryUser;
            }
        }

        return null;
    }
}
