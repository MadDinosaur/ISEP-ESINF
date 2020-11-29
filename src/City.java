import java.util.Objects;

public class City {
    private String country;
    private String continent;
    private float population;
    private String capital;
    private float latitude;
    private float longitude;
    private int numUsers;
    private double centrality;

    public City(String country, String continent, String population, String capital, String latitude, String longitude) {
        this.country = country.trim();
        this.continent = continent.trim();
        this.population = Float.parseFloat(population.trim());
        this.capital = capital.trim();
        this.latitude = Float.parseFloat(latitude.trim());
        this.longitude = Float.parseFloat(longitude.trim());
        numUsers = 0;
        setCentrality(0);
    }

    public City(String name) {
        this.country = name.trim();
        this.continent = "";
        this.population = 0;
        this.capital = "";
        this.latitude = 0;
        this.longitude = 0;
        numUsers = 0;
        setCentrality(0);
    }

    public double distanceFrom(City otherCity) {
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = otherCity.latitude;
        double lon2 = otherCity.longitude;

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to km

        return distance;
    }

    public void addUser()
    {
        numUsers++;
    }


    @Override

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return getCountry().equalsIgnoreCase(((City) obj).getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, continent, capital);
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return capital;
    }

    public int getNumUsers(){return numUsers;}

    public double getCentrality() {
        return centrality;
    }

    public void setCentrality(double centrality) {
        this.centrality = centrality;
    }
}
