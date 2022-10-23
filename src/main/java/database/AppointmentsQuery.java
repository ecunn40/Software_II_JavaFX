package database;

import abstractions.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public abstract class AppointmentsQuery {

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
                Date appointmentStart = rs.getDate(6);
                Date appointmentEnd = rs.getDate(7);
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

    public static void insertAppointment(String title, String description, String location, String type, Date appointmentStart, Date appointmentEnd, int customerId, int userId, int contactId){
        try {
            String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setDate(5, appointmentStart);
            ps.setDate(6, appointmentEnd);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);

            ps.execute();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, Date appointmentStart, Date appointmentEnd, int customerId, int userId, int contactId){
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
            psStart.setDate(1, appointmentStart);
            psEnd.setDate(1, appointmentEnd);
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
            psEnd.execute();
            psCustId.execute();
            psUserId.execute();
            psContId.execute();

            resetAutoInc();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ps.execute();
        resetAutoInc();
    }

    public static void resetAutoInc() throws SQLException {
        String sql = "ALTER TABLE appointments AUTO_INCREMENT = 1";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.execute();
    }
}
