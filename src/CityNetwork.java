import javafx.util.Pair;

import java.util.*;

public class CityNetwork extends Graph<City, Double> {
    private static Map<String, City> cityList;
    private static Map<String, City> countryList;

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

    public static City getCountry(String country) {
        return countryList.get(country);
    }

    public List<City> getCentralities()
    {
        for(City city : vertices())
        {
            double soma = 0;

            for(City otherCity : vertices())
            {
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

        Collections.sort(centralityList,byCentrality);

        return centralityList;
    }

    public List<City> getUserPercentage(float p,List<City> centralityList, int n) {

        List<City> centralityListAux = new ArrayList<>();
        int count = 0;

        if (centralityList.isEmpty() || n == 0) {
            return centralityListAux;
        }

        for(int i = 0; i<centralityList.size(); i++)
        {
            if(centralityList.get(i).getNumUsers() > p)
            {
                centralityListAux.add(centralityList.get(i));
                count++;
            }

            if(count == n)
            {
                break;
            }
        }

        return centralityListAux;
    }

    /*  public Map<City,Integer> getNumFriendsByCity(List<User> friendsList)
    {
        PriorityQueue<Pair<City,Integer>> pqNumFriendsByCity = new PriorityQueue<>(Comparator.comparing(Pair::getValue));

        for(User user : friendsList)
        {
            City userCity = getCity(user.getCity());

            if(pqNumFriendsByCity.)
            {
                mapNumFriendsByCity.put(userCity,mapNumFriendsByCity.get(userCity) + 1);
            }

            else
            {
                mapNumFriendsByCity.put(userCity,1);
            }

        }
        return mapNumFriendsByCity;
    }
    */

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

    public boolean insertEdge(City vOrig, City vDest) {
        vOrig = getCountry(vOrig.getCountry());
        vDest = getCountry(vDest.getCountry());

        Double edge = vOrig.distanceFrom(vDest);

        return super.insertEdge(vOrig, vDest, edge);
    }

    public List<City> convert(List<String> cityList) {
        List<City> newCityList = new ArrayList<>();
        for (String cityName : cityList) {
            newCityList.add(getCity(cityName));
        }
        return newCityList;
    }
}

