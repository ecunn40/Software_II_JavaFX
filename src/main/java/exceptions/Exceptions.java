package exceptions;

import database.CustomersQuery;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.Main;

import java.sql.SQLException;
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

    public static int validateState(String selectedState) throws Exception{
        try{
            return CustomersQuery.createDivisionId(selectedState);
        }catch (SQLException sqlException){
            makeAlert(Alert.AlertType.ERROR, "No State Selected", "Please Choose A State");
            throw new Exception("Error");
        }
    }
}
