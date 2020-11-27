import java.util.Objects;

public class User {
    private String name;
    private int age;
    private String city;
    int numFriends;

    public User(String name, String age, String city) {
        this.setName(name.trim());
        this.setAge(Integer.parseInt(age));
        this.setCity(city.trim());
    }

    public User(String name) {
        this.setName(name.trim());
        this.setAge(0);
        this.setCity(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return getName().equalsIgnoreCase(((User) obj).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, city);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void addFriend() {
        numFriends++;
    }

    public void removeFriend() {
        numFriends--;
    }

    public int getNumFriends() {
        return numFriends;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
