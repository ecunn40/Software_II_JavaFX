package firstleveldivisions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public abstract class Provinces {
    public static ObservableList<String> allProvinces = FXCollections.observableArrayList(Arrays.asList("Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon"));

    public static ObservableList<String> getAllProvinces(){
        return allProvinces;
    }
}
