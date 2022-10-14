package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.Main;

import java.io.IOException;

public class CustomerController extends Main {

    @FXML
    public void LogOut(ActionEvent event) throws IOException {
        loadFile(event, LOGIN_FORM);
    }
    @FXML
    public void onAddButtonClicked(ActionEvent event) throws IOException {
        loadFile(event, ADD_CUSTOMER_FORM);
    }
    @FXML
    public void onUpdateButtonClicked(ActionEvent event) throws IOException {
        loadFile(event, ADD_CUSTOMER_FORM);
    }
    @FXML
    public void onDeleteButtonClicked(){
        //TODO Delete selection
    }
    @FXML
    public void onAppointmentsButtonClicked(ActionEvent event) throws IOException {
        loadFile(event, APPOINTMENT_FORM);
    }
}
