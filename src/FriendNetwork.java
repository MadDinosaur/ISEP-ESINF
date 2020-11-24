import java.util.*;

public class FriendNetwork extends Graph<User, String> {

    public FriendNetwork() {
        super();
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

    // implementado para o exercício 4

    public List<User> getClosestFriends(User u,List<Country> countryList)
    {
        List<User> friendsList = new ArrayList<>();

        for(User friends : this.getAdjacentVertices(u))
        {
            if(countryList.contains(friends.getCity()))
            {
                friendsList.add(friends);
            }
        }

        return friendsList;
    }

    public User getUser (String userName)
    {
        for(User userU : vertices())
        {
            if (userU.getName().equalsIgnoreCase(userName))
            {
                return userU;
            }
        }
        return null;
    }

    @Override
    public boolean insertEdge(User vOrig, User vDest, String edge) {
        boolean inserted = super.insertEdge(vOrig, vDest, edge);
        if (inserted) {
            this.getVertex(vOrig).addFriend();
            this.getVertex(vDest).addFriend();
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
}
