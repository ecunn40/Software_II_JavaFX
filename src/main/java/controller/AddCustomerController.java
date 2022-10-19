package controller;

import abstractions.Customer;
import exceptions.Exceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Customers;
import main.Main;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController extends Main {

    @FXML
    private int customerId = 1;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField postalCodeInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField divisionIdInput;

    @FXML
    private ComboBox countryComboBox;
    @FXML
    private ComboBox stateComboBox;

    @FXML
    private void onCancelButtonClick(ActionEvent event) throws IOException {
        onCancelButtonClick(event, CUSTOMER_FORM);
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) throws Exception {
        String customerName;
        String address;
        String postal_code;
        String phone;
        int divisionId;

        try{
            customerName = Exceptions.validateString(nameInput);
            address = Exceptions.validateString(addressInput);
            postal_code = Exceptions.validateString(postalCodeInput);
            phone = Exceptions.validateString(phoneInput);
            divisionId = Exceptions.validateAndParseId(divisionIdInput);

            Customer customer = new Customer(customerId, customerName,address,postal_code,phone,divisionId);
            Customers.addCustomer(customer);
            customerId++;
        } catch (Exception e){
            return;
        }
        loadFile(event, CUSTOMER_FORM);
        System.out.println("Saved");
    }
}
