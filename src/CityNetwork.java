import java.util.*;

/**
 * Classe que modela uma rede de cidades, ligadas por fronteiras
 */
public class CityNetwork extends Graph<City, Double> {
    /**
     * Mapa dos objetos City que compõem o grafo, indexados pelo seu nome.
     * Permite a pesquisa de cidades pelo nome em O(1).
     */
    private static Map<String, City> cityList;
    /**
     * Mapa dos objetos City que compõem o grafo, indexados pelo nome do país a que pertencem.
     * Permite a pesquisa de cidades pelo seu país em O(1).
     */
    private static Map<String, City> countryList;
    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor da classe CityNetwork.
     */
    public CityNetwork() {
        super();
        cityList = new HashMap<>();
        countryList = new HashMap<>();
    }
    //------------------------------- Métodos estáticos ---------------------------------

    /**
     * Método para obter o objeto City correspondente a partir do nome da cidade.
     *
     * @param city o nome da cidade.
     * @return o objeto City correspondente.
     */
    public static City getCity(String city) {
        return cityList.get(city);
    }

    /**
     * Método para obter o objeto City correspondente a partir do nome do país.
     *
     * @param country o nome da país
     * @return o objeto City correspondente.
     */
    public static City getCountry(String country) {
        return countryList.get(country);
    }

    /**
     * Método que converte uma lista de nomes de cidades em lista de objetos City correspondentes.
     *
     * @param cityList a lista de nomes de cidades.
     * @return lista de objetos City.
     */
    public static List<City> convert(List<String> cityList) {
        List<City> newCityList = new ArrayList<>();
        for (String cityName : cityList) {
            newCityList.add(getCity(cityName));
        }
        return newCityList;
    }
    //------------------------------- Métodos de instância ---------------------------------

    /**
     * Método para obter as cidades que distam até @n fronteiras da cidade pretendida.
     *
     * @param newCity a cidade origem para o cálculo do nº de fronteiras.
     * @param n       o nº de fronteiras.
     * @return a lista de cidades que distam até @n fronteiras de @newCity.
     */
    public List<City> getCitiesByBorders(City newCity, int n) {
        return this.searchByLayers(newCity, n);
    }

    /**
     * Método para obter as centralidades de todas as cidades no grafo.
     * A centralidade é dada pela somas das distâncias geográficas de cada cidade a todas as outras cidades.
     *
     * @return a lista das cidades do grafo, ordenadas por ordem crescente de centralidade (das mais centrais para as menos centrais).
     */
    public List<City> getCentralities() {
        for (City city : vertices()) {
            double soma = 0;

            for (City otherCity : vertices()) {
                soma += city.distanceFrom(otherCity);
            }

            city.setCentrality(soma);
        }

        Comparator<City> byCentrality = new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return Double.compare(o1.getCentrality(), o2.getCentrality());
            }
        };

        List<City> centralityList = new ArrayList<City>(vertices());

        Collections.sort(centralityList, byCentrality);

        return centralityList;
    }

    /**
     * Método para obter as @n cidades com percentagem de utilizadores da rede social que nela habitam superior a @p.
     *
     * @param p              o número de utilizadores mínimo.
     * @param centralityList a lista de cidades.
     * @param n              o número de cidades a retornar.
     * @return a lista de @n cidades com nº de utilizadores superior a @p, seguindo a ordem da lista de cidades passada como parâmetro.
     */
    public List<City> getUserPercentage(float p, List<City> centralityList, int n) {

        List<City> centralityListAux = new ArrayList<>();
        int count = 0;

        if (centralityList.isEmpty() || n == 0) {
            return centralityListAux;
        }

        for (int i = 0; i < centralityList.size(); i++) {
            if (centralityList.get(i).getNumUsers() > p) {
                centralityListAux.add(centralityList.get(i));
                count++;
            }

            if (count == n) {
                break;
            }
        }

        return centralityListAux;
    }
    //------------------------------- Métodos herdados ---------------------------------

    /**
     * Método alternativo ao insertEdge da classe Graph, que calcula o valor da "edge" automaticamente
     *
     * @param vOrig a cidade de origem.
     * @param vDest a cidade de destino.
     * @return true, caso a inserção seja bem sucedida, false caso contrário.
     */
    public boolean insertEdge(City vOrig, City vDest) {
        if (vOrig == null || vDest == null) return false;
        vOrig = getCountry(vOrig.getCountry());
        vDest = getCountry(vDest.getCountry());
        if (!validVertex(vOrig) || !validVertex(vDest)) return false;
        Double edge = vOrig.distanceFrom(vDest);

        return super.insertEdge(vOrig, vDest, edge);
    }

    @Override
    public boolean insertVertex(City newVert) {
        boolean inserted = super.insertVertex(newVert);
        if (inserted) {
            cityList.put(newVert.getCity(), newVert);
            countryList.put(newVert.getCountry(), newVert);
        }
        return inserted;
    }

    @Override
    public boolean removeVertex(City vert) {
        boolean removed = super.removeVertex(vert);
        if (removed) {
            cityList.remove(vert.getCity());
        }
        return removed;
    }
}

