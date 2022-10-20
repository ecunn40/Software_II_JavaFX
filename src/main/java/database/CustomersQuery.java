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
            System.out.println(customer.getCustomer_id());
            String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_Id) VALUES(NULL, ?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, customer.getCustomer_name());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostal_code());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, customer.getDivision_id());

            ps.executeUpdate();
            //System.out.println(rowsAffected);

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(Customer customer){
        try {
            String sqlName = "UPDATE CUSTOMERS SET Customer_Name = ? WHERE Customer_ID = ?";
            String sqlAdd = "UPDATE CUSTOMERS SET Address = ? WHERE Customer_ID = ?";
            String sqlPost = "UPDATE CUSTOMERS SET Postal_Code = ? WHERE Customer_ID = ?";
            String sqlPhone = "UPDATE CUSTOMERS SET Phone = ? WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlName);
            PreparedStatement psAdd = JDBC.getConnection().prepareStatement(sqlAdd);
            PreparedStatement psPost = JDBC.getConnection().prepareStatement(sqlPost);
            PreparedStatement psPhone = JDBC.getConnection().prepareStatement(sqlPhone);
            ps.setString(1, customer.getCustomer_name());
            psAdd.setString(1, selectedCustomer.getAddress());
            psPost.setString(1, selectedCustomer.getPostal_code());
            psPhone.setString(1, selectedCustomer.getPhone());
            ps.setInt(2, selectedCustomer.getCustomer_id());
            psAdd.setInt(2, selectedCustomer.getCustomer_id());
            psPost.setInt(2, selectedCustomer.getCustomer_id());
            psPhone.setInt(2, selectedCustomer.getCustomer_id());
            ps.execute();
            psAdd.execute();
            psPost.execute();
            psPhone.execute();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void deleteCustomer(int customer_id) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customer_id);
        ps.execute();
    }

}
