package controllers;

import exceptions.AuthenticationException;
import services.AuthService;
import users.User;

public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public User login(String login, String password) throws AuthenticationException {
        return authService.login(login, password);
    }
}
