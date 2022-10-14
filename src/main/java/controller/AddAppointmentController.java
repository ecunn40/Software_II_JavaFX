package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.Main;

import java.io.IOException;

public class AddAppointmentController extends Main {

    public void onCancelButtonClick(ActionEvent event) throws IOException {
        onCancelButtonClick(event, APPOINTMENT_FORM);
    }
}
