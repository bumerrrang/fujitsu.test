package andrei.lunin.fujitsu.test.service;

import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import andrei.lunin.fujitsu.test.rent.VideoRent;
import andrei.lunin.fujitsu.test.rent.VideoRentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoRentListService {
    private static final Logger LOG = LoggerFactory
            .getLogger(VideoRentListService.class);

    private final VideoRentList videoRentList;
    private final CustomerList customerList;

    @Autowired
    public VideoRentListService(VideoRentList videoRentList, CustomerList customerList) {
        this.videoRentList = videoRentList;
        this.customerList = customerList;
    }

    public void createTestVideoRentListService() {
        LOG.info("The test VideoRentListService started");
        VideoRent newVideoRent = new VideoRent(null, 1L, 10L, null, LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // 'id' can not be null -> FALSE
        newVideoRent = new VideoRent(1L, 1L, 10L, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // all the data is correct -> TRUE
        videoRentList.addVideoRent(newVideoRent); // trying to add duplicate -> FALSE
        videoRentList.returnVideo(null, LocalDateTime.now().plusDays(2L)); // VideoReturn id can not be 'null' -> FALSE
        videoRentList.returnVideo(1L, LocalDateTime.now().minusDays(2L)); // VideoReturn 'videoReturned' can not be the same or after 'rentStarted' -> FALSE
        videoRentList.returnVideo(1L, LocalDateTime.now().plusDays(3L)); // all data is correct -> TRUE
        videoRentList.addVideoRent(newVideoRent); // trying to save already existing data -> FALSE
        newVideoRent = new VideoRent(2L, null, 10L, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // 'customerId' can not be null -> FALSE
        newVideoRent = new VideoRent(2L, 1L, null, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // 'videoId' can not be null -> FALSE
        newVideoRent = new VideoRent(2L, 1L, 10L, null, LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // 'rentStarted' can not be null -> FALSE
        newVideoRent = new VideoRent(2L, 1L, 10L, LocalDateTime.now(), null, false);
        videoRentList.addVideoRent(newVideoRent); // 'rentFinished' can not be null -> FALSE
        newVideoRent = new VideoRent(2L, 1L, 10L, LocalDateTime.now(), LocalDateTime.now().minusDays(7L), false);
        videoRentList.addVideoRent(newVideoRent); // 'rentStarted' can not be after than 'rentFinished' -> FALSE
        newVideoRent = new VideoRent(2L, 1L, 10L, LocalDateTime.now(), LocalDateTime.now().minusDays(7L), true);
        videoRentList.addVideoRent(newVideoRent); // 'rentStarted' can not be after than 'rentFinished' -> FALSE
        newVideoRent = new VideoRent(2L, 1L, 10L, LocalDateTime.now(), LocalDateTime.now().plusDays(7L), true);
        videoRentList.addVideoRent(newVideoRent); // not enough 'bonus' for paying -> FALSE
        Customer customer = customerList.findCustomerById(1L);
        customerList.changeBonus(customer, 24);
        videoRentList.addVideoRent(newVideoRent); // not enough 'bonus' for paying -> FALSE
        customerList.changeBonus(customer, 1);
        videoRentList.addVideoRent(newVideoRent); // there is enough 'bonus' for paying -> FALSE
        newVideoRent.setRentFinished(newVideoRent.getRentStarted().plusDays(1L));
        videoRentList.addVideoRent(newVideoRent); // all data is correct -> TRUE
        videoRentList.returnVideo(2L, newVideoRent.getRentStarted().minusDays(5L));  // 'returnVideo' can not be earlier, than 'rentStarted' -> FALSE
        videoRentList.returnVideo(2L, newVideoRent.getRentStarted().plusDays(8L));  // 'returnVideo' is after 'rentStarted' -> TRUE

        newVideoRent = new VideoRent(3L, 2L, 12L, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), true);
        videoRentList.addVideoRent(newVideoRent); // can not rent not PREMIUM_PRICE video with using bonus points -> FALSE
        newVideoRent = new VideoRent(3L, 2L, 12L, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // all data is correct -> TRUE
        videoRentList.returnVideo(3L, newVideoRent.getRentStarted().plusDays(1L));  // 'returnVideo' is after 'rentStarted' -> TRUE

        newVideoRent = new VideoRent(4L, 2L, 100L, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // video with such videoId is missing -> FALSE
        newVideoRent = new VideoRent(4L, 1L, 14L, LocalDateTime.now(), LocalDateTime.now().plusDays(3L), false);
        videoRentList.addVideoRent(newVideoRent); // right data -> TRUE
        videoRentList.returnVideo(4L, newVideoRent.getRentStarted().plusDays(7L));  // 'returnVideo' is after 'rentStarted' -> TRUE

        videoRentList.printRentList();

        LOG.info("The test VideoRentListService finished");
    }
}
