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
import java.time.*;
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
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        LocalDate date = ZonedDateTime.now().toLocalDate();
        LocalTime easternStartTime = LocalTime.of(8, 0);
        LocalTime easternEndTime = LocalTime.of(22, 0);

        ZonedDateTime easternStartZDT = ZonedDateTime.of(date, easternStartTime, easternZoneId);
        ZonedDateTime easternEndZDT = ZonedDateTime.of(date, easternEndTime, easternZoneId);

        ZoneId localZoneId = ZoneId.of(ZoneId.systemDefault().getId());

        ZonedDateTime localStartZDT = easternStartZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime localEndZDT = easternEndZDT.withZoneSameInstant(localZoneId);

        LocalTime localStartTime = localStartZDT.toLocalTime();
        LocalTime localEndTime = localEndZDT.toLocalTime();

        while(localStartTime.isBefore(localEndTime.plusSeconds(1))){
            if(localStartTime != localEndTime){
                startTime.getItems().add(localStartTime);
            }
            if(localStartTime != localStartZDT.toLocalTime())
            {
                endTime.getItems().add(localStartTime);
            }
            localStartTime = localStartTime.plusHours(1);
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
        startInput.setValue(LocalDateTime.parse(appointment.getAppointmentStart(), Appointment.dtf).toLocalDate());
        startTime.setValue(LocalDateTime.parse(appointment.getAppointmentStart(), Appointment.dtf).toLocalTime());
        endInput.setValue(LocalDateTime.parse(appointment.getAppointmentEnd(), Appointment.dtf).toLocalDate());
        endTime.setValue(LocalDateTime.parse(appointment.getAppointmentEnd(), Appointment.dtf).toLocalTime());
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
            appointmentStart = LocalDateTime.of(startInput.getValue(), startTime.getValue());
            appointmentEnd = LocalDateTime.of(endInput.getValue(), endTime.getValue());
            Exceptions.validateAppointments(appointmentStart, appointmentEnd, Exceptions.validateAndParseId(appointmentIdInput));
            customerId = (int) customerIdInput.getSelectionModel().getSelectedItem();
            userId = (int) userIdInput.getSelectionModel().getSelectedItem();
            contactId = ContactsQuery.getContactId(contactComboBox.getValue());

            if(addingAppointment)
                AppointmentsQuery.insertAppointment(title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);
            else
                AppointmentsQuery.updateAppointment(selectedAppointment.getAppointmentId(), title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);

        } catch (Exception e){
            e.printStackTrace();
        }
        loadFile(event, APPOINTMENT_FORM);
        System.out.println("Saved");
    }
}
