import java.util.Objects;

/**
 * Classe que modela um Utilizador, com a respetiva informação demográfica e de volume de amigos.
 */
public class User {
    /**
     * Nome do utilizador.
     */
    private String name;
    /**
     * Idade do utilizador.
     */
    private int age;
    /**
     * Nome da cidade do utilizador.
     */
    private String city;
    /**
     * Número de amigos do utilizador na rede.
     */
    int numFriends;
    //------------------------------- Construtores ---------------------------------

    /**
     * Construtor completo da classe User.
     *
     * @param name: nome do utilizador
     * @param age:  idade do utilizador
     * @param city: nome da cidade do utilizador
     */
    public User(String name, String age, String city) {
        this.name = name.trim();
        this.age = Integer.parseInt(age);
        this.city = city.trim();
    }

    /**
     * Construtor parcial da classe User.
     * Deve ser utilizado para criar utilizadores "cópia" a ser passados como parâmetro para o registo de amizades entre utilizadores.
     *
     * @param name O nome do utilizador.
     */
    public User(String name) {
        this.name = name.trim();
        this.age = 0;
        this.city = null;
    }
    //------------------------------- Getters e Setters ---------------------------------

    /**
     * @return nome do utilizador.
     */
    public String getName() {
        return name;
    }

    /**
     * @return nome da cidade do utilizador.
     */
    public String getCity() {
        return city;
    }

    /**
     * @return número de amigos do utilizador.
     */
    public int getNumFriends() {
        return numFriends;
    }
    //------------------------------- Métodos ---------------------------------

    /**
     * Incrementa o nº de amigos do utilizador.
     */
    public void addFriend() {
        numFriends++;
    }

    /**
     * Decrementa o nº de amigos do utilizador.
     */
    public void removeFriend() {
        numFriends--;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
