import javafx.util.Pair;

import java.util.*;

/**
 * Classe que modela uma rede de utilizadores, ligadas por amizades.
 */
public class FriendNetwork extends Graph<User, String> {
    /**
     * Mapa dos objetos User que compõem o grafo, indexados pelo seu nome.
     * Permite a pesquisa de utilizadores pelo nome em O(1).
     */
    private static Map<String, User> userList;
    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor da classe FriendNetwork.
     */
    public FriendNetwork() {
        super();
        userList = new HashMap<>();
    }
    //------------------------------- Métodos estáticos ---------------------------------

    /**
     * Método para obter o objeto User correspondente a partir do nome do utilizador.
     *
     * @param userName o nome do utilizador.
     * @return o objeto User correspondente.
     */
    public static User getUser(String userName) {
        return userList.get(userName);
    }
    //------------------------------- Métodos de instância ---------------------------------

    /**
     * @return a lista de utilizadores da rede, ordenados de forma decrescente pelo número de amigos.
     */
    public List<User> usersByPopularity() {
        Comparator<User> byPopularity = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return -Integer.compare(u1.getNumFriends(), u2.getNumFriends());
            }
        };

        List<User> userList = new ArrayList<>();
        userList.addAll(this.vertices());

        Collections.sort(userList, byPopularity);

        return userList;
    }

    /**
     * Método para obter os amigos em comum entre dois utilizadores.
     *
     * @param vOrig o utilizador de origem.
     * @param vDest o utilizador de destino.
     * @return lista de amigos em comum (Users) dos utilizadores @vOrig e @vDest.
     */
    public Set<User> friendsInCommon(User vOrig, User vDest) {
        Set<User> vOrigFriends = new HashSet<>(this.getAdjacentVertices(vOrig));

        Set<User> vDestFriends = new HashSet<>(this.getAdjacentVertices(vDest));

        vOrigFriends.retainAll(vDestFriends);

        return vOrigFriends;
    }

    /**
     * Método para obter os amigos em comum de um dado conjunto de utilizadores.
     *
     * @param auxList: lista de utilizadores.
     * @param size:    número de utilizadores a considerar na lista.
     * @return lista de amigos em comum entre os @size primeiros utilizadores da @auxList.
     */
    public Set<User> friendsInCommon(List<User> auxList, int size) {
        Set<Set<User>> vFinalFriends = new HashSet<>();

        ListIterator<User> i = auxList.listIterator();

        while (i.hasNext() && i.nextIndex() < size - 1) {
            User vOrig = i.next();
            User vDest = i.next();

            vFinalFriends.add(friendsInCommon(vOrig, vDest));
        }
        //Se o número de utilizadores for ímpar, será adicionado no final
        if (size % 2 != 0) {
            User vFinal = i.next();
            Set<User> vFriends = new HashSet<>(this.getAdjacentVertices(vFinal));
            vFinalFriends.add(vFriends);
        }

        return intersections(vFinalFriends).iterator().next();
    }

    /**
     * Método para calcular interseções entre conjuntos de utilizadores.
     *
     * @param auxSet conjunto de conjuntos de utilizadores (p.ex. agregação do conjunto de amigos de vários utilizadores).
     * @return um conjunto de conjuntos com os amigos em comum a todos os utilizadores.
     */
    public Set<Set<User>> intersections(Set<Set<User>> auxSet) {
        Set<Set<User>> newAuxSet = new HashSet<>();
        Iterator<Set<User>> i = auxSet.iterator();

        if (auxSet.size() % 2 != 0) {
            newAuxSet.add(i.next());
        }

        while (i.hasNext()) {
            Set<User> firstCommonFriends = i.next();
            Set<User> secondCommonFriends = i.next();

            firstCommonFriends.retainAll(secondCommonFriends);

            newAuxSet.add(firstCommonFriends);
        }

        if (auxSet.size() <= 1) {
            return newAuxSet;
        }

        return intersections(newAuxSet);
    }

    /**
     * Método para obter os amigos de um dado user que habitam num dado conjunto de cidades.
     *
     * @param u:        utilizador de origem.
     * @param cityList: lista das cidades onde se pretendem localizar amigos.
     * @return Lista de amigos de @u que habitem numa das cidades em @cityList.
     */
    public List<User> getNearestFriends(User u, List<City> cityList) {
        List<User> friendsList = new ArrayList<>();

        for (User friend : getAdjacentVertices(u)) {
            if (cityList.contains(CityNetwork.getCity(friend.getCity()))) {
                friendsList.add(friend);
            }
        }

        return friendsList;
    }

    /**
     * Método para obter o lista de amigos de um utilizador, ordenados pela cidade em que habitam (por ordem alfabética).
     *
     * @param u: utilizador de origem.
     * @return lista de amigos ordenada por local de habitação.
     */
    public List<User> getFriendsByCity(User u) {
        Comparator<User> byCity = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getCity().compareTo(o2.getCity());
            }
        };
        List<User> friendList = getAdjacentVertices(u);
        Collections.sort(friendList, byCity);
        return friendList;
    }

    /**
     * Método para obter uma lista de @n cidades ordenadas pelo nº de amigos de um dado utilizador.
     *
     * @param u: utilizador de origem.
     * @param n: número de cidades a retornar.
     * @return lista de @n cidades ordenadas, de forma decrescente, pelo nº de amigos de @u.
     */
    public List<String> getTopCitiesByNumFriends(User u, int n) {
        List<Pair<String, Integer>> citiesByNumFriends = new LinkedList<>();
        String city = "";
        int citySum = 0;
        for (User friend : getFriendsByCity(u)) {
            if (friend.getCity().equals(city)) {
                citySum++;
            } else {
                if (!city.isEmpty()) citiesByNumFriends.add(new Pair<>(city, citySum));
                city = friend.getCity();
                citySum = 1;
            }
        }
        citiesByNumFriends.add(new Pair<>(city, citySum));
        Collections.sort(citiesByNumFriends, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return -Integer.compare(o1.getValue(), o2.getValue());
            }
        });
        List<String> topCities = new ArrayList<>();
        citiesByNumFriends.subList(0, n).forEach(p -> topCities.add(p.getKey()));
        return topCities;
    }

    //------------------------------- Métodos herdados ---------------------------------
    @Override
    public boolean insertEdge(User vOrig, User vDest, String edge) {
        vOrig = getUser(vOrig.getName());
        vDest = getUser(vDest.getName());
        boolean inserted = super.insertEdge(vOrig, vDest, edge);
        if (inserted) {
            vOrig.addFriend();
            vDest.addFriend();
        }
        return inserted;
    }

    @Override
    public boolean removeEdge(User vOrig, User vDest) {
        boolean removed = super.removeEdge(vOrig, vDest);
        if (removed) {
            vOrig.removeFriend();
            vDest.removeFriend();
        }
        return removed;
    }

    @Override
    public boolean insertVertex(User newVert) {
        boolean inserted = super.insertVertex(newVert);
        if (inserted) {
            userList.put(newVert.getName(), newVert);
            City newCity = CityNetwork.getCity(newVert.getCity());
            newCity.addUser();
        }
        return inserted;
    }

    @Override
    public boolean removeVertex(User vert) {
        boolean removed = super.removeVertex(vert);
        if (removed) userList.remove(vert.getName());
        return removed;
    }
}
