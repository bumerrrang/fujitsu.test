package andrei.lunin.fujitsu.test;

import andrei.lunin.fujitsu.test.service.CustomerListService;
import andrei.lunin.fujitsu.test.service.VideoListService;
import andrei.lunin.fujitsu.test.service.VideoRentListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory
            .getLogger(Application.class);

    CustomerListService customerListService;
    VideoListService videListService;
    VideoRentListService videoRentListService;

    @Autowired
    public Application(VideoListService videListService, CustomerListService customerListService,
                       VideoRentListService videoRentListService) {
        this.customerListService = customerListService;
        this.videListService = videListService;
        this.videoRentListService = videoRentListService;
    }

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        customerListService.createTestCustomerListService();
        videListService.createTestVideoListService();
        videoRentListService.createTestVideoRentListService();
    }
}
