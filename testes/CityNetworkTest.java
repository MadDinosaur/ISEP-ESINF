import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//TODO Code coverage check
class CityNetworkTest {
    String USER_FILE = "small-network/susers.txt";
    String RELATIONSHIP_FILE = "small-network/srelationships.txt";
    String COUNTRY_FILE = "small-network/scountries.txt";
    String BORDERS_FILE = "small-network/sborders.txt";

    public CityNetworkTest() throws IOException {
        Main.readFile(COUNTRY_FILE, 3);
        Main.readFile(BORDERS_FILE, 4);
        Main.readFile(USER_FILE, 1);
        Main.readFile(RELATIONSHIP_FILE, 2);
    }

    @Test
    void numVertices() {
        int expected = 13;
        int actual = Main.cityNetwork.numVertices();
        assertEquals(expected, actual);
    }

    @Test
    void vertices() {
        Set<City> expected = new HashSet<>();
        expected.add(CityNetwork.getCity("buenosaires"));
        expected.add(CityNetwork.getCity("lapaz"));
        expected.add(CityNetwork.getCity("brasilia"));
        expected.add(CityNetwork.getCity("lima"));
        expected.add(CityNetwork.getCity("santiago"));
        expected.add(CityNetwork.getCity("bogota"));
        expected.add(CityNetwork.getCity("quito"));
        expected.add(CityNetwork.getCity("paramaribo"));
        expected.add(CityNetwork.getCity("montevideu"));
        expected.add(CityNetwork.getCity("georgetwon"));
        expected.add(CityNetwork.getCity("caiena"));
        expected.add(CityNetwork.getCity("assuncao"));
        expected.add(CityNetwork.getCity("caracas"));
        Set<City> actual = Main.cityNetwork.vertices();
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void numEdges() {
        int expected = 25;
        int actual = Main.cityNetwork.numEdges();
        assertEquals(expected, actual);
    }

    //TODO Confirmar valores das distâncias com coordenadas
    @Test
    void edges() {
        Set<Double> expected = new HashSet<>();
        expected.add(1037951.0260178599);
        expected.add(2160365.597442246);
        expected.add(346348.20645409526);
        expected.add(1028103.4680938737);
        expected.add(1902063.9490390944);
        expected.add(2467779.598965711);
        expected.add(2236888.7384291743);
        expected.add(330381.1594867405);
        expected.add(1323925.8491831454);
        expected.add(2353640.710252978);
        expected.add(1137301.5364568012);
        expected.add(3589294.1832363578);
        expected.add(3165425.5955088856);
        expected.add(1077171.159246151);
        expected.add(2280256.99168126);
        expected.add(2752138.7939006393);
        expected.add(1462395.4121815956);
        expected.add(202561.19155364987);
        expected.add(2339523.615233598);
        expected.add(1465164.8959404975);
        expected.add(1880155.7568462044);
        expected.add(3663813.116663118);
        expected.add(2535399.5646110806);
        expected.add(1042653.1170816979);
        expected.add(730193.861870057);
        Set<Double> actual = Main.cityNetwork.edges();
        assertEquals(expected, actual);
    }

    @Test
    void getEdge() {
        double actual = Main.cityNetwork.getEdge(CityNetwork.getCity("buenosaires"), CityNetwork.getCity("lapaz"));
        double expected = 2236.8887384291743;
        assertEquals(expected, actual);
    }

    @Test
    void opposite() {
        City actual = Main.cityNetwork.opposite(CityNetwork.getCity("buenosaires"), 2236.8887384291743);
        City expected = CityNetwork.getCity("lapaz");
        assertEquals(expected, actual);
    }

    @Test
    void outDegree() {
        int actual = Main.cityNetwork.outDegree(CityNetwork.getCity("buenosaires"));
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void outgoingEdges() {
        List<Double> actual = (List) Main.cityNetwork.outgoingEdges(CityNetwork.getCity("buenosaires"));
        List<Double> expected = new ArrayList<>();
        expected.add(2236.8887384291743);
        expected.add(2339.523615233598);
        expected.add(1137.3015364568012);
        expected.add(1037.95102601786);
        expected.add(202.56119155364988);
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void getAdjacentVertices() {
        List<City> actual = Main.cityNetwork.getAdjacentVertices(CityNetwork.getCity("buenosaires"));
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("lapaz"));
        expected.add(CityNetwork.getCity("brasilia"));
        expected.add(CityNetwork.getCity("montevideu"));
        expected.add(CityNetwork.getCity("assuncao"));
        expected.add(CityNetwork.getCity("santiago"));
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void validVertex() {
        City nonExistentCity = new City("portugal", "europa", "10", "lisboa", "0", "0");
        City existentCity = CityNetwork.getCity("buenosaires");
        assertTrue(Main.cityNetwork.validVertex(existentCity));
        assertFalse(Main.cityNetwork.validVertex(nonExistentCity));
        assertFalse(Main.cityNetwork.validVertex(null));
    }

    @Test
    void isConnected() {
        assertTrue(Main.cityNetwork.isConnected());

        Main.cityNetwork.insertVertex(new City("portugal", "europa", "10", "lisboa", "0", "0"));
        assertFalse(Main.cityNetwork.isConnected());
    }

    @Test
    void longestPath() {
        int actual = Main.cityNetwork.longestPath();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void searchByLayers() {
        List<City> actual = Main.cityNetwork.searchByLayers(CityNetwork.getCity("buenosaires"), 2);
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("buenosaires"));
        expected.add(CityNetwork.getCity("lapaz"));
        expected.add(CityNetwork.getCity("brasilia"));
        expected.add(CityNetwork.getCity("montevideu"));
        expected.add(CityNetwork.getCity("assuncao"));
        expected.add(CityNetwork.getCity("santiago"));
        expected.add(CityNetwork.getCity("lima"));
        expected.add(CityNetwork.getCity("georgetwon"));
        expected.add(CityNetwork.getCity("bogota"));
        expected.add(CityNetwork.getCity("caiena"));
        expected.add(CityNetwork.getCity("caracas"));
        expected.add(CityNetwork.getCity("paramaribo"));
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void shortestPath() {
        List<City> actual = Main.cityNetwork.shortestPath(0, 0, true);
        assertTrue(actual.isEmpty());

        actual = Main.cityNetwork.shortestPath(0, 1, true);
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("buenosaires"));
        expected.add(CityNetwork.getCity("lapaz"));
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void shortestDistance() {
        double actual = Main.cityNetwork.shortestDistance(0, 0, true);
        double expected = 0;
        assertEquals(expected, actual);

        actual = Main.cityNetwork.shortestDistance(0, 1, true);
        expected = 2236.8887384291743;
        assertEquals(expected, actual);
    }

    @Test
    void getPathAcrossAllVertices() {
        List<City> vertexList = new ArrayList<>();
        vertexList.add(CityNetwork.getCity("quito"));
        vertexList.add(CityNetwork.getCity("assuncao"));
        vertexList.add(CityNetwork.getCity("paramaribo"));
        vertexList.add(CityNetwork.getCity("bogota"));
        vertexList.add(CityNetwork.getCity("lapaz"));
        vertexList.add(CityNetwork.getCity("brasilia"));

        Pair<List<City>, Double> actual = Main.cityNetwork.getPathAcrossAllVertices();
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("quito"));
        expected.add(CityNetwork.getCity("bogota"));
        expected.add(CityNetwork.getCity("caracas"));
        expected.add(CityNetwork.getCity("georgetwon"));
        expected.add(CityNetwork.getCity("paramaribo"));
        expected.add(CityNetwork.getCity("brasilia"));
        expected.add(CityNetwork.getCity("assuncao"));
        expected.add(CityNetwork.getCity("lapaz"));
        expected.add(CityNetwork.getCity("brasilia"));

        assertArrayEquals(expected.toArray(), actual.getKey().toArray());
        assertEquals(10770.62, actual.getValue());
    }

    @Test
    void getCitiesByBorders() {
    }

    @Test
    void getCity() {
    }

    @Test
    void getCountry() {
    }

    @Test
    void getCentralities() {
    }

    @Test
    void getUserPercentage() {
    }

    @Test
    void insertVertex() {
    }

    @Test
    void removeVertex() {
    }

    @Test
    void insertEdge() {
    }

    @Test
    void convert() {
    }
}