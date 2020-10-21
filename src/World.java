import java.util.HashSet;
import java.util.Set;

public class World {
    private static Set<Continent> continentList = new HashSet<Continent>();
    private static Set<String> continentNames = new HashSet<>();

    public static boolean addContinent(Continent c) {
        return continentList.add(c) && continentNames.add(c.getName());
    }

    public static Set<String> getContinents() {
        return new HashSet<String>(continentNames);
    }
}
