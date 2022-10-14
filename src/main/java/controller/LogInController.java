package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInController extends Main implements Initializable {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML
    private Text title;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label location;
    @FXML
    private Button loginButton;
    private String userLocation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Locale.getDefault().getLanguage().equals("fr")) {
            title.setText(rb.getString("Login"));
            title.setLayoutX(160.0);
            username.setText(rb.getString("username"));
            username.setLayoutX(100.0);
            password.setText(rb.getString("password"));
            password.setLayoutX(125.0);
            location.setText(rb.getString("location" + ": " + ZoneId.systemDefault()));
            loginButton.setText(rb.getString("Login"));
        }
        location.setText("location: " + ZoneId.systemDefault());
    }

    @FXML
    protected void LogIn(ActionEvent actionEvent) throws IOException {
        loadFile(actionEvent, "Customers.fxml");
    }
}
