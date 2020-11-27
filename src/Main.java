import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
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

        System.out.println("Bem-vindo a Rede Social");
        System.out.println("Escolha a opção que deseja: ");
        System.out.println("1-Construir os grafos a partir da informação fornecida nos ficheiros de texto.");
        System.out.println("2-Devolver os amigos comuns entre os n utilizadores mais populares da rede.");
        System.out.println("3-Verificar se a rede de amizades é conectada e em caso positivo devolver o número mínimo de ligações necessário para nesta rede qualquer utilizador conseguir contactar um qualquer outro utilizador");
        System.out.println("4-Devolver para um utilizador os amigos que se encontrem nas proximidades, isto é, amigos que habitem em cidades que distam um dado número de fronteiras da cidade desse utilizador.");
        System.out.println("5-Devolver as n cidades com maior centralidade ou seja, as cidades que em média estão mais próximas de todas as outras cidades e onde habitem pelo menos p% dos utilizadores da rede de amizades, onde p% é a percentagem relativa de utilizadores em cada cidade.");
        System.out.println("6-Devolver o caminho terrestre mais curto entre dois utilizadores, passando obrigatoriamente pelas n cidade(s) intermédias onde cada utilizador tenha o maior número de amigos.");
        System.out.println("0-Sair");

        Scanner sc = new Scanner(System.in);
        int op = 99;

        while (op != 0)
        {
            op = sc.nextInt();

            switch (op)
            {
                case 1:
                {
                    readFile(COUNTRY_FILE);
                    readFile(BORDERS_FILE);
                    readFile(USER_FILE);
                    readFile(RELATIONSHIP_FILE);

                    System.out.println("Ficheiro lido com sucesso");
                    sc.nextLine();
                    break;
                }

                case 2:
                {
                    System.out.println("Insira o numero de utilizadores populares que pretende saber os amigos em comum: ");
                    int numero = sc.nextInt();

                    printMostPopularCommonFriends(numero);
                    break;
                }

                case 3:
                {
                    printMinimumNumberOfConnections();

                    break;
                }

                case 4:
                {
                    sc.nextLine();

                    System.out.println("Insira o utilizador que pretende analisar: ");
                    String utilizador = sc.nextLine();

                    System.out.println("Insira a quantas fronteiras pretende aceder: ");
                    int fronteiras = sc.nextInt();

                    printNearestFriends(fronteiras, utilizador);
                    break;
                }

                case 5:
                {
                    sc.nextLine();

                    System.out.println("Insira o numero de cidades que pretende conhecer a maior centralidade: ");
                    int cidades = sc.nextInt();

                    System.out.println("Insira a percentagem de utilizadores: ");
                    float percentagem = sc.nextFloat();

                    printCitiesGreaterCentrality(cidades,percentagem);
                    break;
                }

                case 0:
                {
                    System.out.println("Espero que tenha gostado. Volte sempre! :D");
                    break;
                }

                default:
                {
                    System.out.println("Opção Inválida");
                }
            }

        }
    }

        public static void readFile (String fileName) throws IOException {
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

        private static void processUsers (Scanner reader){
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

        private static void processRelationships (Scanner reader){
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

        private static void processCountry (Scanner reader){
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

        private static void processBorders (Scanner reader){
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

        public static void printMostPopularCommonFriends ( int n){
            List<User> usersByPopularity = friendNetwork.usersByPopularity();
            Set<User> commonFriends = friendNetwork.friendsInCommon(usersByPopularity, n);

            System.out.println("==========================================");
            System.out.println("Amigos em comum dos users mais populares:");
            for (User user : commonFriends) {
                System.out.println(user.getName());
            }
            System.out.println("==========================================");
        }

        public static void printMinimumNumberOfConnections () {
            if (friendNetwork.isConnected()) {
                System.out.println("=================================================================================================================================");
                System.out.println("A rede de amizades é conectada.");
                System.out.printf("O número mínimo de ligações necessário para nesta rede qualquer utilizador conseguir contactar um qualquer outro utilizador é: %d \n", friendNetwork.longestPath());
                System.out.println("=================================================================================================================================");
            }
        }

        public static void printNearestFriends ( int n, String userName){
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

        public static void printCitiesGreaterCentrality (int n, float p)
        {
            List<City> centralityCity = cityNetwork.getCentralities(n);

            List<City> percentageCity = cityNetwork.getUserPercentage(p, centralityCity);

            System.out.printf("Das %d cidades com maior centralidade, com uma percentagem de utilizadores acima de %.2f%%: \n", n, p);

            for(City city : percentageCity)
            {
                System.out.printf("%s \n",city.getCity());
            }

        }

    }