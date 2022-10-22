package controller;

import abstractions.Customer;
import countries.Countries;
import database.CustomersQuery;
import database.DivisionQuery;
import exceptions.Exceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.CustomerController.addingCustomer;
import static controller.CustomerController.selectedCustomer;

public class AddCustomerController extends Main implements Initializable {

    @FXML
    private int customerId;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField postalCodeInput;
    @FXML
    private TextField phoneInput;

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> stateComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!addingCustomer)
            setCustomerData(selectedCustomer);

        countryComboBox.setPromptText("Choose a Country");
        countryComboBox.setItems(Countries.getAllCountries());
    }

    private void setCustomerData(Customer customer){
        nameInput.setText(customer.getCustomer_name());
        addressInput.setText(customer.getAddress());
        postalCodeInput.setText(customer.getPostal_code());
        phoneInput.setText(customer.getPhone());
        countryComboBox.setValue(DivisionQuery.getCountry(customer.getDivision_id()));
        stateComboBox.setValue(DivisionQuery.getState(customer.getDivision_id()));
    }

    @FXML
    public void onCountrySelected(ActionEvent event){
        switch (countryComboBox.getValue()){
            case "United States":
                stateComboBox.setItems(DivisionQuery.getAllStates());
                break;
            case "Canada":
                stateComboBox.setItems(DivisionQuery.getAllProvinces());
                break;
            case "United Kingdom":
                stateComboBox.setItems(DivisionQuery.getAllRegions());
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
        int division_id;

        try{
            customerName = Exceptions.validateString(nameInput);
            address = Exceptions.validateString(addressInput);
            postal_code = Exceptions.validateString(postalCodeInput);
            phone = Exceptions.validateString(phoneInput);
            Exceptions.validateCountry(countryComboBox.getValue());
            division_id = Exceptions.getDivisionId(stateComboBox.getValue());
            System.out.println("Selected State: "+ stateComboBox.getValue());
            System.out.println("Division ID: "+ division_id);

            if(addingCustomer)
                CustomersQuery.insertCustomer(customerName, address, postal_code, phone, division_id);
            else
                CustomersQuery.updateCustomer(selectedCustomer.getCustomer_id(), customerName, address, postal_code, phone, division_id);

        } catch (Exception e){
            return;
        }
        loadFile(event, CUSTOMER_FORM);
        System.out.println("Saved");
    }

}
