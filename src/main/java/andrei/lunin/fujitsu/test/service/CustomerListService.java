package andrei.lunin.fujitsu.test.service;

import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CustomerListService {
    private static final Logger LOG = LoggerFactory
            .getLogger(CustomerListService.class);

    CustomerList customerList;

    @Autowired
    public CustomerListService(CustomerList customerList) {
        this.customerList = customerList;
    }

    public void createTestCustomerListService() {
        LOG.info("The test CustomerListService started");
        Customer customer = new Customer(1L, -1); // new Customer with right 'id' and wrong 'bonus'
        customerList.addCustomer(customer);   // wrong 'bonus' -> FALSE
        customer.setBonus(2);  // modifying 'bonus' to wrong value
        customerList.addCustomer(customer); // wrong 'bonus' -> FALSE
        customer.setBonus(0);  // modifying 'bonus'
        customerList.addCustomer(customer); // right 'id' and 'bonus' -> TRUE
        customerList.addCustomer(customer);  // adding a duplicate -> FALSE

        customer = new Customer(2L, -10);  // new Customer with right 'id' and wrong 'bonus'
        customerList.addCustomer(customer); // wrong 'bonus' -> FALSE
        customer.setBonus(20);  // modifying 'bonus' to wrong value
        customerList.addCustomer(customer); // wrong 'bonus' -> FALSE
        customer.setBonus(0); // modifying 'bonus' to right value
        customerList.addCustomer(customer); // right 'id' and 'bonus' -> TRUE
        customerList.addCustomer(customer); // adding a duplicate -> FALSE

        customer = new Customer(3L, -5); // new Customer with right 'id' and wrong 'bonus'
        customerList.addCustomer(customer); // wrong 'bonus' -> FALSE
        customer.setBonus(7); // modifying 'bonus' to wrong value
        customerList.addCustomer(customer);// wrong 'bonus' -> FALSE
        customer.setBonus(0); // modifying 'bonus' to right value
        customerList.addCustomer(customer); // right 'id' and 'bonus' -> TRUE
        customerList.addCustomer(customer); // adding a duplicate -> FALSE

        customerList.findCustomerById(9L); // wrong 'id' -> FALSE
        customerList.findCustomerById(5L); // wrong 'id' -> FALSE
        customerList.findCustomerById(1L); // right 'id' -> Customer
        customerList.findCustomerById(2L); // right 'id' -> Customer
        customerList.findCustomerById(3L); // right 'id' -> Customer
        customerList.findCustomerById(13L); // wrong 'id' -> null

        customer = new Customer(4L, -9); // new Customer with right 'id' and wrong 'bonus'
        customerList.addCustomer(customer); // wrong 'bonus' -> FALSE
        customer.setBonus(17); // modifying 'bonus' to wrong value
        customerList.addCustomer(customer); // wrong 'bonus' -> FALSE
        customer.setBonus(0); // modifying 'bonus' to right value
        customerList.addCustomer(customer); // right 'id' and 'bonus' -> TRUE
        customerList.addCustomer(customer); // adding a duplicate -> FALSE
        customerList.removeCustomer(new Customer(5L, 0)); // removing a Customer with wrong 'id' -> null
        customerList.removeCustomer(customer); // removing a Customer with right 'id' -> Customer

        LOG.info("Intermediate testing customer list:\n" + customerList); // three Customers -> id:1 bonus:0, id:2 bonus:0, id:3 bonus:0

        customerList.changeBonus(customerList.findCustomerById(10L), -10); // wrong increment -> FALSE
        customerList.changeBonus(customerList.findCustomerById(-2L), 5); // wrong 'id' -> FALSE
        customerList.changeBonus(customerList.findCustomerById(0L), -10); // wrong increment and 'id'  -> FALSE
        customerList.changeBonus(customerList.findCustomerById(1L), -10); // wrong increment -> FALSE
        customerList.changeBonus(customerList.findCustomerById(2L), 10); // right increment and 'id' -> TRUE
        customerList.initializeBonus(5L); // wrong 'id' -> FALSE
        customerList.initializeBonus(-4L); // wrong 'id' -> FALSE
        customerList.initializeBonus(2L); // right 'id' -> TRUE

        LOG.info("Final testing customer list:\n" + customerList); // three Customers -> id:1 bonus:0, id:2 bonus:0, id:3 bonus:0
        LOG.info("The test CustomerListService finished");
    }
}
