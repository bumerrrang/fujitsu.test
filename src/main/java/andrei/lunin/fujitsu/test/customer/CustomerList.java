package andrei.lunin.fujitsu.test.customer;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Component
public class CustomerList {

    private static final Logger LOG = LoggerFactory
            .getLogger(CustomerList.class);

    private final List<Customer> customerList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Customer customer : customerList) {
            result.append("id:").append(customer.getId()).append(", bonus:").append(customer.getBonus()).append("\n");
        }
        return result.toString();
    }

    public boolean addCustomer(Customer customer) {
        if (customer.getBonus() != 0) {
            LOG.warn("New Customer " + customer + " can not have not '0' bonuses at first!");
            return false;
        }
        if (customerList.contains(customer)) {
            LOG.info("Customer " + customer + " is already in the customerList");
            return false;
        }
        LOG.info("Customer " + customer + " was added to the customerList");
        return customerList.add(customer);
    }

    public Customer findCustomerById(Long id) {
        Optional<Customer> customer =
                customerList.stream().filter(c -> c.getId().equals(id)).findAny();
        if (customer.isPresent()) {
            LOG.info("Customer with id " + id + " is in the customerList");
            return customer.get();
        } else {
            LOG.warn("Customer with id " + id + " DOES NOT EXIST in the customerList");
            return null;
        }
    }

    public Customer removeCustomer(Customer customer) {
        Customer customerRemove = findCustomerById(customer.getId());
        if (customerRemove != null) {
            customerList.remove(customerRemove);
            LOG.info("Customer with id " + customer.getId() + " was removed from the customerList");
        } else {
            LOG.warn("Customer with id " + customer.getId() + " DOES NOT EXIST in the customerList");
        }
        return customerRemove;
    }

    public boolean changeBonus(Customer customer, int changeBonusvar) {
        if (customer == null) {
            LOG.warn("The Customer " + customer + " must exist in the customerList for changing its bonus.");
            return false;
        }
        if (customer.getBonus() + changeBonusvar >= 0) {
            customer.setBonus(customer.getBonus() + changeBonusvar);
            LOG.info("Customer with id " + customer.getId() + " bonus was changed with " + changeBonusvar);
            return true;
        }
        LOG.warn("Customer " + customer + " has not enough bonus to be changed with " + changeBonusvar);
        return false;
    }

    public boolean initializeBonus(Long customerId) {
        Customer customer = findCustomerById(customerId);
        if (customer != null) {
            customer.setBonus(0);
            LOG.info("Customer with id " + customer.getId() + " bonus was initialized with 0");
            return true;
        }
        LOG.warn("Customer with id " + customerId + " DOES NOT exist in the list");
        return false;
    }
}
