package services;

import exceptions.AuthenticationException;
import exceptions.UserNotFoundException;
import users.User;

public class AuthService {
    private UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String login, String password) throws AuthenticationException {
        try {
            User user = userService.findByLogin(login);
            if (!user.checkPassword(password)) {
                throw new AuthenticationException("Wrong password.");
            }
            return user;
        } catch (UserNotFoundException e) {
            throw new AuthenticationException("Wrong login.");
        }
    }
}
