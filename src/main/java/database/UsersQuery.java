package database;

import abstractions.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class UsersQuery {

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
