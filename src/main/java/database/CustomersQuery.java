package database;

import abstractions.Customer;
import javafx.scene.control.TableView;
import main.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomersQuery {
    public static void fillCustomerTable(TableView tableView){
        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customer_id = rs.getInt(1);
                String customer_name = rs.getString(2);
                String address = rs.getString(3);
                String postal_code = rs.getString(4);
                String phone = rs.getString(5);
                int division_id = rs.getInt(6);

                Customer customer = new Customer(customer_id, customer_name, address, postal_code, phone, division_id);
                Customers.addCustomer(customer);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
