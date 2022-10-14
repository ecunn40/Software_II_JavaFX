package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.Main;

import java.io.IOException;

public class AppointmentController extends Main {
    @FXML
    private void onBackButtonPressed(ActionEvent event) throws IOException {
        loadFile(event, CUSTOMER_FORM);
    }
    @FXML
    public void onAddButtonClicked(ActionEvent event) throws IOException {
        loadFile(event, ADD_APPOINTMENT_FORM);
    }
}
