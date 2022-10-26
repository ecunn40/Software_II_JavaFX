package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
