import javafx.util.Pair;

import java.util.*;

public class FriendNetwork extends Graph<User, String> {
    private static Map<String, User> userList;

    public FriendNetwork() {
        super();
        userList = new HashMap<>();
    }

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

    public Set<User> friendsInCommon(User vOrig, User vDest) {
        Set<User> vOrigFriends = new HashSet<>(this.getAdjacentVertices(vOrig));

        Set<User> vDestFriends = new HashSet<>(this.getAdjacentVertices(vDest));

        vOrigFriends.retainAll(vDestFriends);

        return vOrigFriends;
    }

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

    public List<User> getNearestFriends(User u, List<City> cityList) {
        List<User> friendsList = new ArrayList<>();

        for (User friend : getAdjacentVertices(u)) {
            if (cityList.contains(CityNetwork.getCity(friend.getCity()))) {
                friendsList.add(friend);
            }
        }

        return friendsList;
    }

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

    public static User getUser(String userName) {
        return userList.get(userName);
    }

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
