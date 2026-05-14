package services;

import java.util.Vector;

import database.UDBM;
import exceptions.UserNotFoundException;
import users.User;

public class UserService {
    private Vector<User> users = new Vector<>();
    private static final UDBM db = new UDBM();

    public void addUser(User user, String password) {
        users.add(user);
        db.addUser(user, password);
    }

    public static UserService getInstance(){
        return new UserService();
    }

    public void removeUser(String id) throws UserNotFoundException {
        User user = findById(id);
        users.remove(user);
        db.removeUser(id);
    }

    public User findById(String id) throws UserNotFoundException {
    User user = db.getUserByID(id);

    if (user == null) {
        throw new UserNotFoundException("User with id " + id + " not found.");
    }

    return user;
}

    public User findByLogin(String login) throws UserNotFoundException {
      User user = db.getUserByUsername(login);

    if (user == null) {
        throw new UserNotFoundException("User with id " + login + " not found.");
    }

    return user;
    }

    public Vector<User> getUsers(){
        this.users = db.getUsers();
        return users;
    }
}
