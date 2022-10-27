package exceptions;

import abstractions.Appointment;
import controller.AppointmentController;
import database.AppointmentsQuery;
import database.CustomersQuery;
import database.DivisionQuery;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Main;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Exceptions extends Main {

    /**
     * Validates the given Integer
     * @param textField
     * @return
     * @throws Exception
     */
    public static Integer validateAndParseInteger(TextField textField) throws Exception {
        try{
            return Integer.parseUnsignedInt(textField.getText());
        }catch (NumberFormatException e){
            makeAlert(Alert.AlertType.ERROR, "Invalid " + textField.getId(), "Please enter a valid " + textField.getId() + " value");
            throw new Exception("Error");
        }
    }

    /**
     * Validates and parses the given  id
     * @param textField text field
     * @return the parsed integer
     * @throws Exception an exception
     */
    public static Integer validateAndParseId(TextField textField) throws Exception {
        try{
            return Integer.parseUnsignedInt(textField.getText());
        }catch (NumberFormatException e){
            makeAlert(Alert.AlertType.ERROR, "Invalid ID", "Please enter a valid ID");
            throw new Exception("Error");
        }
    }

    /**
     * Validates the given string
     * @param textField text field
     * @return the validated string
     * @throws Exception an exception
     */
    public static String validateString(TextField textField) throws Exception{
        if(!textField.getText().isEmpty())
            return textField.getText();
        else{
            makeAlert(Alert.AlertType.ERROR, "Invalid " + textField.getId(), "Please enter a valid " + textField.getId());
            throw new Exception("Error");
        }
    }
    /**
     * Validates the address
     * @param textField text field
     * @return the validated string
     * @throws Exception an exception
     */
    public static String validateAddress(TextField textField) throws Exception {
        Pattern pattern = Pattern.compile("[a-zA-Z]+",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(textField.getText());
        boolean matchFound = matcher.find();
        if(matchFound) return textField.getText();
        else {
            makeAlert(Alert.AlertType.ERROR, "Invalid Address", "Please enter a valid Address");
            throw new Exception("Error");
        }
    }

    /**
     * Validates the postal code
     * @param textField text field
     * @return the validated string
     * @throws Exception an exception
     */
    public static String validatePostalCode(TextField textField) throws Exception {
        Pattern pattern = Pattern.compile("[a-zA-Z]+",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(textField.getText());
        boolean matchFound = matcher.find();
        if(matchFound) return textField.getText();
        else {
            makeAlert(Alert.AlertType.ERROR, "Invalid Postal Code", "Please enter a valid Postal Code");
            throw new Exception("Error");
        }
    }
    /**
     * Validates the phone number
     * @param textField text field
     * @return the validated string
     * @throws Exception an exception
     */
    public static String validatePhoneNumber(TextField textField) throws Exception {
        Pattern pattern = Pattern.compile("[a-zA-Z]+",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(textField.getText());
        boolean matchFound = matcher.find();
        if(matchFound) return textField.getText();
        else {
            makeAlert(Alert.AlertType.ERROR, "Invalid Phone Number", "Please enter a valid Phone Number");
            throw new Exception("Error");
        }
    }

    public static void validateCountry(String selectedCountry) throws Exception{
        if(selectedCountry == null)
        {
            makeAlert(Alert.AlertType.ERROR, "No Country Selected", "Please Choose a Country");
            throw new Exception("Error");
        }
    }

    public static int getDivisionId(String selectedState) throws Exception{
        try{
            return DivisionQuery.getDivisionId(selectedState);
        }catch (SQLException sqlException){
            makeAlert(Alert.AlertType.ERROR, "No State Selected", "Please Choose A State");
            throw new Exception("Error");
        }
    }

    public static void validateAppointments(LocalDateTime proposedAppointmentStart, LocalDateTime proposedAppointmentEnd, int appointmentId) throws Exception {
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        if(proposedAppointmentStart.isAfter(proposedAppointmentEnd)){
            makeAlert(Alert.AlertType.ERROR, "Appointment Conflict", "Appointment Start cannot be after Appointment End");
            throw new Exception("Error");
        }
        if(!AppointmentController.addingAppointment) {
            findConflict(allAppointments.filtered(appointment -> appointment.getAppointmentId() != appointmentId), proposedAppointmentStart, proposedAppointmentEnd);
        }
        else
            findConflict(allAppointments, proposedAppointmentStart, proposedAppointmentEnd);

    }

    private static void findConflict(ObservableList<Appointment> existingAppointments, LocalDateTime propApptSt, LocalDateTime propApptEnd) throws RuntimeException {

        existingAppointments.forEach(existingAppointment -> {
            if(propApptSt.isAfter(existingAppointment.getAppointmentEnd()) || propApptEnd.isBefore(existingAppointment.getAppointmentStart())){
                return;
            }
            makeAlert(Alert.AlertType.ERROR, "Appointment Conflict", "Proposed Appt: " + propApptSt + " to " + propApptEnd + " conflicts with existing Appt: " + existingAppointment.getAppointmentStart() + " to " + existingAppointment.getAppointmentEnd());
            throw new RuntimeException("Error");
        });
    }
}
