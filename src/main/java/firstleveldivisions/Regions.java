package firstleveldivisions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public abstract class Regions {
    public static ObservableList<String> allRegions = FXCollections.observableArrayList(Arrays.asList("England","Scotland","Wales","Northern Ireland"));

    public static ObservableList<String> getallRegions(){
        return allRegions;
    }
}
