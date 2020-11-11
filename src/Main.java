import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static final String USER_FILE = "/small-network/susers.txt";
    static final String RELATIONSHIP_FILE = "/small-network/srelationships.txt";
    static final String COUNTRY_FILE = "/small-network/scountries.txt";
    static final String BORDERS_FILE = "/small-network/sborders.txt";

    static final Graph<User, String> friendNetwork = new Graph<User, String>();
    static final Graph<Country, Float> cityNetwork = new Graph<Country, Float>();

    public static void readFile(String fileName) throws IOException {
        Scanner reader = new Scanner(new File(fileName));
        switch (fileName) {
            case USER_FILE:
                processUsers(reader);
                break;
            case RELATIONSHIP_FILE:
                processRelationships(reader);
                break;
            case COUNTRY_FILE:
                processCountry(reader);
                break;
            case BORDERS_FILE:
                processBorders(reader);
                break;
        }
        reader.close();
    }

    private static void processUsers(Scanner reader) {
        int name = 0;
        int age = 1;
        int city = 2;
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            User u = new User(line[name], line[age], line[city]);
            friendNetwork.insertVertex(u);
        }
    }

    private static void processRelationships(Scanner reader) {
        int user1 = 0;
        int user2 = 1;
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            friendNetwork.insertEdge(new User(line[user1]), new User(line[user2]), null, 0);
        }
    }

    private static void processCountry(Scanner reader) {
        int country = 0;
        int continent = 1;
        int population = 2;
        int capital = 3;
        int latitude = 4;
        int longitude = 5;
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            Country c = new Country(line[country], line[continent], line[population], line[capital], line[latitude], line[longitude]);
            cityNetwork.insertVertex(c);
        }
    }

    private static void processBorders(Scanner reader) {
        int country1 = 0;
        int country2 = 1;
        //Lê o ficheiro linha a linha e atribuiu os valores das colunas às respetivas classes
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            cityNetwork.insertEdge(new Country(line[country1]), new Country(line[country2]), null, 0);
        }
    }
}