package application.service;

import application.dao.UserDAO;
import application.model.User;

public class AuthenticationService {
    private AuthenticationStrategy strategy;
    private UserDAO userDAO;
    private User loggedInUser;

    public AuthenticationService(AuthenticationStrategy strategy, UserDAO userDAO) {
        this.strategy = strategy;
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password) {
        User user = userDAO.getUser(username);
        if (user != null && strategy.authenticate(user, password)) {
            loggedInUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
