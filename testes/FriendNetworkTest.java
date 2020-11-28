import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
    void usersByPopularity()
    {
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
    void friendsInCommon()
    {
        Set<User> expected = Main.friendNetwork.friendsInCommon(Main.friendNetwork.usersByPopularity(),3);
        Set<User> actual = new HashSet<>();
        actual.add(Main.friendNetwork.getUser("u9"));

        assertEquals(actual,expected);
    }
}