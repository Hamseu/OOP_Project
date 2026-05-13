package controllers;

import java.util.Vector;

import database.UDBM;
import exceptions.UserNotFoundException;
import users.User;

public class AdminController {
    private UDBM userService;

    public AdminController(UDBM userService) {
        this.userService = userService;
    }

    public void addUser(User user, String password) {
        userService.addUser(user, password);
    }

    public void removeUser(String id) throws UserNotFoundException {
        if(userService.removeUser(id)){
            System.out.println("User removed");
        }
        else {
            System.out.println("Failed to remove");
        }
    }

    public Vector<User> viewUsers() {
        return userService.getUsers();
    }
}
