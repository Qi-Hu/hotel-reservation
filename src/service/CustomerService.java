package service;

import model.Customer;

import java.util.*;

/**
 * @author qihu
 */

public class CustomerService {

    private static final CustomerService instance = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<String, Customer>();

    private CustomerService(){}

    public static CustomerService getInstance(){
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName){
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String email){
        return customers.get(email);
    }

    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }
}
