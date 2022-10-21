package database;

import abstractions.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class CustomersQuery {
    public static ObservableList getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
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
                allCustomers.add(customer);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allCustomers;
    }

    public static void insertCustomer(String name, String address, String postalCode, String phoneNumber, int division_id){
        try {
            String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, division_id);

            ps.execute();
            //System.out.println(rowsAffected);

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateCustomer(int customerId, String name, String address, String postalCode, String phoneNumber, int division_id){
        try {
            String sqlName = "UPDATE CUSTOMERS SET Customer_Name = ? WHERE Customer_ID = ?";
            String sqlAdd = "UPDATE CUSTOMERS SET Address = ? WHERE Customer_ID = ?";
            String sqlPost = "UPDATE CUSTOMERS SET Postal_Code = ? WHERE Customer_ID = ?";
            String sqlPhone = "UPDATE CUSTOMERS SET Phone = ? WHERE Customer_ID = ?";
            String sqlDiv = "UPDATE CUSTOMERS SET Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlName);
            PreparedStatement psAdd = JDBC.getConnection().prepareStatement(sqlAdd);
            PreparedStatement psPost = JDBC.getConnection().prepareStatement(sqlPost);
            PreparedStatement psPhone = JDBC.getConnection().prepareStatement(sqlPhone);
            PreparedStatement psDiv = JDBC.getConnection().prepareStatement(sqlDiv);
            ps.setString(1, name);
            psAdd.setString(1, address);
            psPost.setString(1, postalCode);
            psPhone.setString(1, phoneNumber);
            psDiv.setInt(1, division_id);
            ps.setInt(2, customerId);
            psAdd.setInt(2, customerId);
            psPost.setInt(2, customerId);
            psPhone.setInt(2, customerId);
            psDiv.setInt(2, division_id);
            ps.execute();
            psAdd.execute();
            psPost.execute();
            psPhone.execute();
            psDiv.execute();

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

    public static int createDivisionId(String state) throws SQLException{
        int division_id;
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, state);

        ResultSet rs = ps.executeQuery();
        rs.next();
        division_id = rs.getInt(1);

        return division_id;
    }
}
