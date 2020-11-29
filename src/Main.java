import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    //Definição dos nomes de ficheiros
    static String USER_FILE;
    static String RELATIONSHIP_FILE;
    static String COUNTRY_FILE;
    static String BORDERS_FILE;

    //Inicialização dos grafos
    static final FriendNetwork friendNetwork = new FriendNetwork();
    static final CityNetwork cityNetwork = new CityNetwork();

    static Scanner sc = new Scanner(System.in);

    public Main() {
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Bem-vindo a Rede Social");
        menu();

        readFile(COUNTRY_FILE, 3);
        readFile(BORDERS_FILE, 4);
        readFile(USER_FILE, 1);
        readFile(RELATIONSHIP_FILE, 2);

        System.out.println("1-Os amigos comuns entre os n utilizadores mais populares da rede.");
        System.out.println("2-Grafo é conectado? Número mínimo de ligações dos users mais distantes caso se confirme!");
        System.out.println("3-Amigos entre fronteiras.");
        System.out.println("4-Centralidade relativa das cidades onde habitam %p de users.");
        System.out.println("5-Caminho terrestre mais curto entre utilizadores que decidem viajar pelas cidades onde possuem mais amigos.");
        System.out.println("0-Sair");
        int op = 99;
        while (op != 0) {
            try {
                op = sc.nextInt();
                switch (op) {
                    case 1: {
                        System.out.println("Insira o numero de utilizadores populares que pretende saber os amigos em comum: ");
                        int numero = sc.nextInt();
                        printMostPopularCommonFriends(numero);
                        System.out.println("Printe o comando que necessita realizar a seguir: ");
                        break;
                    }
                    case 2: {
                        printMinimumNumberOfConnections();
                        System.out.println("Printe o comando que necessita realizar a seguir: ");
                        break;
                    }
                    case 3: {
                        sc.nextLine();
                        System.out.println("Insira o utilizador que pretende analisar: ");
                        String utilizador = sc.nextLine();
                        System.out.println("Insira a quantas fronteiras pretende aceder: ");
                        int fronteiras = sc.nextInt();
                        printNearestFriends(fronteiras, utilizador);
                        System.out.println("Printe o comando que necessita realizar a seguir: ");
                        break;
                    }
                    case 4: {
                        sc.nextLine();
                        System.out.println("Insira o numero de cidades que pretende conhecer a maior centralidade: ");
                        int cidades = sc.nextInt();
                        System.out.println("Insira a percentagem de utilizadores: ");
                        float percentagem = sc.nextFloat();
                        printCitiesGreaterCentrality(cidades, percentagem);
                        System.out.println("Printe o comando que necessita realizar a seguir: ");
                        break;
                    }
                    case 5:
                        sc.nextLine();
                        System.out.println("Insira o nome do primeiro utilizador:");
                        String user1 = sc.nextLine();
                        System.out.println("Insira o nome do segundo utilizador:");
                        String user2 = sc.nextLine();
                        System.out.println("Insira o número de cidades intermédias:");
                        int n = sc.nextInt();
                        printShortestPathAcrossCitiesWithMostFriends(user1, user2, n);
                        System.out.println("Printe o comando que necessita realizar a seguir: ");
                        break;
                    case 0: {
                        System.out.println("Espero que tenha gostado. Volte sempre! :D");
                        break;
                    }
                    default: {
                        System.out.println("Opção Inválida");
                        System.out.println("1-Os amigos comuns entre os n utilizadores mais populares da rede.");
                        System.out.println("2-Grafo é conectado? Número mínimo de ligações dos users mais distantes caso se confirme!");
                        System.out.println("3-Amigos entre fronteiras.");
                        System.out.println("4-Centralidade relativa das cidades onde habitam %p de users.");
                        System.out.println("5-Caminho terrestre mais curto entre utilizadores que decidem viajar pelas cidades onde possuem mais amigos.");
                        System.out.println("0-Sair");
                    }
                }

            }catch (NullPointerException | InputMismatchException error){
                System.out.println("Erro, inseriu valores inválidos!");
                System.out.println("Printe o comando que necessita realizar a seguir: ");
            }
        }
    }

    public static void readFile(String fileName, int identifier) throws IOException {
        Scanner reader = new Scanner(new File(fileName));
        switch (identifier) {
            case 1:
                processUsers(reader);
                break;
            case 2:
                processRelationships(reader);
                break;
            case 3:
                processCountry(reader);
                break;
            case 4:
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

            cityNetwork.insertEdge(city, otherCity);
        }
    }

    // método para responder ao exercício 2

    public static void printMostPopularCommonFriends(int n) {
        List<User> usersByPopularity = friendNetwork.usersByPopularity();
        Set<User> commonFriends = friendNetwork.friendsInCommon(usersByPopularity, n);

        System.out.println("==========================================");
        System.out.println("Amigos em comum dos users mais populares:");
        for (User user : commonFriends) {
            System.out.println(user.getName());
        }
        System.out.println("==========================================");
    }

    public static void printMinimumNumberOfConnections() {
        if (friendNetwork.isConnected()) {
            System.out.println("=================================================================================================================================");
            System.out.println("A rede de amizades é conectada.");
            System.out.printf("O número mínimo de ligações necessário para nesta rede qualquer utilizador conseguir contactar um qualquer outro utilizador é: %d \n", friendNetwork.longestPath());
            System.out.println("=================================================================================================================================");
        }
    }

    public static void printNearestFriends(int n, String userName) {
        User userAux = FriendNetwork.getUser(userName);

        City cityAux = CityNetwork.getCity(userAux.getCity());

        List<City> cityList = cityNetwork.getCitiesByBorders(cityAux, n);

        List<User> userFriends = friendNetwork.getNearestFriends(userAux, cityList);

        Comparator<User> byCity = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getCity().compareTo(u2.getCity());
            }
        };

        userFriends.sort(byCity);

        String previousCity = "";

        System.out.println("==========================================");
        System.out.println("Lista de amigos nas proximidades: ");

        for (User friend : userFriends) {

            if (previousCity.equalsIgnoreCase(friend.getCity())) {
                System.out.printf(", %s ", friend.getName());
            } else {
                System.out.printf("\n%s: %s", friend.getCity(), friend.getName());
            }

            previousCity = friend.getCity();
        }
        System.out.println("\n");
        System.out.println("==========================================");
    }

    public static void printCitiesGreaterCentrality(int n, float p) {
        List<City> centralityCity = cityNetwork.getCentralities();

        float x = p * friendNetwork.numVertices() / 100;

        List<City> percentageCity = cityNetwork.getUserPercentage(x, centralityCity, n);

        System.out.printf("As %d cidades com maior centralidade, com uma percentagem de utilizadores acima de %.2f%%: \n", n, p);

        System.out.println("===================");

        for (City city : percentageCity) {
            System.out.printf("%s \n", city.getCity());
        }

        System.out.println("===================");
    }

    public static void printShortestPathAcrossCitiesWithMostFriends(String userName1, String userName2, int n) {
        User user1 = FriendNetwork.getUser(userName1);
        User user2 = FriendNetwork.getUser(userName2);
        List<City> user1Cities = cityNetwork.convert(friendNetwork.getTopCitiesByNumFriends(user1, n));
        List<City> user2Cities = cityNetwork.convert(friendNetwork.getTopCitiesByNumFriends(user2, n));

        List<City> usersCities = new ArrayList<>();
        usersCities.add(CityNetwork.getCity(user1.getCity()));
        usersCities.addAll(user1Cities);
        usersCities.addAll(user2Cities);
        usersCities.add(CityNetwork.getCity(user2.getCity()));

        Pair<List<City>, Double> path = cityNetwork.getPathAcrossAllVertices(usersCities);
        System.out.printf("O caminho terrestre mais curto é: ");
        for (City c : path.getKey()) {
            System.out.printf("%s, ", c.getCity());
        }
        System.out.printf("\nDistância total: %.2fkm\n", path.getValue());
    }

    public static void menu() {
        int ficheiro = 99;

        while (ficheiro == 99) {
            System.out.println("Qual ficheiro deseja escolher? 1.Big 2.Small");
            ficheiro = sc.nextInt();

            switch (ficheiro) {
                case 1: {
                    USER_FILE = "big-network/busers.txt";
                    RELATIONSHIP_FILE = "big-network/brelationships.txt";
                    COUNTRY_FILE = "big-network/bcountries.txt";
                    BORDERS_FILE = "big-network/bborders.txt";
                    return;
                }
                case 2: {
                    USER_FILE = "small-network/susers.txt";
                    RELATIONSHIP_FILE = "small-network/srelationships.txt";
                    COUNTRY_FILE = "small-network/scountries.txt";
                    BORDERS_FILE = "small-network/sborders.txt";
                    return;
                }
                default: {
                    System.out.println("Opção inválida");
                    ficheiro = 99;
                }
            }
        }
    }

}