package countries;

import abstractions.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Countries {

    public static ObservableList<String> allCountries = FXCollections.observableArrayList();

    static{
        init();
    }

    private static void init(){
        if(allCountries.size() == 0){
            allCountries.add("United States");
            allCountries.add("Canada");
            allCountries.add("United Kingdom");
        }
    }

    public static ObservableList<String> getAllCountries(){
        return allCountries;
    }
}
