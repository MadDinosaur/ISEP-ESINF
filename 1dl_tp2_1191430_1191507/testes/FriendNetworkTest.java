import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testes unitários à classe FriendNetwork, assim como aos métodos utilizados da classe-mãe Graph.
 */
class FriendNetworkTest {
    String USER_FILE = "small-network/susers.txt";
    String RELATIONSHIP_FILE = "small-network/srelationships.txt";
    String COUNTRY_FILE = "small-network/scountries.txt";
    String BORDERS_FILE = "small-network/sborders.txt";

    public FriendNetworkTest() throws IOException {
        Main.readFile(COUNTRY_FILE,3);
        Main.readFile(BORDERS_FILE,4);
        Main.readFile(USER_FILE,1);
        Main.readFile(RELATIONSHIP_FILE,2);
    }


    @Test
    void usersByPopularity() {
        List<User> expected = Main.friendNetwork.usersByPopularity();
        List<User> actual = new ArrayList<User>();

        actual.add(Main.friendNetwork.getUser("u33"));
        actual.add(Main.friendNetwork.getUser("u1"));
        actual.add(Main.friendNetwork.getUser("u3"));
        actual.add(Main.friendNetwork.getUser("u2"));
        actual.add(Main.friendNetwork.getUser("u32"));
        actual.add(Main.friendNetwork.getUser("u24"));
        actual.add(Main.friendNetwork.getUser("u9"));
        actual.add(Main.friendNetwork.getUser("u4"));
        actual.add(Main.friendNetwork.getUser("u14"));
        actual.add(Main.friendNetwork.getUser("u28"));
        actual.add(Main.friendNetwork.getUser("u30"));
        actual.add(Main.friendNetwork.getUser("u31"));
        actual.add(Main.friendNetwork.getUser("u26"));
        actual.add(Main.friendNetwork.getUser("u25"));
        actual.add(Main.friendNetwork.getUser("u7"));
        actual.add(Main.friendNetwork.getUser("u5"));
        actual.add(Main.friendNetwork.getUser("u6"));
        actual.add(Main.friendNetwork.getUser("u20"));
        actual.add(Main.friendNetwork.getUser("u29"));
        actual.add(Main.friendNetwork.getUser("u23"));
        actual.add(Main.friendNetwork.getUser("u19"));
        actual.add(Main.friendNetwork.getUser("u15"));
        actual.add(Main.friendNetwork.getUser("u21"));
        actual.add(Main.friendNetwork.getUser("u27"));
        actual.add(Main.friendNetwork.getUser("u10"));
        actual.add(Main.friendNetwork.getUser("u16"));

        assertEquals(actual,expected);
    }

    @Test
    void friendsInCommonSize1() {
        Set<User> expected = Main.friendNetwork.friendsInCommon(Main.friendNetwork.usersByPopularity(),1);
        Set<User> actual = new HashSet<>();
        actual.add(Main.friendNetwork.getUser("u19"));
        actual.add(Main.friendNetwork.getUser("u24"));
        actual.add(Main.friendNetwork.getUser("u15"));
        actual.add(Main.friendNetwork.getUser("u16"));
        actual.add(Main.friendNetwork.getUser("u30"));
        actual.add(Main.friendNetwork.getUser("u31"));
        actual.add(Main.friendNetwork.getUser("u32"));
        actual.add(Main.friendNetwork.getUser("u23"));
        actual.add(Main.friendNetwork.getUser("u3"));
        actual.add(Main.friendNetwork.getUser("u21"));
        actual.add(Main.friendNetwork.getUser("u9"));

        assertEquals(actual,expected);
    }

    @Test
    void friendsInCommonSize2() {
        Set<User> expected = Main.friendNetwork.friendsInCommon(Main.friendNetwork.usersByPopularity(),2);
        Set<User> actual = new HashSet<>();
        actual.add(Main.friendNetwork.getUser("u32"));
        actual.add(Main.friendNetwork.getUser("u3"));
        actual.add(Main.friendNetwork.getUser("u9"));

        assertEquals(actual,expected);
    }

    @Test
    void friendsInCommonSize3() {
        Set<User> expected = Main.friendNetwork.friendsInCommon(Main.friendNetwork.usersByPopularity(),3);
        Set<User> actual = new HashSet<>();
        actual.add(Main.friendNetwork.getUser("u9"));

        assertEquals(actual,expected);
    }

    //Este teste assemelha-se a um teste para testar o valor null, isto porque não faz sentido inserir o size 0, então decidimos inserir um size tão grande de maneira a receber uma lista vazia, para testar a possibilidade de ela dar vazia.
    @Test
    void friendsInCommonSizeNull() {
        Set<User> expected = Main.friendNetwork.friendsInCommon(Main.friendNetwork.usersByPopularity(), 5);
        Set<User> actual = new HashSet<>();

        assertEquals(actual, expected);
    }

    @Test
    void numVertices() {
        int expected = 26;
        int actual = Main.friendNetwork.numVertices();
        assertEquals(expected, actual);
    }

    @Test
    void vertices() {
        Set<User> expected = new HashSet<>();
        expected.add(FriendNetwork.getUser("u1"));
        expected.add(FriendNetwork.getUser("u2"));
        expected.add(FriendNetwork.getUser("u3"));
        expected.add(FriendNetwork.getUser("u4"));
        expected.add(FriendNetwork.getUser("u5"));
        expected.add(FriendNetwork.getUser("u6"));
        expected.add(FriendNetwork.getUser("u7"));
        expected.add(FriendNetwork.getUser("u9"));
        expected.add(FriendNetwork.getUser("u10"));
        expected.add(FriendNetwork.getUser("u14"));
        expected.add(FriendNetwork.getUser("u15"));
        expected.add(FriendNetwork.getUser("u16"));
        expected.add(FriendNetwork.getUser("u19"));
        expected.add(FriendNetwork.getUser("u20"));
        expected.add(FriendNetwork.getUser("u21"));
        expected.add(FriendNetwork.getUser("u23"));
        expected.add(FriendNetwork.getUser("u24"));
        expected.add(FriendNetwork.getUser("u25"));
        expected.add(FriendNetwork.getUser("u26"));
        expected.add(FriendNetwork.getUser("u27"));
        expected.add(FriendNetwork.getUser("u28"));
        expected.add(FriendNetwork.getUser("u29"));
        expected.add(FriendNetwork.getUser("u30"));
        expected.add(FriendNetwork.getUser("u31"));
        expected.add(FriendNetwork.getUser("u32"));
        expected.add(FriendNetwork.getUser("u33"));

        Set<User> actual = Main.friendNetwork.vertices();
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void numEdges() {
        int expected = 45;
        int actual = Main.friendNetwork.numEdges();
        assertEquals(expected, actual);
    }

    @Test
    void opposite() {
        User actual = Main.friendNetwork.opposite(Main.friendNetwork.getUser("u1"), "u2-u1");
        User expected = Main.friendNetwork.getUser("u2");
        assertEquals(expected, actual);
    }

    @Test
    void outDegree() {
        int actual = Main.friendNetwork.outDegree(Main.friendNetwork.getUser("u1"));
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    void outgoingEdges() {
        List<String> actual = (List) Main.friendNetwork.outgoingEdges(Main.friendNetwork.getUser("u1"));
        List<String> expected = new ArrayList<>();
        expected.add("u2-u1");
        expected.add("u4-u1");
        expected.add("u3-u1");
        expected.add("u9-u1");
        expected.add("u7-u1");
        expected.add("u6-u1");
        expected.add("u14-u1");
        expected.add("u20-u1");
        expected.add("u32-u1");
        expected.add("u5-u1");
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void getAdjacentVertices() {
        List<User> actual = Main.friendNetwork.getAdjacentVertices(FriendNetwork.getUser("u1"));
        List<User> expected = new ArrayList<>();
        expected.add(FriendNetwork.getUser("u2"));
        expected.add(FriendNetwork.getUser("u3"));
        expected.add(FriendNetwork.getUser("u4"));
        expected.add(FriendNetwork.getUser("u5"));
        expected.add(FriendNetwork.getUser("u6"));
        expected.add(FriendNetwork.getUser("u7"));
        expected.add(FriendNetwork.getUser("u9"));
        expected.add(FriendNetwork.getUser("u14"));
        expected.add(FriendNetwork.getUser("u20"));
        expected.add(FriendNetwork.getUser("u32"));
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    void validVertex() {
        User nonExistentUser = new User("u34");
        User existentUser = FriendNetwork.getUser("u1");
        assertTrue(Main.friendNetwork.validVertex(existentUser));
        assertFalse(Main.friendNetwork.validVertex(nonExistentUser));
        assertFalse(Main.friendNetwork.validVertex(null));
    }

    @Test
    void isConnected() {
        assertTrue(Main.friendNetwork.isConnected());

        Main.friendNetwork.insertVertex(new User("u34", "34", "buenosaires"));
        assertFalse(Main.friendNetwork.isConnected());
        Main.friendNetwork.removeVertex(new User("u34", "34", "buenosaires"));
    }

    @Test
    void longestPath() {
        int actual = Main.friendNetwork.longestPath();
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void shortestDistance() {
        double actual = Main.friendNetwork.shortestDistance(0, 0, false);
        double expected = 0;
        assertEquals(expected, actual);

        actual = Main.friendNetwork.shortestDistance(0, 1, false);
        expected = 1;
        assertEquals(expected, actual);
    }
}