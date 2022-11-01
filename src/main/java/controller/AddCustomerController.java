package controller;

import abstractions.Customer;
import database.CountriesQuery;
import database.CustomersQuery;
import database.DivisionQuery;
import exceptions.Exceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.CustomerController.addingCustomer;
import static controller.CustomerController.selectedCustomer;

/**
 * Controller class for the AddCustomer form
 */
public class AddCustomerController extends Main implements Initializable {

    @FXML
    private TextField customerIdField;
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

    /**
     * Initializes the Add Customer form with given or empty values if adding or updating a customer
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!addingCustomer)
            setCustomerData(selectedCustomer);
        else
            customerIdField.setText(CustomersQuery.customerCount + "");

        countryComboBox.setPromptText("Choose a Country");
        countryComboBox.setItems(CountriesQuery.getAllCountries());
    }

    /**
     * Sets all field values based on given customer
     * @param customer
     */
    private void setCustomerData(Customer customer){
        customerIdField.setText(customer.getCustomer_id() + "");
        nameInput.setText(customer.getCustomer_name());
        addressInput.setText(customer.getAddress());
        postalCodeInput.setText(customer.getPostal_code());
        phoneInput.setText(customer.getPhone());
        countryComboBox.setValue(DivisionQuery.getCountry(customer.getDivision_id()));
        stateComboBox.setValue(DivisionQuery.getState(customer.getDivision_id()));
    }

    /**
     * Sets the first level divisions based on the given country selection
     * @param event
     */
    @FXML
    public void onCountrySelected(ActionEvent event){
        switch (countryComboBox.getValue()){
            case "U.S":
                stateComboBox.setItems(DivisionQuery.getAllStates());
                break;
            case "Canada":
                stateComboBox.setItems(DivisionQuery.getAllProvinces());
                break;
            case "UK":
                stateComboBox.setItems(DivisionQuery.getAllRegions());
                break;
            default:
                break;
        }
    }

    /**
     * Cancels all changes and loads previous form
     * @param event
     * @throws IOException
     */
    @FXML
    private void onCancelButtonClick(ActionEvent event) throws IOException {
        onCancelButtonClick(event, CUSTOMER_FORM);
    }

    /**
     * Validates all given values, either inserts or updates customers in the database, and loads the previous form
     * @param event
     * @throws Exception
     */
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

            if(addingCustomer)
                CustomersQuery.insertCustomer(customerName, address, postal_code, phone, division_id);
            else
                CustomersQuery.updateCustomer(selectedCustomer.getCustomer_id(), customerName, address, postal_code, phone, division_id);

        } catch (Exception e){
            return;
        }
        loadFile(event, CUSTOMER_FORM);
    }

}
