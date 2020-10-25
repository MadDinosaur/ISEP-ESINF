import java.util.*;

public class World {
    private static Map<String, Continent> continents = new HashMap<>();
<<<<<<< HEAD
=======

>>>>>>> 5828fbe21cbd1837bfa901b9718a9d30ddd8ee36
    public static void addContinent(Continent c) {
        continents.put(c.getName(), c);
    }
    public static Set<String> continentList() {
        return continents.keySet();
    }
<<<<<<< HEAD
    public static Collection<Continent> getContinents() {
        return continents.values();
    }
=======

    public static Collection<Continent> getContinents() {
        return continents.values();
    }

>>>>>>> 5828fbe21cbd1837bfa901b9718a9d30ddd8ee36
    public static Continent get(String name) {
        return continents.get(name);
    }
}
