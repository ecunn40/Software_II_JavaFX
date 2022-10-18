package controller;

import abstractions.Customer;
import database.CustomersQuery;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Customers;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController extends Main implements Initializable {

    @FXML
    private TableView customersTable;
    @FXML
    private TableColumn customer_id_column;
    @FXML
    private TableColumn customer_name_column;
    @FXML
    private TableColumn address_column;
    @FXML
    private TableColumn postal_code_column;
    @FXML
    private TableColumn phone_column;
    @FXML
    private TableColumn division_id_column;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customersTable.setItems(Customers.getAllCustomers());

        customer_id_column.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customer_name_column.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        address_column.setCellValueFactory(new PropertyValueFactory<>("address"));
        postal_code_column.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        division_id_column.setCellValueFactory(new PropertyValueFactory<>("division_id"));

        CustomersQuery.fillCustomerTable(customersTable);
    }

    @FXML
    public void LogOut(ActionEvent event) throws IOException {
        JDBC.closeConnection();
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
