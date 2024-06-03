package application.controller;

import application.model.User;
import application.dao.UserDAO;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(String username) {
        userDAO.deleteUser(username);
    }
}
