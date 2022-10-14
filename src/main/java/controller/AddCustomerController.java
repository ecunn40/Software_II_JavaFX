package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.Main;

import java.io.IOException;

public class AddCustomerController extends Main {

    @FXML
    private void onCancelButtonClick(ActionEvent event) throws IOException {
        onCancelButtonClick(event, CUSTOMER_FORM);
    }
}
