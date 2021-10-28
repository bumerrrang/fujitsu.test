package andrei.lunin.fujitsu.test.service;

import andrei.lunin.fujitsu.test.constants.Constants;
import andrei.lunin.fujitsu.test.store.Video;
import andrei.lunin.fujitsu.test.store.VideoList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoListService {
    private static final Logger LOG = LoggerFactory
            .getLogger(VideoListService.class);

    VideoList videoList;

    @Autowired
    public VideoListService(VideoList videoList) {
        this.videoList = videoList;
    }

    public void createTestVideoListService() {
        LOG.info("The test VideoListService started");
        Video newVideo = null; // can't be 'null' -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(-10L, "Matrix 11", Constants.PREMIUM_PRICE, false); // wrong 'id' -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(0L, "Matrix 11", Constants.PREMIUM_PRICE, false); // wrong 'id' -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(null, "Matrix 11", Constants.PREMIUM_PRICE, false); // wrong 'id' -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(1L, null, Constants.PREMIUM_PRICE, false); // 'name' can not be null -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(1L, "", Constants.PREMIUM_PRICE, false); // 'name' can not be blank -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(10L, "Matrix 11", null, false); // 'type' can not be null -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(10L, "Matrix 11", Constants.PREMIUM_PRICE, true); // 'rented' must have 'false' as an initial value -> FALSE
        videoList.addVideo(newVideo);
        newVideo = new Video(10L, "Matrix 11", Constants.PREMIUM_PRICE, false); // all data is right -> TRUE
        videoList.addVideo(newVideo);
        videoList.addVideo(newVideo); // adding a duplicate -> FALSE
        videoList.printAllVideoList(); // "10 Matrix 11 PREMIUM_PRICE false"

        changeType(10L, null); // 'type' ca not be null -> FALSE
        changeType(10L, Constants.PREMIUM_PRICE); // video has the same type -> FALSE
        changeType(11L, Constants.PREMIUM_PRICE); // video does not exist in the list -> FALSE
        changeType(10L, Constants.BASIC_PRICE); // video was found and has different 'type' -> TRUE
        changeType(10L, Constants.PREMIUM_PRICE); // video was found and has different 'type' -> TRUE

        newVideo = new Video(12L, "Spider man", Constants.BASIC_PRICE, false);
        videoList.addVideo(newVideo); // all data right -> TRUE
        newVideo = new Video(13L, "Spider man 2", Constants.BASIC_PRICE, false);
        videoList.addVideo(newVideo); // all data right -> TRUE
        newVideo = new Video(13L, "Spider man 2", Constants.BASIC_PRICE, false);
        videoList.addVideo(newVideo); // trying to add duplicate -> FALSE
        newVideo = new Video(14L, "Spider man 3", Constants.BASIC_PRICE, false);
        videoList.addVideo(newVideo); // all data right -> TRUE
        removeVideo(null); // Video can not be 'null' -> FALSE
        newVideo.setId(null);
        removeVideo(newVideo); // Video 'id' can not be 'null' -> FALSE
        newVideo.setId(14L); // Video found and removed -> TRUE
        removeVideo(newVideo); // all data is correct, video was found and removed -> TRUE
        newVideo = new Video(14L, "Out of africa", Constants.OLD_FILM, false);
        videoList.addVideo(newVideo); // all data right -> TRUE
        LOG.info("\nVideo list created ->\n" + videoList.toString());
        videoList.printAllVideoList();
        videoList.printAllStoreList();
        LOG.info("The test VideoListService finished");
    }

    public boolean removeVideo(Video video) {
        if (video != null) {
            return videoList.removeVideo(video.getId());
        }
        LOG.warn("The being added video can not be 'null!'");
        return false;
    }

    public void changeType(Long id, Constants constant) {
        videoList.changeType(id, constant);
    }
}
