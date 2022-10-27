package controller;

import database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LogInController extends Main implements Initializable {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    public static String userName;

    @FXML
    private Text title;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordField;
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
            usernameLabel.setText(rb.getString("username"));
            usernameLabel.setLayoutX(100.0);
            passwordLabel.setText(rb.getString("password"));
            passwordLabel.setLayoutX(125.0);
            location.setText(rb.getString("location" + ": " + ZoneId.systemDefault()));
            loginButton.setText(rb.getString("Login"));
        }
        //ZoneId.getAvailableZoneIds().stream().forEach(zone -> System.out.println(zone));
        location.setText("location: " + ZoneId.systemDefault());
        //System.out.println(ZonedDateTime.now());
        LocalDate parisDate = LocalDate.of(2019, 10, 26);
        LocalTime parisTime = LocalTime.of(1, 00);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");

        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);

        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        Instant parisToGMTInstant = parisZDT.toInstant();
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId);

        //System.out.println("Local: " + ZonedDateTime.now());
//        System.out.println("Paris: " + parisZDT);
//        System.out.println("Paris->GMT: " + parisToGMTInstant);
//        System.out.println("GMT->Local: " + gmtToLocalZDT);
//        System.out.println("Paris->Local " + parisToLocalZDT);
    }

    @FXML
    protected void LogIn(ActionEvent actionEvent) throws IOException {
        JDBC.openConnection();
        loadFile(actionEvent, "Customers.fxml");
        userName = "test";
//        if(JDBC.validateLogin(usernameField.getText(), passwordField.getText())) {
//            JDBC.openConnection();
//            loadFile(actionEvent, "Customers.fxml");
//        }
//        else
//            makeAlert(Alert.AlertType.ERROR, "Invalid username or password", "Please enter the valid username and password");
    }
}
