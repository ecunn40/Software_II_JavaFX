package main;

import abstractions.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Countries {

    public static ObservableList<String> allCountries = FXCollections.observableArrayList();

    public ObservableList<String> getAllCountries(){
        return allCountries;
    }

    private static void init(){
        if(allCountries.size() == 0){

        }
    }
}
