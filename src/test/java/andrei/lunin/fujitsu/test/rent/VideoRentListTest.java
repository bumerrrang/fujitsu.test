package andrei.lunin.fujitsu.test.rent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import andrei.lunin.fujitsu.test.constants.Constants;
import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import andrei.lunin.fujitsu.test.store.Video;
import andrei.lunin.fujitsu.test.store.VideoList;

import java.time.LocalDateTime;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VideoRentList.class, CustomerList.class, VideoList.class})
@ExtendWith(SpringExtension.class)
class VideoRentListTest {
    @MockBean
    private CustomerList customerList;

    @MockBean
    private VideoList videoList;

    @Autowired
    private VideoRentList videoRentList;

    @Test
    void testConstructor() {
        CustomerList customerList = new CustomerList();
        VideoList videoList = new VideoList();
        VideoRentList actualVideoRentList = new VideoRentList(customerList, videoList);

        CustomerList customerList1 = actualVideoRentList.getCustomerList();
        assertSame(customerList, customerList1);
        List<VideoRent> videoRentList = actualVideoRentList.getVideoRentList();
        assertTrue(videoRentList.isEmpty());
        VideoList videoList1 = actualVideoRentList.getVideoList();
        assertSame(videoList, videoList1);
        List<Customer> customerList2 = customerList1.getCustomerList();
        assertEquals(videoRentList, customerList2);
        assertTrue(customerList2.isEmpty());
        assertEquals("", videoList1.toString());
        List<Video> videoList2 = videoList1.getVideoList();
        assertEquals(videoRentList, videoList2);
        assertEquals(customerList2, videoList2);
        assertTrue(videoList2.isEmpty());
        assertEquals("", customerList1.toString());
        assertSame(customerList1, customerList);
        assertSame(videoList1, videoList);
    }

    @Test
    void testAddVideoRent() {
        when(this.videoList.findRentableVideoById((Long) any())).thenReturn(null);
        when(this.customerList.findCustomerById((Long) any())).thenReturn(new Customer());
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertFalse(this.videoRentList
                .addVideoRent(new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true)));
    }

    @Test
    void testAddVideoRent2() {
        when(this.videoList.findRentableVideoById((Long) any()))
                .thenReturn(new Video(123L, "Name", Constants.PREMIUM_PRICE, true));
        when(this.customerList.changeBonus((Customer) any(), anyInt())).thenReturn(true);
        when(this.customerList.findCustomerById((Long) any())).thenReturn(new Customer());
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertFalse(this.videoRentList
                .addVideoRent(new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true)));
    }

    @Test
    void testAddVideoRent3() {
        when(this.videoList.toggleRented((Long) any())).thenReturn(true);
        when(this.videoList.findRentableVideoById((Long) any()))
                .thenReturn(new Video(123L, "Name", Constants.PREMIUM_PRICE, true));
        when(this.customerList.changeBonus((Customer) any(), anyInt())).thenReturn(true);
        when(this.customerList.findCustomerById((Long) any())).thenReturn(new Customer());
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertFalse(this.videoRentList
                .addVideoRent(new VideoRent(0L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true)));
    }

    @Test
    void testAddVideoRent4() {
        when(this.videoList.toggleRented((Long) any())).thenReturn(true);
        when(this.videoList.findRentableVideoById((Long) any()))
                .thenReturn(new Video(123L, "Name", Constants.PREMIUM_PRICE, true));
        when(this.customerList.changeBonus((Customer) any(), anyInt())).thenReturn(true);
        when(this.customerList.findCustomerById((Long) any())).thenReturn(new Customer());
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertFalse(this.videoRentList
                .addVideoRent(new VideoRent(1L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true)));
    }

    @Test
    void testReturnVideo() {
        assertFalse(this.videoRentList.returnVideo(123L, LocalDateTime.of(1, 1, 1, 1, 1)));
        assertFalse(this.videoRentList.returnVideo(0L, LocalDateTime.of(1, 1, 1, 1, 1)));
        assertFalse(this.videoRentList.returnVideo(5L, LocalDateTime.of(1, 1, 1, 1, 1)));
        assertFalse(this.videoRentList.returnVideo(null, LocalDateTime.of(1, 1, 1, 1, 1)));
    }

    @Test
    void testReturnVideo2() {
        when(this.videoList.findVideoById((Long) any())).thenReturn(new Video());
        assertFalse(this.videoRentList.returnVideo(123L, LocalDateTime.of(5, 1, 1, 1, 1)));
    }

    @Test
    void testReturnVideo3() {
        when(this.videoList.findVideoById((Long) any())).thenReturn(null);
        assertFalse(this.videoRentList.returnVideo(123L, LocalDateTime.of(5, 1, 1, 1, 1)));
    }

    @Test
    void testPrintRentList() {
        CustomerList customerList = new CustomerList();
        assertFalse((new VideoRentList(customerList, new VideoList())).printRentList());
    }
}

