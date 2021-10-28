package andrei.lunin.fujitsu.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import andrei.lunin.fujitsu.test.constants.Constants;
import andrei.lunin.fujitsu.test.store.Video;
import andrei.lunin.fujitsu.test.store.VideoList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VideoListService.class, VideoList.class})
@ExtendWith(SpringExtension.class)
class VideoListServiceTest {
    @MockBean
    private VideoList videoList;

    @Autowired
    private VideoListService videoListService;

    @Test
    void testConstructor() {
        assertEquals("", (new VideoListService(new VideoList())).videoList.toString());
    }

    @Test
    void testCreateTestVideoListService() {
        doNothing().when(this.videoList).printAllStoreList();
        when(this.videoList.removeVideo((Long) any())).thenReturn(true);
        when(this.videoList.changeType((Long) any(), (andrei.lunin.fujitsu.test.constants.Constants) any()))
                .thenReturn(true);
        doNothing().when(this.videoList).printAllVideoList();
        when(this.videoList.addVideo((andrei.lunin.fujitsu.test.store.Video) any())).thenReturn(true);
        this.videoListService.createTestVideoListService();
        verify(this.videoList, atLeast(1)).addVideo((andrei.lunin.fujitsu.test.store.Video) any());
        verify(this.videoList, atLeast(1)).changeType((Long) any(), (andrei.lunin.fujitsu.test.constants.Constants) any());
        verify(this.videoList).printAllStoreList();
        verify(this.videoList, atLeast(1)).printAllVideoList();
        verify(this.videoList, atLeast(1)).removeVideo((Long) any());
    }

    @Test
    void testRemoveVideo() {
        when(this.videoList.removeVideo((Long) any())).thenReturn(true);
        assertTrue(this.videoListService.removeVideo(new Video()));
        verify(this.videoList).removeVideo((Long) any());
    }

    @Test
    void testRemoveVideo2() {
        when(this.videoList.removeVideo((Long) any())).thenReturn(false);
        assertFalse(this.videoListService.removeVideo(new Video()));
        verify(this.videoList).removeVideo((Long) any());
    }

    @Test
    void testRemoveVideo3() {
        when(this.videoList.removeVideo((Long) any())).thenReturn(true);
        assertFalse(this.videoListService.removeVideo(null));
    }

    @Test
    void testRemoveVideo4() {
        when(this.videoList.removeVideo((Long) any())).thenReturn(true);
        Video video = mock(Video.class);
        when(video.getId()).thenReturn(123L);
        assertTrue(this.videoListService.removeVideo(video));
        verify(this.videoList).removeVideo((Long) any());
        verify(video).getId();
    }

    @Test
    void testChangeType() {
        when(this.videoList.changeType((Long) any(), (Constants) any())).thenReturn(true);
        this.videoListService.changeType(123L, Constants.PREMIUM_PRICE);
        verify(this.videoList).changeType((Long) any(), (Constants) any());
    }
}

