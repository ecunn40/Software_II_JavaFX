package database;

import abstractions.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactsQuery {

    public static ObservableList getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contactId = rs.getInt(1);
                String contactName = rs.getString(2);
                String contactEmail = rs.getString(3);

                Contact contact = new Contact(contactId, contactName, contactEmail);
                allContacts.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }
}
