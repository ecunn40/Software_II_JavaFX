package controller;

import abstractions.Appointment;
import database.AppointmentsQuery;
import database.ContactsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller class for Appointment form
 */
public class AppointmentController extends Main implements Initializable {

    @FXML
    private TableView appointmentsTable;
    @FXML
    private TableColumn appointmentId_column;
    @FXML
    private TableColumn title_column;
    @FXML
    private TableColumn description_column;
    @FXML
    private TableColumn location_column;
    @FXML
    private TableColumn type_column;
    @FXML
    private TableColumn appointmentStart_column;
    @FXML
    private TableColumn appointmentEnd_column;
    @FXML
    private TableColumn customerId_column;
    @FXML
    private TableColumn userId_column;
    @FXML
    private TableColumn contactId_column;
    @FXML
    private ComboBox locationComboBox;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private ComboBox dateComboBox;
    @FXML
    private ComboBox contactComboBox;
    @FXML
    private ToggleGroup toggleGroup;

    public static boolean addingAppointment;
    public static Appointment selectedAppointment = null;
    private static String selectedType = null;
    private static String selectedMonth = null;

    private ObservableList allLocations = FXCollections.observableArrayList();
    private ObservableList allTypes = FXCollections.observableArrayList();
    private ObservableList allDates = FXCollections.observableArrayList();

    /**
     * Initializes the table with all appointments from the database. Initializes the locations, types and dates in all appointments in seperate lists.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
        allLocations = AppointmentsQuery.getAllLocations();
        allTypes = AppointmentsQuery.getAllTypes();
        allDates = AppointmentsQuery.getAllDates();
        locationComboBox.setItems(returnDistinct(allLocations));
        typeComboBox.setItems(returnDistinct(allTypes));
        dateComboBox.setItems(returnDistinct(allDates));
        contactComboBox.setItems(ContactsQuery.getAllContactNames());

        appointmentId_column.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title_column.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_column.setCellValueFactory(new PropertyValueFactory<>("location"));
        type_column.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart_column.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appointmentEnd_column.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        customerId_column.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId_column.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactId_column.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    /**
     * @param list
     * @return a distinct list with no repeating elements
     */
    private ObservableList returnDistinct(ObservableList list){
        return (ObservableList) list.stream().distinct().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Updates the table to show all appointments
     */
    @FXML
    private void showAllAppointments(){
        if(toggleGroup.getSelectedToggle() != null)
            toggleGroup.getSelectedToggle().setSelected(false);
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
    }

    /**
     * Sorts the table based on the radio button selected(Month or Week)
     * @param event
     * @throws SQLException
     */
    @FXML
    private void sortBy(ActionEvent event) throws SQLException {
        RadioButton button = (RadioButton) event.getSource();
        appointmentsTable.setItems(AppointmentsQuery.getAllDateTimes(button));
    }

    /**
     * Loads the previous form
     * @param event
     * @throws IOException
     */
    @FXML
    private void onBackButtonPressed(ActionEvent event) throws IOException {
        loadFile(event, CUSTOMER_FORM);
    }

    /**
     * Sets the addingAppointment variable and loads the AddAppointment form.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onAddButtonClicked(ActionEvent event) throws IOException {
        addingAppointment = true;
        loadFile(event, ADD_APPOINTMENT_FORM);
    }

    /**
     * Creates an alert with the number of appointments that are in the selected location
     * @param event
     */
    @FXML
    public void onLocationButtonClicked(ActionEvent event) {
        String locationSelection = (String) locationComboBox.getSelectionModel().getSelectedItem();
        int count = AppointmentsQuery.getLocationCount(locationSelection);

        makeAlert(Alert.AlertType.INFORMATION, "Appointments Found", String.format("There are %d appointments at %s", count, locationSelection));
    }

    /**
     * Creates an alert with the number of appointments that share the same type and month
     * @param event
     */
    @FXML
    public void onApptTypeClicked(ActionEvent event) {
        selectedType = (String) typeComboBox.getSelectionModel().getSelectedItem();

        if(selectedMonth != null)
        {
            int count = AppointmentsQuery.getApptByMonth(selectedType, selectedMonth);
            makeAlert(Alert.AlertType.INFORMATION, "Appointments Found", String.format(" %s - %s - %d", selectedType, selectedMonth, count));
        }
    }
    /**
     * Creates an alert with the number of appointments that share the same type and month
     * @param event
     */
    @FXML
    public void onApptDateClicked(ActionEvent event) {
        selectedMonth = (String) dateComboBox.getSelectionModel().getSelectedItem();

        if(selectedType != null)
        {
            int count = AppointmentsQuery.getApptByMonth(selectedType, selectedMonth);
            makeAlert(Alert.AlertType.INFORMATION, "Appointments Found", String.format(" %s - %s - %d", selectedType, selectedMonth, count));
        }
    }

    /**
     * Sets the add appointment variable, validates the selected appointment and loads the next form
     * @param event
     */
    @FXML
    public void onUpdateButtonClicked(ActionEvent event) {
        addingAppointment = false;
        try{
            selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
            loadFile(event, ADD_APPOINTMENT_FORM);
        } catch (Exception e){
            makeAlert(Alert.AlertType.ERROR, "No Appointment Selected", "Please select an Appointment");
        }
    }

    /**
     * Validates the given appointment, and deletes the appointment from the database
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onDeleteButtonClicked(ActionEvent event) throws SQLException {
        selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Appointment?");
            if(alert.showAndWait().get() == ButtonType.OK){
                AppointmentsQuery.deleteAppointment(selectedAppointment.getAppointmentId());
                makeAlert(Alert.AlertType.INFORMATION, "Appointment Deleted" , "Appointment with Appointment Id " + selectedAppointment.getAppointmentId() + " and Appointment Type " + selectedAppointment.getType() + " has been deleted");
            }
        } else{
            makeAlert(Alert.AlertType.ERROR, "No Appointment Selected", "Please select an Appointment");
        }
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
    }

    /**
     * Sets the table with the appointments that share the same contactId
     * @param event
     */
    public void onContactBoxClicked(ActionEvent event) {
        String selectedContact = (String) contactComboBox.getSelectionModel().getSelectedItem();
        ObservableList contactAppts = AppointmentsQuery.getContactAppointments(ContactsQuery.getContactId(selectedContact));

        appointmentsTable.setItems(contactAppts);
    }
}
