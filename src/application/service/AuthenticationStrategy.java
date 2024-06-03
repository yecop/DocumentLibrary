package application.service;

import application.model.User;

public interface AuthenticationStrategy {
    boolean authenticate(User user, String password);
}
