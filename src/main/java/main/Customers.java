package main;

import abstractions.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    public static void updateCustomer(int index, Customer customer) {
        allCustomers.set(index, customer);
    }

    public static void removeCustomer(Customer customer) {allCustomers.remove(customer);}

    public static void removeAllCustomers(){ allCustomers.clear(); }
}
