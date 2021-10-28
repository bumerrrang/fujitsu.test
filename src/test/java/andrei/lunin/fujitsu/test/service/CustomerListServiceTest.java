package andrei.lunin.fujitsu.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerListService.class, CustomerList.class})
@ExtendWith(SpringExtension.class)
class CustomerListServiceTest {
    @MockBean
    private CustomerList customerList;

    @Autowired
    private CustomerListService customerListService;

    @Test
    void testConstructor() {
        CustomerList customerList = new CustomerList();
        CustomerListService actualCustomerListService = new CustomerListService(customerList);
        CustomerList expectedCustomerList = actualCustomerListService.customerList;
        CustomerList customerList1 = actualCustomerListService.getCustomerList();
        assertSame(expectedCustomerList, customerList1);
        assertTrue(customerList1.getCustomerList().isEmpty());
        assertEquals("", customerList1.toString());
        assertSame(customerList1, customerList);
    }

    @Test
    void testCreateTestCustomerListService() {
        when(this.customerList.initializeBonus((Long) any())).thenReturn(true);
        when(this.customerList.changeBonus((Customer) any(), anyInt())).thenReturn(true);
        when(this.customerList.removeCustomer((Customer) any())).thenReturn(new Customer());
        when(this.customerList.findCustomerById((Long) any())).thenReturn(new Customer());
        when(this.customerList.addCustomer((Customer) any())).thenReturn(true);
        this.customerListService.createTestCustomerListService();
        verify(this.customerList, atLeast(1)).addCustomer((Customer) any());
        verify(this.customerList, atLeast(1)).changeBonus((Customer) any(), anyInt());
        verify(this.customerList, atLeast(1)).findCustomerById((Long) any());
        verify(this.customerList, atLeast(1)).initializeBonus((Long) any());
        verify(this.customerList, atLeast(1)).removeCustomer((Customer) any());
    }
}

