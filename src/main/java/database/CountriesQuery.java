package database;

import abstractions.Customer;
import database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountriesQuery {

    public static ObservableList<String> getAllCountries(){
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Country FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                allCountries.add(rs.getString(1));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return allCountries;
    }
}
