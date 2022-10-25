package abstractions;

public class Customer {

    private int customer_id;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone;
    private int division_id;

    public Customer(int customer_id, String customer_name, String address, String postal_code, String phone, int division_id) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division_id = division_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public int getDivision_id() {
        return division_id;
    }
}
