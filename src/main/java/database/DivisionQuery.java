package database;

import abstractions.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionQuery {

    public static int getDivisionId(String state) throws SQLException {
        int division_id;
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, state);

        ResultSet rs = ps.executeQuery();
        rs.next();
        division_id = rs.getInt(1);

        return division_id;
    }

    public static ObservableList<String> getAllStates(){
        ObservableList<String> allStates = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID <= 54";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allStates.add(rs.getString(1));
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allStates;
    }

    public static ObservableList<String> getAllProvinces(){
        ObservableList<String> allProvinces = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID BETWEEN 55 AND 72";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allProvinces.add(rs.getString(1));
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allProvinces;
    }

    public static ObservableList<String> getAllRegions(){
        ObservableList<String> allRegions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID > 72";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allRegions.add(rs.getString(1));
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allRegions;
    }

    public static String getState(int divisionId){
        try{
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, divisionId);

            ResultSet rs = ps.executeQuery();

            rs.next();
            return rs.getString(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getCountry(int divisionId){
        try{
            String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, divisionId);

            ResultSet rs = ps.executeQuery();

            rs.next();
            switch (rs.getInt(1)){
                case 1: return "U.S";
                case 2: return "UK";
                case 3: return "Canada";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
