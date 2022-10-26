package controller;

import abstractions.Appointment;
import abstractions.Contact;
import database.AppointmentsQuery;
import database.ContactsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static controller.AppointmentController.addingAppointment;
import static controller.AppointmentController.selectedAppointment;

public class AddAppointmentController extends Main implements Initializable {
    @FXML
    private Text appointmentText;
    @FXML
    private TextField appointmentIdInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField description;
    @FXML
    private TextField locationInput;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private TextField typeInput;
    @FXML
    private DatePicker startInput;
    @FXML
    private ComboBox startTime;
    @FXML
    private DatePicker endInput;
    @FXML
    private ComboBox endTime;
    @FXML
    private TextField customerIdInput;
    @FXML
    private TextField userIdInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!addingAppointment){
            setAppointmentData(selectedAppointment);
        } else
            appointmentIdInput.setText(AppointmentsQuery.getAllAppointments().size() + 1 + "");

        contactComboBox.setPromptText("Choose a contact");
        contactComboBox.setItems(ContactsQuery.getAllContactNames());

        setTime();
    }

    private void setTime() {
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(6, 0);
        LocalTime end = LocalTime.of(5, 50);

        while(start.isBefore(end.plusSeconds(1))){
            startTime.getItems().add(start);
            start = start.plusMinutes(10);

            endTime.getItems().add(end);
            end = end.plusMinutes(10);
        }
    }

    private void setAppointmentData(Appointment appointment) {
        appointmentText.setText("Update Appointment");
        appointmentIdInput.setText(appointment.getAppointmentId() + "");
        titleInput.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        locationInput.setText(appointment.getLocation());
        contactComboBox.setValue(ContactsQuery.getContactName(appointment.getContactId()));
        typeInput.setText(appointment.getType());
        startInput.setValue(appointment.getAppointmentStart().toLocalDate());
        startTime.setValue(appointment.getAppointmentStart().toLocalTime());
        endInput.setValue(appointment.getAppointmentEnd().toLocalDate());
        endTime.setValue(appointment.getAppointmentEnd().toLocalTime());
        customerIdInput.setText(appointment.getCustomerId() + "");
        userIdInput.setText(AppointmentsQuery.getUserId(appointment.getAppointmentId()) + "");
    }

    public void onCancelButtonClick(ActionEvent event) throws IOException {
        onCancelButtonClick(event, APPOINTMENT_FORM);
    }
}
