import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityNetwork extends Graph<City, Double> {
    private static Map<String, City> cityList;
    private Map<String, City> countryList;

    public CityNetwork() {
        super();
        cityList = new HashMap<>();
        countryList = new HashMap<>();
    }

    public List<City> getCitiesByBorders(City newCity, int n) {
        return this.searchByLayers(newCity, n);
    }

    public static City getCity(String city) {
        return cityList.get(city);
    }

    public City getCountry(String country) {
        return countryList.get(country);
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
        if (removed) cityList.remove(vert.getCity());
        return removed;
    }

    @Override
    public boolean insertEdge(City vOrig, City vDest, Double edge) {
        vOrig = getCountry(vOrig.getCountry());
        vDest = getCountry(vDest.getCountry());
        return super.insertEdge(vOrig, vDest, edge);
    }
}

