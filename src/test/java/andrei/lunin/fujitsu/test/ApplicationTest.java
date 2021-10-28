package andrei.lunin.fujitsu.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import andrei.lunin.fujitsu.test.rent.VideoRentList;
import andrei.lunin.fujitsu.test.service.CustomerListService;
import andrei.lunin.fujitsu.test.service.VideoListService;
import andrei.lunin.fujitsu.test.service.VideoRentListService;
import andrei.lunin.fujitsu.test.store.VideoList;

import java.util.List;

import org.junit.jupiter.api.Test;

class ApplicationTest {
    @Test
    void testConstructor() {
        VideoListService videListService = new VideoListService(new VideoList());
        CustomerList customerList = new CustomerList();
        CustomerListService customerListService = new CustomerListService(customerList);
        CustomerList customerList1 = new CustomerList();
        VideoRentList videoRentList = new VideoRentList(customerList1, new VideoList());

        assertSame(customerList, (new Application(videListService, customerListService,
                new VideoRentListService(videoRentList, new CustomerList()))).customerListService.getCustomerList());
    }

    @Test
    void testRun() {
        VideoListService videListService = new VideoListService(new VideoList());
        CustomerListService customerListService = new CustomerListService(new CustomerList());
        CustomerList customerList = new CustomerList();
        VideoRentList videoRentList = new VideoRentList(customerList, new VideoList());

        Application application = new Application(videListService, customerListService,
                new VideoRentListService(videoRentList, new CustomerList()));
        application.run("Args");
        List<Customer> customerList1 = application.customerListService.getCustomerList().getCustomerList();
        assertEquals(3, customerList1.size());
        Customer getResult = customerList1.get(0);
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(0, getResult.getBonus());
        Customer getResult1 = customerList1.get(1);
        assertEquals(2L, getResult1.getId().longValue());
        assertEquals(0, getResult1.getBonus());
        Customer getResult2 = customerList1.get(2);
        assertEquals(3L, getResult2.getId().longValue());
        assertEquals(0, getResult2.getBonus());
    }
}

