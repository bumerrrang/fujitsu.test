package andrei.lunin.fujitsu.test.rent;

import andrei.lunin.fujitsu.test.constants.Constants;
import andrei.lunin.fujitsu.test.customer.Customer;
import andrei.lunin.fujitsu.test.customer.CustomerList;
import andrei.lunin.fujitsu.test.store.Video;
import andrei.lunin.fujitsu.test.store.VideoList;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Component
public class VideoRentList {

    private static final Logger LOG = LoggerFactory
            .getLogger(VideoRentList.class);

    private final CustomerList customerList;
    private final VideoList videoList;

    @Autowired
    public VideoRentList(CustomerList customerList, VideoList videoList) {
        this.customerList = customerList;
        this.videoList = videoList;
    }

    private final List<VideoRent> videoRentList = new ArrayList<>();

    private boolean payingWithBonus(VideoRent videoRent) {
        if (videoRent == null) {
            return false;
        }
        return videoRent.isBonusPaid();
    }

    private boolean isPossibleToPayWithBonus(Customer customer, Duration duration) {
        return customer.getBonus() >= duration.toDays() * Constants.oneDayCost;
    }

    private boolean checkCustomerVideo(Customer customer, Video video) {
        return customer != null && video != null;
    }

    public boolean addVideoRent(VideoRent videoRent) {
        if (checkVideoRentFieldsValues(videoRent)) {
            Customer customer = customerList.findCustomerById(videoRent.getCustomerId());
            Video video = videoList.findRentableVideoById(videoRent.getVideoId());
            if (!checkCustomerVideo(customer, video)) {
                LOG.warn("For adding " + videoRent + " customer " + videoRent.getCustomerId() + " and video " + videoRent.getVideoId() + " must also exist.");
                return false;
            }
            if (payingWithBonus(videoRent)) {
                if (video.getType().equals(Constants.PREMIUM_PRICE)) {
                    Duration duration = Duration.between(videoRent.getRentStarted(), videoRent.getRentFinished());
                    if (isPossibleToPayWithBonus(customer, duration)) {
                        videoRentList.add(videoRent);
                        customerList.changeBonus(customer, (int) -duration.toDays() * Constants.oneDayCost);
                        videoList.toggleRented(video.getId());
                        LOG.warn("VideoRent " + videoRent + " was added to the videoRentList and been paid with extra points.");
                        return true;
                    }
                    LOG.warn("Impossible to add " + videoRent + " to VideoRent as Customer has not enough bonus points.");
                    return false;
                }
                LOG.warn("Impossible to pay for renting not " + Constants.PREMIUM_PRICE + " video with bonus points.");
                return false;
            }
            if (canAddVideoRent(customer, video)) {
                videoRentList.add(videoRent);
                customerList.changeBonus(customer, video.getType().bonus);
                videoList.toggleRented(video.getId());
                LOG.info("VideoRent " + videoRent + " was added to the videoRentList");
                return true;
            }
        }
        LOG.info("VideoRent " + videoRent + " was NOT added to the videoRentList");
        return false;
    }

    private boolean canAddVideoRent(Customer customer, Video video) {
        return customer != null && video != null;
    }

    private boolean checkVideoRentFieldsValues(VideoRent videoRent) {
        if (videoRentList.contains(videoRent)) {
            return false;
        }
        Long id = videoRent.getId();
        if (id == null) {
            return false;
        }
        Long customerId = videoRent.getCustomerId();
        if (customerId == null) {
            return false;
        }
        Long videoId = videoRent.getVideoId();
        if (videoId == null) {
            return false;
        }
        LocalDateTime rentStarted = videoRent.getRentStarted();
        if (rentStarted == null) {
            return false;
        }
        LocalDateTime rentFinished = videoRent.getRentFinished();
        if (rentFinished == null) {
            return false;
        }
        return !rentStarted.isAfter(rentFinished);
    }

    private VideoRent getVideoRentById(Long id) {
        Optional<VideoRent> videoRent = videoRentList.stream().filter(v -> v.getId().equals(id)).findAny();
        return videoRent.orElse(null);
    }

    private boolean isPossibleReturn(VideoRent videoRent) {
        if (videoRent == null) {
            LOG.warn("Video rent " + videoRent + " for return can not be 'null'");
            return false;
        }
        return videoRent.getVideoReturned() == null;
    }

    public boolean returnVideo(Long id, LocalDateTime returnDateTime) {
        if (id == null) {
            return false;
        }
        VideoRent videoRent = getVideoRentById(id);
        if (isPossibleReturn(videoRent)) {
            if (returnDateTime.isAfter(videoRent.getRentStarted())) {
                Video video = videoList.findVideoById(videoRent.getVideoId());
                video.setRented(false);
                videoRent.setVideoReturned(returnDateTime);
                LOG.info("Video " + video + " was successfully returned and returning registered");
                return true;
            }
            LOG.warn("Video rent " + videoRent + " return can not be registered as its returnDateTime CAN NOT BE EQUAL OR BEFORE its rentStarted");
            return false;
        }
        return false;
    }

    public boolean printRentList() {
        if (videoRentList.size() == 0) {
            System.out.println("\nThe list of rent is empty.\n");
            return false;
        }
        System.out.println("\nThe video rentList list:");
        printRentals();
        return true;
    }

    private long getDaysBetween(VideoRent videoRent) {
        return Duration.between(videoRent.getRentStarted(),
                (videoRent.getRentFinished().isAfter(videoRent.getVideoReturned()) ?
                        videoRent.getRentFinished() : videoRent.getVideoReturned())).toDays();
    }

    private boolean printRentals() {
        StringBuilder resultStringBuilder = new StringBuilder();
        if (videoRentList.size() == 0) {
            System.out.println("The rental list is empty.");
            return false;
        }
        List<VideoRent> bufferVideoRentList = videoRentList.stream().
                filter(v -> !v.isBonusPaid()).
                filter(v -> v.getVideoReturned().isBefore(v.getRentFinished()) ||
                        (v.getVideoReturned().isAfter(v.getRentFinished()) &&
                                Duration.between(v.getRentFinished(), v.getVideoReturned()).toDays() < 1)
                ).
                collect(Collectors.toList());
        resultStringBuilder.append(appendResult(bufferVideoRentList, "inTime"));
        bufferVideoRentList = videoRentList.stream().
                filter(v -> !v.isBonusPaid()).
                filter(v -> v.getVideoReturned().isAfter(v.getRentFinished()) &&
                        Duration.between(v.getRentFinished(), v.getVideoReturned()).toDays() > 1).
                collect(Collectors.toList());
        resultStringBuilder.append(appendResult(bufferVideoRentList, "overTime"));
        bufferVideoRentList = videoRentList.stream().
                filter(v -> v.isBonusPaid()).collect(Collectors.toList());
        resultStringBuilder.append(appendResult(bufferVideoRentList, "bonusPayment"));
        System.out.println("Printing the results -> \n" + resultStringBuilder);
        return true;
    }

    private StringBuilder appendResult(List<VideoRent> videoRentList, String rentType) {
        StringBuilder resultStringBuilder = new StringBuilder();
        switch (rentType) {
            case ("inTime"):
                long totalPrice = 0;
                for (VideoRent videoRent : videoRentList) {
                    Customer customer = customerList.findCustomerById(videoRent.getCustomerId());
                    Video video = videoList.findVideoById(videoRent.getVideoId());
                    long daysBetween = getDaysBetween(videoRent);
                    long videoRentPrice = countPrice(daysBetween, videoRent);
                    resultStringBuilder.append(video.getName()).append(" (").
                            append(video.getType().videoType).append(") ").
                            append(" ").append(daysBetween).append(" days ").
                            append(videoRentPrice).append(" EUR").append("\n");
                    totalPrice += videoRentPrice;
                }
                resultStringBuilder.append("Total price: " + totalPrice + "\n");
                return resultStringBuilder;
            case ("overTime"):
                totalPrice = 0;
                for (VideoRent videoRent : videoRentList) {
                    Customer customer = customerList.findCustomerById(videoRent.getCustomerId());
                    Video video = videoList.findVideoById(videoRent.getVideoId());
                    long daysBetween = getDaysBetween(videoRent);
                    long videoRentPrice = countPrice(daysBetween, videoRent);
                    resultStringBuilder.append(video.getName()).append(" (").
                            append(video.getType().videoType).append(") ").
                            append(" ").append(daysBetween).append(" days ").
                            append(videoRentPrice).append(" EUR").append("\n");
                    totalPrice += videoRentPrice;
                }
                resultStringBuilder.append("Total price: " + totalPrice + "\n");
                return resultStringBuilder;
            case ("bonusPayment"):
                totalPrice = 0;
                for (VideoRent videoRent : videoRentList) {
                    Customer customer = customerList.findCustomerById(videoRent.getCustomerId());
                    Video video = videoList.findVideoById(videoRent.getVideoId());
                    long daysBetween = getDaysBetween(videoRent);
                    long videoRentPrice = countPrice(daysBetween, videoRent);
                    resultStringBuilder.append(video.getName()).append(" (").
                            append(video.getType().videoType).append(") ").
                            append(" ").append(daysBetween).append(" days ").
                            append("(Paid with " + Constants.oneDayCost * daysBetween + " Bonus points.)\n").
                            append("Remaining Bonus points: " + customer.getBonus() + "\n");
                    totalPrice += videoRentPrice;
                }
        }
        resultStringBuilder.append("Total price: 0 EUR\n");
        return resultStringBuilder;
    }


    private long countPrice(long days, VideoRent videoRent) {
        Video video = videoList.findVideoById(videoRent.getVideoId());
        Constants constant = video.getType();
        long finalPrice = 0;
        if (constant.equals(Constants.PREMIUM_PRICE)) {
            finalPrice = days * Constants.PREMIUM_PRICE.price;
        }
        if (constant.equals(Constants.BASIC_PRICE)) {
            if (days <= 3) {
                finalPrice = Constants.BASIC_PRICE.price;
            } else {
                finalPrice = Constants.BASIC_PRICE.price + (days - 3) * Constants.BASIC_PRICE.price;
            }
        }
        if (constant.equals(Constants.OLD_FILM)) {
            if (days <= 5) {
                finalPrice = Constants.BASIC_PRICE.price;
            } else {
                finalPrice = Constants.BASIC_PRICE.price + (days - 5) * Constants.BASIC_PRICE.price;
            }
        }
        return finalPrice;
    }
}
