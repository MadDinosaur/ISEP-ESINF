import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Main {
    //Definição dos nomes de ficheiros
    static final String USER_FILE = "small-network/susers.txt";
    static final String RELATIONSHIP_FILE = "small-network/srelationships.txt";
    static final String COUNTRY_FILE = "small-network/scountries.txt";
    static final String BORDERS_FILE = "small-network/sborders.txt";
    //Inicialização dos grafos
    static FriendNetwork friendNetwork = new FriendNetwork();
    static CityNetwork cityNetwork = new CityNetwork();

    public static void main(String[] args) throws IOException {
        //1
        readFile(USER_FILE);
        readFile(RELATIONSHIP_FILE);
        readFile(COUNTRY_FILE);
        readFile(BORDERS_FILE);

        //2
        printMostPopularCommonFriends(3);
    }


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
        //Índices do ficheiro de texto
        int name = 0;
        int age = 1;
        int city = 2;
        //Leitura do ficheiro linha a linha e atribuição dos valores das colunas aos respetivos objetos
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            User u = new User(line[name], line[age], line[city]);
            friendNetwork.insertVertex(u);
        }
    }

    private static void processRelationships(Scanner reader) {
        //Índices do ficheiro de texto
        int user1 = 0;
        int user2 = 1;
        //Declaração dos objetos
        User user;
        User otherUser;
        //Leitura do ficheiro linha a linha e atribuição dos valores das colunas aos respetivos objetos
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");

            user = new User(line[user1]);
            otherUser = new User(line[user2]);

            friendNetwork.insertEdge(user, otherUser, user.getName() + "-" + otherUser.getName());
        }
    }

    private static void processCountry(Scanner reader) {
        //Índices do ficheiro de texto
        int country = 0;
        int continent = 1;
        int population = 2;
        int capital = 3;
        int latitude = 4;
        int longitude = 5;
        //Leitura do ficheiro linha a linha e atribuição dos valores das colunas aos respetivos objetos
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            Country c = new Country(line[country], line[continent], line[population], line[capital], line[latitude], line[longitude]);
            cityNetwork.insertVertex(c);
        }
    }

    private static void processBorders(Scanner reader) {
        //Índices do ficheiro de texto
        int country1 = 0;
        int country2 = 1;
        //Declaração dos objetos
        Country country;
        Country otherCountry;
        //Leitura do ficheiro linha a linha e atribuição dos valores das colunas aos respetivos objetos
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");

            country = new Country(line[country1]);
            otherCountry = new Country(line[country2]);

            cityNetwork.insertEdge(country, otherCountry, country.distanceFrom(otherCountry));
        }
    }

    // método para responder ao exercício 2

    public static void printMostPopularCommonFriends(int n) {
        LinkedList<User> usersByPopularity = friendNetwork.usersByPopularity();
        Set<User> commonFriends = friendNetwork.friendsInCommon(usersByPopularity, n);

        System.out.println("Amigos em comum dos users mais populares: \n");
        for (User user : commonFriends) {
            System.out.println(user.getName());
        }
    }
}