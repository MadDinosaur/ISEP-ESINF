import java.util.*;

public class World {

    private static Map<String, Continent> continents = new HashMap<>();

    //---------------- Getters ----------------
    public static Continent get(String name) {
        return continents.get(name);
    }

    public static Collection<Continent> getContinents() {
        return continents.values();
    }

    public static Set<String> continentList() {
        return continents.keySet();
    }

    //---------------- Public update methods ----------------
    public static void addContinent(Continent c) {
        continents.put(c.getName(), c);
    }

}
