package controller;

import abstractions.Customer;
import countries.Countries;
import exceptions.Exceptions;
import firstleveldivisions.Provinces;
import firstleveldivisions.Regions;
import firstleveldivisions.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController extends Main implements Initializable {

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
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> stateComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setPromptText("Choose a Country");
        countryComboBox.setItems(Countries.getAllCountries());
    }
    @FXML
    public void onCountrySelected(ActionEvent event){
        String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        switch (selectedCountry){
            case "United States":
                stateComboBox.setItems(States.getAllStates());
                break;
            case "Canada":
                stateComboBox.setItems(Provinces.getAllProvinces());
                break;
            case "United Kingdom":
                stateComboBox.setItems(Regions.getallRegions());
                break;
            default:
                break;
        }
    }

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
