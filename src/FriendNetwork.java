import java.util.*;

public class FriendNetwork extends Graph<User, String> {

    public FriendNetwork() {
        super();
    }

    public LinkedList<User> usersByPopularity()
    {
        LinkedList<User> friendships = new LinkedList<>(Collections.nCopies(this.numVertices(), null));

        for (User vertex : this.vertices()) {
            int connections = this.outDegree(vertex);

            friendships.add(connections, vertex);
        }

        Collections.reverse(friendships);

        friendships.removeAll(Arrays.asList(null,""));

        return friendships;
    }

    public Set<User> friendsInCommon(User vOrig,User vDest)
    {
        Set<User> vOrigFriends = new HashSet<>(this.getAdjacentVertices(vOrig));

        Set<User> vDestFriends = new HashSet<>(this.getAdjacentVertices(vDest));

        vOrigFriends.retainAll(vDestFriends);

        return vOrigFriends;
    }

    public Set<User> friendsInCommon(List<User> auxList,int size)
    {
        Set<Set<User>> vFinalFriends = new HashSet<>();

        ListIterator<User> i = auxList.listIterator();

        while(i.hasNext() && i.nextIndex()<size)
        {
            User vOrig = i.next();
            User vDest = i.next();

            vFinalFriends.add(friendsInCommon(vOrig,vDest));
        }

        return intersections(vFinalFriends).iterator().next();
    }

    public Set<Set<User>> intersections(Set<Set<User>> auxSet)
    {
        Set<Set<User>> newAuxSet = new HashSet<>();
        Iterator<Set<User>> i = auxSet.iterator();

        if(auxSet.size() %2 != 0)
        {
            newAuxSet.add(i.next());
        }

        while(i.hasNext())
        {
            Set<User> firstCommonFriends = i.next();
            Set<User> secondCommonFriends = i.next();

            firstCommonFriends.retainAll(secondCommonFriends);

            newAuxSet.add(firstCommonFriends);
        }

        if(auxSet.size()<=1) {
            return newAuxSet;
        }

        return intersections(newAuxSet);
    }
}
