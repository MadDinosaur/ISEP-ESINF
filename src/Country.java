public class Country {
    private String country;
    private String continent;
    private float population;
    private String capital;
    private float latitude;
    private float longitude;

    public Country(String country, String continent, String population, String capital, String latitude, String longitude) {
        this.country = country.trim();
        this.continent = continent.trim();
        this.population = Float.parseFloat(population.trim());
        this.capital = capital.trim();
        this.latitude = Float.parseFloat(latitude.trim());
        this.longitude = Float.parseFloat(longitude.trim());
    }
    public Country (String name) {
        this.country = name.trim();
        this.continent = "";
        this.population = 0;
        this.capital = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        return country.equalsIgnoreCase(((Country) obj).country);
    }
}
