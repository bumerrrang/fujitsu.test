package andrei.lunin.fujitsu.test.store;

import andrei.lunin.fujitsu.test.constants.Constants;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Component
public class VideoList {
    private static final Logger LOG = LoggerFactory
            .getLogger(VideoList.class);

    private final List<Video> videoList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Video video : videoList) {
            result.append(video.getId()).append(" ").append(video.getName()).append(" ")
                    .append(video.getType()).append(" ").append(video.isRented()).append("\n");
        }
        return result.toString();
    }

    public boolean addVideo(Video video) {
        if (video == null) {
            LOG.warn("The being added new Video " + video + " can not be 'null' by itself.");
            return false;
        }
        if (video.getId() == null) {
            LOG.warn("The being added new Video " + video + " can not have 'null' as it's id.");
            return false;
        }
        if (video.getId() <= 0) {
            LOG.warn("The being added new Video " + video + " can not have negative id.");
            return false;
        }
        if (video.getName() == null || video.getName().equals("")) {
            LOG.warn("The being added new Video " + video + " can not have blank name.");
            return false;
        }
        if (video.getType() == null) {
            LOG.warn("The being added new Video " + video + " can not have null type.");
            return false;
        }
        if (video.isRented()) {
            LOG.warn("The being added new Video " + video + " can not have rented=true status the same time.");
            return false;
        }
        if (videoList.contains(video)) {
            LOG.warn("The being added new Video " + video + " is already in the videoList");
            return false;
        }
        LOG.info("The new Video " + video + " was added to the videoList");
        return videoList.add(video);
    }

    public Video findVideoById(Long id) {
        Optional<Video> video = videoList.stream().filter(v -> v.getId().equals(id)).findAny();
        if (video.isPresent()) {
            LOG.info("Video with id " + id + " was found in the videoList and been returned");
            return video.get();
        } else {
            LOG.info("Video with id " + id + " DOES NOT EXIST in the list");
            return null;
        }
    }

    public Video findRentableVideoById(Long id) {
        Optional<Video> video = videoList.stream().filter(v -> v.getId().equals(id)).filter(v -> !v.isRented()).findAny();
        if (video.isPresent()) {
            LOG.info("Video with id " + id + " is rentable, was found in the videoList and been returned");
            return video.get();
        } else {
            LOG.info("Video with id " + id + " is not rentable or/and DOES NOT EXIST in the list");
            return null;
        }
    }

    public boolean removeVideo(Long id) {
        if (id == null) {
            LOG.warn("Video 'id' can not be 'null'!");
            return false;
        }
        Video video = findVideoById(id);
        if (video != null) {
            videoList.remove(video);
            LOG.info("Video " + video + " was removed from the videoList");
            return true;
        }
        LOG.info("Video " + video + " DOES NOT EXIST in the videoList");
        return false;
    }

    public boolean changeType(Long id, Constants constant) {
        if (constant == null) {
            LOG.warn("The type for being updated can not be null!");
            return false;
        }
        Video video = findVideoById(id);
        if (video != null) {
            if (video.getType() != constant) {
                video.setType(constant);
                LOG.info("Video with id " + id + " type was changed to " + constant);
                return true;
            } else {
                LOG.warn("Video with id " + id + " has the same type in the list");
                return false;
            }
        }
        LOG.warn("Video with id " + id + " DOES NOT EXIST in the list");
        return false;
    }

    public boolean toggleRented(Long videoId) {
        Video video = findVideoById(videoId);
        if (video != null) {
            video.setRented(!video.isRented());
            LOG.info("Video " + video + " rented value changed to '" + video.isRented() + "'");
            return true;
        }
        LOG.warn("Video " + video + " was not fount in the store");
        return false;
    }

    public void printAllVideoList() {
        System.out.println("\nPrinting all video list ->");
        Stream<Video> videoStream = videoList.stream();
        videoStream.forEach(System.out::println);
    }

    public void printAllStoreList() {
        System.out.println("\nPrinting all video list at store ->");
        Stream<Video> videoStream = videoList.stream().filter(v -> !v.isRented());
        videoStream.forEach(System.out::println);
    }
}
