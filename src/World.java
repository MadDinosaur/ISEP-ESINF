import java.util.HashSet;
import java.util.Set;

public class World {
    private static Set<Continent> continents = new HashSet<>();
    private static Set<String> continentNames = new HashSet<>();

    public static boolean addContinent(Continent c) {
        return continents.add(c) && continentNames.add(c.getName());
    }

    public static Set<String> continentList() {
        return new HashSet<String>(continentNames);
    }

    public static Set<Continent> getContinents() {
        return new HashSet<Continent>(continents);
    }
}
