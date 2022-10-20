package controller;

import abstractions.Customer;
import countries.Countries;
import database.CustomersQuery;
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
    private int divisionId;

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> stateComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(addingCustomer){
            countryComboBox.setPromptText("Choose a Country");
            countryComboBox.setItems(Countries.getAllCountries());
        }
        else
        {
            System.out.println(selectedCustomer.getCustomer_id());
            setCustomerData(selectedCustomer);
        }
    }

    private void setCustomerData(Customer customer){
        nameInput.setText(customer.getCustomer_name());
        addressInput.setText(customer.getAddress());
        postalCodeInput.setText(customer.getPostal_code());
        phoneInput.setText(customer.getPhone());
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

        try{
            customerName = Exceptions.validateString(nameInput);
            address = Exceptions.validateString(addressInput);
            postal_code = Exceptions.validateString(postalCodeInput);
            phone = Exceptions.validateString(phoneInput);

            if(addingCustomer){
                Customer customer = new Customer(Customers.getAllCustomers().size() + 1, customerName, address, postal_code, phone, divisionId);
                CustomersQuery.insertCustomer(customer);
                Customers.addCustomer(customer);
                System.out.println("Customer Added");
            } else{
                Customer customer = new Customer(selectedCustomer.getCustomer_id(), customerName, address, postal_code, phone, selectedCustomer.getDivision_id());
                Customers.updateCustomer(selectedCustomer.getCustomer_id() - 1, customer);
                CustomersQuery.updateCustomer(customer);
            }

        } catch (Exception e){
            return;
        }
        loadFile(event, CUSTOMER_FORM);
        System.out.println("Saved");
    }
}
