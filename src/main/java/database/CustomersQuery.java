package database;

import abstractions.Customer;
import javafx.scene.control.TableView;
import main.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controller.CustomerController.selectedCustomer;

public abstract class CustomersQuery {
    public static void fillCustomerTable(){
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
                int division_Id = rs.getInt(6);

                Customer customer = new Customer(customer_id, customer_name, address, postal_code, phone, division_Id);
                Customers.addCustomer(customer);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void insertCustomer(Customer customer){
        try {
            String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_Id) VALUES(?, ?, ?, ?, ?)";
4
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, customer.getCustomer_name());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostal_code());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivision_id());

            int rowsAffected = ps.executeUpdate();
            //System.out.println(rowsAffected);

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(Customer customer){
        try {
            String sql = "UPDATE CUSTOMERS SET Customer_Name = ? WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customer.getCustomer_name());
            ps.setInt(2, selectedCustomer.getCustomer_id());

            int rowsAffected = ps.executeUpdate();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

}
