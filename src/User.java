public class User {
    private String name;
    private int age;
    private String city;

    public User(String name, String age, String city) {
        this.name = name.trim();
        this.age = Integer.parseInt(age);
        this.city = city.trim();
    }

    public User (String name) {
        this.name = name.trim();
        this.age = 0;
        this.city = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        return name.equalsIgnoreCase(((User) obj).name);
    }
}
