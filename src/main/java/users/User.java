package users;

import java.io.Serializable;
import java.util.Objects;

import system.UserType;

public class User implements Serializable {
    private String id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private UserType profile;

    public User(String id, String email, String login, String name, String surname, String role) {
        this.id = id;
        this.email = email;
        this.username = login;
        this.name = name;
        this.surname = surname;
        this.profile = UserType.valueOf(role);
    }


    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public UserType getProfile() {
        return this.profile;
    }

    public String getEmail(){
        return this.email;
    }


    @Override
    public String toString() {
        return this.id + ": " + this.profile + ": " + getFullName() + " (" + this.username + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName(){
        return this.name;
    }
    
    public String getSurname(){
        return this.surname;
    }


    public boolean checkPassword(String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkPassword'");
    }
}
