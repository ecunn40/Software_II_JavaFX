package controller;

import abstractions.Appointment;
import database.JDBC;
import database.UsersQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Stream;

public class LogInController extends Main implements Initializable {

    public static String userName;

    private ResourceBundle resourceBundle;

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
        this.resourceBundle = ResourceBundle.getBundle("lang", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")) {
            title.setText(this.resourceBundle.getString("Login"));
            title.setLayoutX(160.0);
            usernameLabel.setText(this.resourceBundle.getString("username"));
            usernameLabel.setLayoutX(100.0);
            passwordLabel.setText(this.resourceBundle.getString("password"));
            passwordLabel.setLayoutX(125.0);
            location.setText(this.resourceBundle.getString("location") + ": " + ZoneId.systemDefault());
            loginButton.setText(this.resourceBundle.getString("Login"));
        }
        else
            location.setText("location: " + ZoneId.systemDefault());
    }

    @FXML
    protected void LogIn(ActionEvent actionEvent) throws IOException {
        JDBC.openConnection();
        userName = "test";
//        if(JDBC.validateLogin(usernameField.getText(), passwordField.getText())) {
        checkForAppointments();
//        JDBC.openConnection();
        loadFile(actionEvent, "Customers.fxml");
//        }
//        else
//            makeAlert(Alert.AlertType.ERROR, "Invalid username or password", "Please enter the valid username and password");
    }

    private void checkForAppointments(){
        ObservableList<Appointment> userAppointments = UsersQuery.getUserAppointments(UsersQuery.getUserId("test"));

        LocalTime nowPlus15Minutes = ZonedDateTime.now().toLocalTime().plusMinutes(15);
        String alertTitle = "Upcoming Appointment!";
        String alertMessage = "You have an appointment with an ID of ";
        String alertTitleNo = "No Upcoming Appointments!";
        String alertMessageNo = "You have no upcoming appointments";
        String on = "on";
        String at = "at";

        if(Locale.getDefault().getLanguage().equals("fr")){
            alertTitle = this.resourceBundle.getString("upcomingTitle");
            alertMessage = this.resourceBundle.getString("upcomingMessage");
            alertTitleNo = this.resourceBundle.getString("noupcomingTitle");
            alertMessageNo = this.resourceBundle.getString("noupcomingMessage");
            on = this.resourceBundle.getString("on");
            at = this.resourceBundle.getString("on");
        }

        Optional<Appointment> apptWithinFifteen = userAppointments.stream().filter(
                appointment -> nowPlus15Minutes.compareTo(
                        appointment.getAppointmentTStart().toLocalTime()) > 0
                        && nowPlus15Minutes.compareTo(appointment.getAppointmentTStart().toLocalTime().plusMinutes(15)) < 0
                        && ZonedDateTime.now().toLocalDate() == appointment.getAppointmentTStart().toLocalDate()
        ).findFirst();

        String finalAlertTitle = alertTitle;
        String finalAlertMessage = alertMessage;
        String finalAlertTitleNo = alertTitleNo;
        String finalAlertMessageNo = alertMessageNo;
        String finalOn = on;
        String finalAt = at;
        apptWithinFifteen.ifPresentOrElse(
                (appointment)
                        -> {makeAlert(Alert.AlertType.INFORMATION, finalAlertTitle,
                        finalAlertMessage + appointment.getAppointmentId()
                                + String.format(" %s ", finalAt) + appointment.getAppointmentTStart().toLocalDate() + String.format(" %s ", finalOn) + appointment.getAppointmentTStart().toLocalTime() + "!");},
                () -> {makeAlert(Alert.AlertType.INFORMATION, finalAlertTitleNo, finalAlertMessageNo);}
        );
    }

}
