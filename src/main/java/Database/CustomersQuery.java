package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomersQuery {
    public static void showCustomers(){
        try {
            String sql = "SELECT Customer_Name FROM customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("Customer_Name"));
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
