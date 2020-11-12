public class FriendNetwork extends Graph<User, String> {
    private Graph<User, String> friendNetwork;

    public FriendNetwork() {
        friendNetwork = new Graph<User, String>();
    }
}
