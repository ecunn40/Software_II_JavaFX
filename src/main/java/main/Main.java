package main;

import abstractions.Customer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public final String LOGIN_FORM = "LogIn.fxml";
    public final String CUSTOMER_FORM = "Customers.fxml";
    public final String ADD_CUSTOMER_FORM = "AddCustomer.fxml";
    public final String APPOINTMENT_FORM = "Appointments.fxml";
    public final String ADD_APPOINTMENT_FORM = "AddAppointment.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadFile(ActionEvent actionEvent, String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/" + filename));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    @FXML
    public void onCancelButtonClick(ActionEvent event, String fileName) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        if(alert.showAndWait().get() == ButtonType.OK){
            loadFile(event, fileName);
        }
    }

    public static void makeAlert(Alert.AlertType alertType, String alertTitle, String alertMsg){
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
