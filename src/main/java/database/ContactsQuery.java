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
    public static ObservableList getAllContactNames(){
        ObservableList<String> allNames = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Contact_Name FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String contactName = rs.getString(1);

                allNames.add(contactName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allNames;
    }

    public static String getContactName(int contactId){
        String name= "" ;
        try{
            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();

            rs.next();
            name = rs.getString(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }

    public static int getContactId(String contactName){
        int contactId = 0;
        try{
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            rs.next();
            contactId = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactId;
    }
}
