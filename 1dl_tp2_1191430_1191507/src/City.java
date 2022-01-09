import java.util.Objects;

/**
 * Classe que modela uma Cidade, com a respetiva informação geográfica e de volume de utilizadores
 */
public class City {
    /**
     * Nome do país.
     */
    private String country;
    /**
     * Nome do continente.
     */
    private String continent;
    /**
     * Número de habitantes.
     */
    private float population;
    /**
     * Cidade capital do país.
     */
    private String capital;
    /**
     * Latitude das coordenadas geográficas do país.
     */
    private float latitude;
    /**
     * Longitude das coordenadas geográficas do país.
     */
    private float longitude;
    /**
     * Número de utilizadores da rede social que habitam na cidade.
     */
    private int numUsers;
    /**
     * Soma das distâncias da cidade a todas as outras cidades inseridas na CityNetwork.
     */
    private double centrality;
    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor completo da classe City.
     *
     * @param country    O nome do país.
     * @param continent  O nome do continente.
     * @param population O nº de habitantes.
     * @param capital    O nome da cidade.
     * @param latitude   A latitude do país.
     * @param longitude  A longitude do país.
     */
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

    /**
     * Construtor parcial da classe City.
     * Deve ser utilizado para criar cidades "cópia" a ser passadas como parâmetro para o registo de fronteiras entre países.
     *
     * @param name O nome do país.
     */
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
    //------------------------------- Getters e Setters ---------------------------------

    /**
     * @return nome do país.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return nome da cidade.
     */
    public String getCity() {
        return capital;
    }

    /**
     * @return número de utilizadores da rede social que habitam na cidade.
     */
    public int getNumUsers() {
        return numUsers;
    }

    /**
     * @return a soma das distâncias geográfica da cidade a todas as outras.
     */
    public double getCentrality() {
        return centrality;
    }

    /**
     * Modifica o valor da centralidade da cidade.
     *
     * @param centrality a soma das distâncias geográficas da cidade a todas as outras.
     */
    public void setCentrality(double centrality) {
        this.centrality = centrality;
    }
    //------------------------------- Métodos ---------------------------------

    /**
     * Obtém a distância geográfica, calculada através das coordenadas geográficas, entre duas cidades.
     *
     * @param otherCity Objeto City que representa a cidade de destino.
     * @return distância geográfica, em km.
     */
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

    /**
     * Incrementa o nº de utilizadores da rede social registados na cidade.
     */
    public void addUser() {
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
}
