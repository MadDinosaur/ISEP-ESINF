import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    //Definição dos nomes de ficheiros
    static final String USER_FILE = "small-network/susers.txt";
    static final String RELATIONSHIP_FILE = "small-network/srelationships.txt";
    static final String COUNTRY_FILE = "small-network/scountries.txt";
    static final String BORDERS_FILE = "small-network/sborders.txt";

    //Inicialização dos grafos
    static final FriendNetwork friendNetwork = new FriendNetwork();
    static final CityNetwork cityNetwork = new CityNetwork();

    public static void main(String[] args) throws IOException {
        //1
        readFile(USER_FILE);
        readFile(RELATIONSHIP_FILE);
        readFile(COUNTRY_FILE);
        readFile(BORDERS_FILE);

        //2
        printMostPopularCommonFriends(3);

        //3
        printMinimumNumberOfConnections();

        //4
        printNearestFriends(2, "u1");
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
            City c = new City(line[country], line[continent], line[population], line[capital], line[latitude], line[longitude]);
            cityNetwork.insertVertex(c);
        }
    }

    private static void processBorders(Scanner reader) {
        //Índices do ficheiro de texto
        int country1 = 0;
        int country2 = 1;
        //Declaração dos objetos
        City city;
        City otherCity;
        //Leitura do ficheiro linha a linha e atribuição dos valores das colunas aos respetivos objetos
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");

            city = new City(line[country1]);
            otherCity = new City(line[country2]);

            cityNetwork.insertEdge(city, otherCity, city.distanceFrom(otherCity));
        }
    }

    // método para responder ao exercício 2

    public static void printMostPopularCommonFriends(int n) {
        List<User> usersByPopularity = friendNetwork.usersByPopularity();
        Set<User> commonFriends = friendNetwork.friendsInCommon(usersByPopularity, n);

        System.out.println("Amigos em comum dos users mais populares:");
        for (User user : commonFriends) {
            System.out.println(user.getName());
        }
    }

    public static void printMinimumNumberOfConnections() {
        if (friendNetwork.isConnected()) {
            System.out.println("A rede de amizades é conectada.");
            System.out.println("O número mínimo de ligações necessário para nesta rede qualquer utilizador conseguir contactar um qualquer outro utilizador é:");
            System.out.println(friendNetwork.longestPath());
        }
    }

    public static void printNearestFriends(int n, String userName) {
        User userAux = friendNetwork.getUser(userName);

        City cityAux = cityNetwork.getCity(userAux.getCity());

        List<City> cityList = cityNetwork.getCitiesByBorders(cityAux, n);

        List<User> userFriends = friendNetwork.getNearestFriends(userAux, cityList);

        Comparator<User> byCity = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getCity().compareTo(u2.getCity());
            }
        };

        Collections.sort(userFriends, byCity);
        for (User friend : userFriends) {
            System.out.printf("%s, %s\n", friend.getCity(), friend.getName());
        }
    }

}