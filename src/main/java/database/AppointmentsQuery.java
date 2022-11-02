package database;

import abstractions.Appointment;
import abstractions.Contact;
import controller.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Abstract class to hold all database queries on the appointments table
 */
public abstract class AppointmentsQuery {
    /**
     * @return all appointments in appointments table
     */
    public static ObservableList getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt(1);
                String title = rs.getString(2);
                String description = rs.getString(3);
                String location = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime appointmentStart = rs.getTimestamp(6).toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp(7).toLocalDateTime();
                int customerId = rs.getInt(8);
                int userId = rs.getInt(9);
                int contactId = rs.getInt(10);

                Appointment appointment = new Appointment(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);
                allAppointments.add(appointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Inserts given appointment in appointment table
     * @param title
     * @param description
     * @param location
     * @param type
     * @param appointmentStart
     * @param appointmentEnd
     * @param customerId
     * @param userId
     * @param contactId
     */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerId, int userId, int contactId){
        try {
            String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            ps.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);

            ps.execute();
            resetAutoInc();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**
     * Updates appointment in database with given appointment.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param appointmentStart
     * @param appointmentEnd
     * @param customerId
     * @param userId
     * @param contactId
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerId, int userId, int contactId){
        try {
            String sqlTitle = "UPDATE APPOINTMENTS SET Title = ? WHERE Appointment_ID = ?";
            String sqlDesc = "UPDATE APPOINTMENTS SET Description = ? WHERE Appointment_ID = ?";
            String sqlLoc = "UPDATE APPOINTMENTS SET Location = ? WHERE Appointment_ID = ?";
            String sqlType = "UPDATE APPOINTMENTS SET Type = ? WHERE Appointment_ID = ?";
            String sqlStart = "UPDATE APPOINTMENTS SET Start = ? WHERE Appointment_ID = ?";
            String sqlEnd = "UPDATE APPOINTMENTS SET End = ? WHERE Appointment_ID = ?";
            String sqlCustId = "UPDATE APPOINTMENTS SET Customer_ID = ? WHERE Appointment_ID = ?";
            String sqlUserId = "UPDATE APPOINTMENTS SET User_ID = ? WHERE Appointment_ID = ?";
            String sqlContId = "UPDATE APPOINTMENTS SET Contact_ID = ? WHERE Appointment_ID = ?";

            PreparedStatement psTitle = JDBC.getConnection().prepareStatement(sqlTitle);
            PreparedStatement psDesc = JDBC.getConnection().prepareStatement(sqlDesc);
            PreparedStatement psLoc = JDBC.getConnection().prepareStatement(sqlLoc);
            PreparedStatement psType = JDBC.getConnection().prepareStatement(sqlType);
            PreparedStatement psStart = JDBC.getConnection().prepareStatement(sqlStart);
            PreparedStatement psEnd = JDBC.getConnection().prepareStatement(sqlEnd);
            PreparedStatement psCustId = JDBC.getConnection().prepareStatement(sqlCustId);
            PreparedStatement psUserId = JDBC.getConnection().prepareStatement(sqlUserId);
            PreparedStatement psContId = JDBC.getConnection().prepareStatement(sqlContId);

            psTitle.setString(1, title);
            psDesc.setString(1, description);
            psLoc.setString(1, location);
            psType.setString(1, type);
            psStart.setTimestamp(1, Timestamp.valueOf(appointmentStart));
            psEnd.setTimestamp(1, Timestamp.valueOf(appointmentEnd));
            psCustId.setInt(1, customerId);
            psUserId.setInt(1, userId);
            psContId.setInt(1, contactId);

            psTitle.setInt(2, appointmentId);
            psDesc.setInt(2, appointmentId);
            psLoc.setInt(2, appointmentId);
            psType.setInt(2, appointmentId);
            psStart.setInt(2, appointmentId);
            psEnd.setInt(2, appointmentId);
            psCustId.setInt(2, appointmentId);
            psUserId.setInt(2, appointmentId);
            psContId.setInt(2, appointmentId);

            psTitle.execute();
            psDesc.execute();
            psLoc.execute();
            psType.execute();
            psStart.execute();
            psEnd.execute();
            psCustId.execute();
            psUserId.execute();
            psContId.execute();

            resetAutoInc();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**
     * @param appointmentId
     * @return user id with matching appointment id
     */
    public static int getUserId(int appointmentId) {
        int userId = 0;
        try{
            String sql = "SELECT User_ID FROM APPOINTMENTS Where Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, appointmentId);

            ResultSet rs = ps.executeQuery();
            rs.next();

            userId = rs.getInt(1);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return userId;
    }

    /**
     * Deletes appointment with given appointment id
     * @param appointmentId
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, appointmentId);

        ps.execute();
        resetAutoInc();
    }

    /**
     * Deletes appointment with given customer id
     * @param customerId
     * @throws SQLException
     */
    public static void deleteCustomerAppointments(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.execute();
        resetAutoInc();
    }

    /**
     * Resets the auto-increment to 1
     * @throws SQLException
     */
    public static void resetAutoInc() throws SQLException {
        String sql = "ALTER TABLE appointments AUTO_INCREMENT = 1";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.execute();
    }

    /**
     * @param button
     * @return all dates ordered by either month or week based on the selected radio button
     * @throws SQLException
     */
    public static ObservableList getAllDateTimes(RadioButton button) throws SQLException {

        ObservableList sortedBy = FXCollections.observableArrayList();
        int currentMonth = ZonedDateTime.now().getMonthValue();
        try {
            String sql = "";

            if(button.getId().equals("monthButton"))
                sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments WHERE Month(Start) = ?";
            else
                sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments WHERE Week(Start, 0) = Week(?, 0)";


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            if(button.getId().equals("monthButton"))
                ps.setInt(1, currentMonth);
            else
                ps.setTimestamp(1, Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt(1);
                String title = rs.getString(2);
                String description = rs.getString(3);
                String location = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime appointmentStart = rs.getTimestamp(6).toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp(7).toLocalDateTime();
                int customerId = rs.getInt(8);
                int userId = rs.getInt(9);
                int contactId = rs.getInt(10);

                Appointment appointment = new Appointment(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);
                sortedBy.add(appointment);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return sortedBy;
    }

    /**
     * @return all locations in the appointments table
     */
    public static ObservableList getAllLocations() {
        ObservableList allLocations = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Location FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allLocations.add(rs.getString(1));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return allLocations;
    }

    /**
     * @return all types in the appointments table
     */
    public static ObservableList getAllTypes() {
        ObservableList allTypes = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Type FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allTypes.add(rs.getString(1));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return allTypes;
    }

    /**
     * @return all months in the appointments table
     */
    public static ObservableList getAllDates() {
        ObservableList allDates = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Month(Start) FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allDates.add(rs.getString(1));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        return allDates;
    }

    /**
     * @param locationSelection
     * @return number of appointments at given location
     */
    public static int getLocationCount(String locationSelection) {
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) FROM appointments WHERE Location = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, locationSelection);

            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return count;
    }

    /**
     * @param type
     * @param month
     * @return number of appointments with given type and month
     */
    public static int getApptByMonth(String type, String month){
        int count = 0;
        try{
            String sql = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND Month(Start)= ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, type);
            ps.setString(2, month);


            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return count;
    }

    /**
     * @param contactId
     * @return number of appointments with the given contact id
     */
    public static ObservableList getContactAppointments(int contactId) {
        ObservableList allContactAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID FROM appointments WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt(1);
                String title = rs.getString(2);
                String description = rs.getString(3);
                String location = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime appointmentStart = rs.getTimestamp(6).toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp(7).toLocalDateTime();
                int customerId = rs.getInt(8);
                int userId = rs.getInt(9);

                Appointment appointment = new Appointment(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);
                allContactAppointments.add(appointment);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return allContactAppointments;
    }
}
