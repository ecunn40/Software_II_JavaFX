package controller;

import abstractions.Appointment;
import abstractions.Contact;
import database.AppointmentsQuery;
import database.ContactsQuery;
import database.CustomersQuery;
import database.UsersQuery;
import exceptions.Exceptions;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static controller.AppointmentController.addingAppointment;
import static controller.AppointmentController.selectedAppointment;
import static controller.CustomerController.selectedCustomer;

public class AddAppointmentController extends Main implements Initializable {
    @FXML
    private Text appointmentText;
    @FXML
    private TextField appointmentIdInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField descriptionInput;
    @FXML
    private TextField locationInput;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private TextField typeInput;
    @FXML
    private DatePicker startInput;
    @FXML
    private ComboBox<LocalTime> startTime;
    @FXML
    private DatePicker endInput;
    @FXML
    private ComboBox<LocalTime> endTime;
    @FXML
    private ComboBox customerIdInput;
    @FXML
    private ComboBox userIdInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!addingAppointment){
            setAppointmentData(selectedAppointment);
        } else {
            appointmentIdInput.setText(AppointmentsQuery.getAllAppointments().size() + 1 + "");
            userIdInput.getSelectionModel().select(1);
        }

        contactComboBox.setPromptText("Choose a contact");
        contactComboBox.setItems(ContactsQuery.getAllContactNames());
        userIdInput.setItems(UsersQuery.getAllUsers());
        customerIdInput.setItems(CustomersQuery.getCustomerIds());

        setTime();
    }

    private void setTime() {
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        while(start.isBefore(end.plusSeconds(1))){
            if(start != LocalTime.of(22,0)){
                startTime.getItems().add(start);
            }
            if(start != LocalTime.of(8, 0))
            {
                endTime.getItems().add(start);
            }
            start = start.plusHours(1);
        }
    }
    @FXML
    private void onTimeChosen(ActionEvent event){
        ComboBox timePicker = (ComboBox) event.getTarget();
        switch (timePicker.getId()){
            case "startTime": setEndTime((LocalTime) timePicker.getValue());
            break;
            case "endTime": setStartTime((LocalTime) timePicker.getValue());
            break;
            default:
        }
    }

    private void setStartTime(LocalTime endTime) {
        startTime.setValue(endTime.minusHours(1));
    }

    private void setEndTime(LocalTime startTime) {
        endTime.setValue(startTime.plusHours(1));
    }

    @FXML
    private void onDateChosen(ActionEvent event){
        DatePicker datePicker = (DatePicker) event.getSource();
        switch (datePicker.getId()){
            case "startInput": endInput.setValue(startInput.getValue());
            case "endInput": startInput.setValue(endInput.getValue());
        }
    }

    private void setAppointmentData(Appointment appointment) {
        appointmentText.setText("Update Appointment");
        appointmentIdInput.setText(appointment.getAppointmentId() + "");
        titleInput.setText(appointment.getTitle());
        descriptionInput.setText(appointment.getDescription());
        locationInput.setText(appointment.getLocation());
        contactComboBox.setValue(ContactsQuery.getContactName(appointment.getContactId()));
        typeInput.setText(appointment.getType());
        startInput.setValue(appointment.getAppointmentStart().toLocalDate());
        startTime.setValue(appointment.getAppointmentStart().toLocalTime());
        endInput.setValue(appointment.getAppointmentEnd().toLocalDate());
        endTime.setValue(appointment.getAppointmentEnd().toLocalTime());
        customerIdInput.setValue(appointment.getCustomerId());
        userIdInput.setValue(AppointmentsQuery.getUserId(appointment.getUserId()));
    }

    public void onCancelButtonClick(ActionEvent event) throws IOException {
        onCancelButtonClick(event, APPOINTMENT_FORM);
    }

    public void onSaveButtonClick(ActionEvent event) throws IOException{
        String title;
        String description;
        String location;
        String type;
        LocalDateTime appointmentStart;
        LocalDateTime appointmentEnd;
        int customerId;
        int userId;
        int contactId;

        try{
            title = Exceptions.validateString(titleInput);
            description = Exceptions.validateString(descriptionInput);
            location = Exceptions.validateString(locationInput);
            type = Exceptions.validateString(typeInput);
            appointmentStart = LocalDateTime.of(startInput.getValue(), (LocalTime) startTime.getValue());
            appointmentEnd = LocalDateTime.of(endInput.getValue(), (LocalTime) endTime.getValue());
            Exceptions.validateAppointments(appointmentStart, appointmentEnd, Exceptions.validateAndParseId(appointmentIdInput));
            customerId = (int) customerIdInput.getSelectionModel().getSelectedItem();
            userId = (int) userIdInput.getSelectionModel().getSelectedItem();
            contactId = ContactsQuery.getContactId(contactComboBox.getValue());

            if(addingAppointment)
                AppointmentsQuery.insertAppointment(title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);
            else
                AppointmentsQuery.updateAppointment(selectedAppointment.getAppointmentId(), title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);

        } catch (Exception e){
            return;
        }
        loadFile(event, APPOINTMENT_FORM);
        System.out.println("Saved");
    }
}
