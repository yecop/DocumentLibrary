package application.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import application.service.AuthenticationService;
import application.dao.UserDAO;
import application.service.DatabaseAuthenticationStrategy;

public class LoginUI extends Application {
    private AuthenticationService authService;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize AuthenticationService with DatabaseAuthenticationStrategy and UserDAO
        UserDAO userDAO = new UserDAO();
        DatabaseAuthenticationStrategy authStrategy = new DatabaseAuthenticationStrategy();
        authService = new AuthenticationService(authStrategy, userDAO);

        primaryStage.setTitle("Login");

        GridPane gridPane = new GridPane();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin(String username, String password) {
        if (authService.login(username, password)) {
            // Login successful, show the main UI
            MainUI mainUI = new MainUI(authService);
            try {
                mainUI.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
