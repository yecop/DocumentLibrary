package application.service;

import application.model.User;

public class DatabaseAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public boolean authenticate(User user, String password) {
        return user.getPassword().equals(password);  // Simplified for illustration
    }
}
