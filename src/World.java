import java.util.*;

/**
 * Classe que modela o Mundo e agrega um conjunto de Continentes.
 */
public class World {
    /**
     * Mapa de continentes indexados pelo nome.
     */
    private static Map<String, Continent> continents = new HashMap<>();

    //---------------- Getters ----------------

    /**
     * @param name O nome do continente.
     * @return O continente, em formato Continent.
     */
    public static Continent get(String name) {
        return continents.get(name);
    }

    /**
     * @return A lista de objetos Continent contidos na classe.
     */
    public static Collection<Continent> getContinents() {
        return continents.values();
    }

    /**
     * @return A lista de nomes de continentes contidos na classe.
     */
    public static Set<String> continentList() {
        return continents.keySet();
    }

    //---------------- Public update methods ----------------

    /**
     * Adiciona um continente à classe.
     *
     * @param c O objeto Continent a adicionar.
     */
    public static void addContinent(Continent c) {
        continents.put(c.getName(), c);
    }

}
