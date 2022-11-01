package database;

import abstractions.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Abstract class to hold all database queries on the users table
 */
public abstract class UsersQuery {
    /**
     *
     * @return all users in users table
     */
    public static ObservableList getAllUsers() {
        ObservableList allUsers = FXCollections.observableArrayList();
        try{
            String sql = "SELECT User_ID FROM users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                allUsers.add(rs.getInt(1));
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return allUsers;
    }

    /**
     *
     * @param userName
     * @return user id with given username
     */
    public static int getUserId(String userName){
        int userId = 0;
        try{
            String sql = "SELECT User_ID FROM users WHERE User_Name = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            rs.next();
            userId = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userId;
    }

    /**
     * @param userId
     * @return user appointments with given user id
     */
    public static ObservableList<Appointment> getUserAppointments(int userId){
        ObservableList userAppointments = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Appointment_ID, Start FROM Appointments WHERE User_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Appointment userAppointment = new Appointment(rs.getInt(1), " "," ", " ", " ", rs.getTimestamp(2).toLocalDateTime(), LocalDateTime.now(), 0,0,0);
                userAppointments.add(userAppointment);
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return userAppointments;
    }
}
