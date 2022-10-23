package controller;

import abstractions.Appointment;
import database.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import main.Main;

import java.io.IOException;
import java.sql.SQLException;

public class AppointmentController extends Main {

    @FXML
    private TableView appointmentsTable;

    public static boolean addingAppointment;
    private static Appointment selectedAppointment;

    @FXML
    private void onBackButtonPressed(ActionEvent event) throws IOException {
        loadFile(event, APPOINTMENT_FORM);
    }
    @FXML
    public void onAddButtonClicked(ActionEvent event) throws IOException {
        loadFile(event, ADD_APPOINTMENT_FORM);
    }
    @FXML
    public void onUpdateButtonClicked(ActionEvent event) {
        addingAppointment = false;
        try{
            selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
            loadFile(event, ADD_APPOINTMENT_FORM);
        } catch (Exception e){
            makeAlert(Alert.AlertType.ERROR, "No Appointments Selected", "Please select a Appointments");
        }
    }
    @FXML
    public void onDeleteButtonClicked(ActionEvent event) throws SQLException {
        selectedAppointment = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Appointments?");
            if(alert.showAndWait().get() == ButtonType.OK){
                AppointmentsQuery.deleteAppointment(selectedAppointment.getAppointmentId());
            }
        } else{
            makeAlert(Alert.AlertType.ERROR, "No Appointments Selected", "Please select a Appointments");
        }
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
    }
}
