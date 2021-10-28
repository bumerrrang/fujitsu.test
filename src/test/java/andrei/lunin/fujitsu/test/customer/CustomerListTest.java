package andrei.lunin.fujitsu.test.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerList.class})
@ExtendWith(SpringExtension.class)
class CustomerListTest {
    @Autowired
    private CustomerList customerList;

    @Test
    void testToString() {
        assertEquals("", (new CustomerList()).toString());
    }

    @Test
    void testToString2() {
        CustomerList customerList = new CustomerList();
        customerList.addCustomer(new Customer());
        assertEquals("id:null, bonus:0\n", customerList.toString());
    }

    @Test
    void testAddCustomer() {
        assertFalse(this.customerList.addCustomer(new Customer()));
        assertFalse(this.customerList.addCustomer(new Customer(123L, 1)));
    }

    @Test
    void testAddCustomer2() {
        Customer customer = mock(Customer.class);
        when(customer.getBonus()).thenReturn(1);
        assertFalse(this.customerList.addCustomer(customer));
        verify(customer).getBonus();
    }

    @Test
    void testAddCustomer3() {
        Customer customer = new Customer();
        customer.setBonus(0);
        assertFalse(this.customerList.addCustomer(customer));
    }

    @Test
    void testFindCustomerById() {
        assertNull((new CustomerList()).findCustomerById(123L));
    }

    @Test
    void testFindCustomerById2() {
        CustomerList customerList = new CustomerList();
        customerList.addCustomer(new Customer(123L, 1));
        assertNull(customerList.findCustomerById(123L));
    }

    @Test
    void testRemoveCustomer() {
        CustomerList customerList = new CustomerList();
        assertNull(customerList.removeCustomer(new Customer()));
    }

    @Test
    void testRemoveCustomer2() {
        Customer customer = new Customer();
        customer.setId(123L);

        CustomerList customerList = new CustomerList();
        customerList.addCustomer(customer);
        assertNull(customerList.removeCustomer(new Customer()));
    }

    @Test
    void testRemoveCustomer3() {
        CustomerList customerList = new CustomerList();
        customerList.addCustomer(new Customer(123L, 1));
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(123L);
        assertNull(customerList.removeCustomer(customer));
        verify(customer, atLeast(1)).getId();
    }

    @Test
    void testChangeBonus() {
        Customer customer = new Customer();
        assertTrue(this.customerList.changeBonus(customer, 1));
        assertEquals(1, customer.getBonus());
    }

    @Test
    void testChangeBonus2() {
        assertFalse(this.customerList.changeBonus(null, 1));
    }

    @Test
    void testChangeBonus3() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(123L);
        doNothing().when(customer).setBonus(anyInt());
        when(customer.getBonus()).thenReturn(1);
        assertTrue(this.customerList.changeBonus(customer, 1));
        verify(customer, atLeast(1)).getBonus();
        verify(customer).getId();
        verify(customer).setBonus(anyInt());
    }

    @Test
    void testChangeBonus4() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(123L);
        doNothing().when(customer).setBonus(anyInt());
        when(customer.getBonus()).thenReturn(Integer.MIN_VALUE);
        assertFalse(this.customerList.changeBonus(customer, 1));
        verify(customer).getBonus();
    }

    @Test
    void testInitializeBonus() {
        assertFalse((new CustomerList()).initializeBonus(123L));
    }

    @Test
    void testInitializeBonus2() {
        CustomerList customerList = new CustomerList();
        customerList.addCustomer(new Customer(123L, 1));
        assertFalse(customerList.initializeBonus(123L));
    }

    @Test
    void testIncrementBonus() {
        // assertFalse(this.customerList.increaseBonus(123L, 0));
    }
}

