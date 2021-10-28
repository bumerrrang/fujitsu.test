package andrei.lunin.fujitsu.test.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import andrei.lunin.fujitsu.test.rent.VideoRentList;
import andrei.lunin.fujitsu.test.store.VideoList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VideoRentListService.class, CustomerList.class, VideoRentList.class})
@ExtendWith(SpringExtension.class)
class VideoRentListServiceTest {
    @MockBean
    private CustomerList customerList;

    @MockBean
    private VideoRentList videoRentList;

    @Autowired
    private VideoRentListService videoRentListService;

    @Test
    void testConstructor() {
        // TODO: This test is incomplete.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     VideoRentListService.videoRentList
        //     VideoRentListService.customerList

        CustomerList customerList = new CustomerList();
        VideoRentList videoRentList = new VideoRentList(customerList, new VideoList());

        new VideoRentListService(videoRentList, new CustomerList());

    }

    @Test
    void testCreateTestVideoRentListService() {
        // TODO: This test is incomplete.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     VideoRentListService.customerList
        //     VideoRentListService.videoRentList

        when(this.videoRentList.printRentList()).thenReturn(true);
        when(this.videoRentList.returnVideo((Long) any(), (java.time.LocalDateTime) any())).thenReturn(true);
        when(this.videoRentList.addVideoRent((andrei.lunin.fujitsu.test.rent.VideoRent) any())).thenReturn(true);
        when(this.customerList.changeBonus((Customer) any(), anyInt())).thenReturn(true);
        when(this.customerList.findCustomerById((Long) any())).thenReturn(new Customer());
        this.videoRentListService.createTestVideoRentListService();
    }
}

