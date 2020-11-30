import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
//TODO reforçar não repetição das cidades
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

    @Test
    void getEdge() {
        Double actual = Main.cityNetwork.getEdge(CityNetwork.getCity("buenosaires"), CityNetwork.getCity("lapaz"));
        Double expected = 2236.8887384291743;
        assertEquals(expected, actual);

        //Null check
        actual = Main.cityNetwork.getEdge(CityNetwork.getCity("barcelona"), CityNetwork.getCity("dublin"));
        expected = null;
        assertEquals(expected, actual);
    }

    @Test
    void opposite() {
        City actual = Main.cityNetwork.opposite(CityNetwork.getCity("buenosaires"), 2236.8887384291743);
        City expected = CityNetwork.getCity("lapaz");
        assertEquals(expected, actual);

        //Null check
        actual = Main.cityNetwork.opposite(CityNetwork.getCity("lisboa"), 0.0);
        expected = null;
        assertEquals(expected, actual);
    }

    @Test
    void outDegree() {
        int actual = Main.cityNetwork.outDegree(CityNetwork.getCity("buenosaires"));
        int expected = 5;
        assertEquals(expected, actual);

        //Null check
        actual = Main.cityNetwork.outDegree(CityNetwork.getCity("lisboa"));
        expected = 0;
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
        Main.cityNetwork.removeVertex(new City("portugal", "europa", "10", "lisboa", "0", "0"));
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
        vertexList.add(CityNetwork.getCity("brasilia"));
        vertexList.add(CityNetwork.getCity("paramaribo"));
        vertexList.add(CityNetwork.getCity("lapaz"));

        Pair<List<City>, Double> actual = Main.cityNetwork.getPathAcrossAllVertices(vertexList);
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("brasilia"));
        expected.add(CityNetwork.getCity("paramaribo"));
        expected.add(CityNetwork.getCity("georgetwon"));
        expected.add(CityNetwork.getCity("caracas"));
        expected.add(CityNetwork.getCity("bogota"));
        expected.add(CityNetwork.getCity("lima"));
        expected.add(CityNetwork.getCity("lapaz"));

        assertArrayEquals(expected.toArray(), actual.getKey().toArray());
        assertEquals(7909.831272333104, actual.getValue());
    }

    @Test
    void getCity() {
        City actual = CityNetwork.getCity("buenosaires");
        City expected = new City("argentina", "americasul", "41.67", "buenosaires", "-34.6131500", "-58.3772300");
        assertEquals(expected, actual);
    }

    @Test
    void getCountry() {
        City actual = CityNetwork.getCountry("argentina");
        City expected = new City("argentina", "americasul", "41.67", "buenosaires", "-34.6131500", "-58.3772300");
        assertEquals(expected, actual);
    }

    @Test
    void getCentralities() {
        List<City> actual = Main.cityNetwork.getCentralities();
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("lapaz"));
        expected.add(CityNetwork.getCity("assuncao"));
        expected.add(CityNetwork.getCity("lima"));
        expected.add(CityNetwork.getCity("georgetwon"));
        expected.add(CityNetwork.getCity("paramaribo"));
        expected.add(CityNetwork.getCity("brasilia"));
        expected.add(CityNetwork.getCity("bogota"));
        expected.add(CityNetwork.getCity("caiena"));
        expected.add(CityNetwork.getCity("quito"));
        expected.add(CityNetwork.getCity("caracas"));
        expected.add(CityNetwork.getCity("buenosaires"));
        expected.add(CityNetwork.getCity("santiago"));
        expected.add(CityNetwork.getCity("montevideu"));

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void getUserPercentage() {
        List<City> actual = Main.cityNetwork.getUserPercentage(100, Main.cityNetwork.getCentralities(), 2);
        assertTrue(actual.isEmpty());

        actual = Main.cityNetwork.getUserPercentage(5, Main.cityNetwork.getCentralities(), 0);
        assertTrue(actual.isEmpty());

        actual = Main.cityNetwork.getUserPercentage(1.3f, Main.cityNetwork.getCentralities(), 3);
        List<City> expected = new ArrayList<>();
        expected.add(CityNetwork.getCity("lapaz"));
        expected.add(CityNetwork.getCity("assuncao"));
        expected.add(CityNetwork.getCity("georgetwon"));
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void insertEdge() {
        assertFalse(Main.cityNetwork.insertEdge(null, null));
        assertFalse(Main.cityNetwork.insertEdge(new City("portugal"), new City("brasilia")));
        assertTrue(Main.cityNetwork.insertEdge(new City("paraguai"), new City("venezuela")));
    }
}