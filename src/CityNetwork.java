import javafx.util.Pair;

import java.util.*;

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

    public List<City> getCentralities(int n)
    {
        Comparator<City> byCentrality = new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return Double.compare(o1.getCentrality(), o2.getCentrality());
            }
        };
            List<City> centralityList = new ArrayList<City>(vertices());

            Collections.sort(centralityList,byCentrality);

            return centralityList.subList(0,n);
    }

    public List<City> getUserPercentage(float p,List<City> centralityList) {

        if(centralityList.isEmpty())
        {
            return centralityList;
        }

        p = p/100 * numVertices();

        Comparator<City> byNumUsers = new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return -Integer.compare(o1.getNumUsers(),o2.getNumUsers());
            }
        };

        Collections.sort(centralityList,byNumUsers);

        for(int i = 0; i<centralityList.size(); i++)
        {
            if(centralityList.get(i).getNumUsers() < p)
            {
                return centralityList.subList(0,i-1);
            }
        }

        return centralityList;
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

            for(City city : vertices())
            {
                city.updateCentrality(city.distanceFrom(newVert),numVertices());
            }
        }
        return inserted;
    }

    @Override
    public boolean removeVertex(City vert) {
        boolean removed = super.removeVertex(vert);
        if (removed) {
            cityList.remove(vert.getCity());
            for(City city : vertices())
            {
                city.updateCentrality(-city.distanceFrom(vert),numVertices());
            }
        }
        return removed;
    }

    @Override
    public boolean insertEdge(City vOrig, City vDest, Double edge) {
        vOrig = getCountry(vOrig.getCountry());
        vDest = getCountry(vDest.getCountry());
        return super.insertEdge(vOrig, vDest, edge);
    }
}

