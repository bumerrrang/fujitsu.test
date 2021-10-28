package andrei.lunin.fujitsu.test.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import andrei.lunin.fujitsu.test.constants.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VideoList.class})
@ExtendWith(SpringExtension.class)
class VideoListTest {
    @Autowired
    private VideoList videoList;

    @Test
    void testToString() {
        assertEquals(
                "123 42 PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name BASIC_PRICE false\n"
                        + "123 Name OLD_FILM false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                        + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n",
                this.videoList.toString());
    }

    @Test
    void testAddVideo() {
        assertFalse(this.videoList.addVideo(new Video()));
        assertFalse(this.videoList.addVideo(null));
        assertFalse(this.videoList.addVideo(new Video(123L, "Name", Constants.PREMIUM_PRICE, true)));
    }

    @Test
    void testAddVideo2() {
        Video video = mock(Video.class);
        when(video.isRented()).thenReturn(false);
        when(video.getType()).thenReturn(Constants.PREMIUM_PRICE);
        when(video.getName()).thenReturn("Name");
        when(video.getId()).thenReturn(123L);
        assertTrue(this.videoList.addVideo(video));
        verify(video, atLeast(1)).getId();
        verify(video, atLeast(1)).getName();
        verify(video).getType();
        verify(video).isRented();
    }

    @Test
    void testFindVideoById() {
        verify(this.videoList.findVideoById(123L)).getId();
        assertEquals("123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name BASIC_PRICE false\n"
                + "123 Name OLD_FILM false\n" + "123 foo PREMIUM_PRICE false\n"
                + "123 andrei.lunin.fujitsu.test.store.Video PREMIUM_PRICE false\n" + "123 42 PREMIUM_PRICE false\n"
                + "4 Name PREMIUM_PRICE false\n" + "1 Name PREMIUM_PRICE false\n"
                + "9223372036854775807 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name BASIC_PRICE false\n" + "123 Name OLD_FILM false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n", this.videoList.toString());
    }

    @Test
    void testFindVideoById2() {
        assertNull(this.videoList.findVideoById(0L));
        assertEquals("123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name BASIC_PRICE false\n"
                + "123 Name OLD_FILM false\n" + "123 foo PREMIUM_PRICE false\n"
                + "123 andrei.lunin.fujitsu.test.store.Video PREMIUM_PRICE false\n" + "123 42 PREMIUM_PRICE false\n"
                + "4 Name PREMIUM_PRICE false\n" + "1 Name PREMIUM_PRICE false\n"
                + "9223372036854775807 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name BASIC_PRICE false\n" + "123 Name OLD_FILM false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n", this.videoList.toString());
    }

    @Test
    void testFindRentableVideoById() {
        Video actualFindRentableVideoByIdResult = this.videoList.findRentableVideoById(123L);
        verify(actualFindRentableVideoByIdResult).getId();
        verify(actualFindRentableVideoByIdResult).isRented();
        assertEquals("123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name BASIC_PRICE false\n"
                + "123 Name OLD_FILM false\n" + "123 foo PREMIUM_PRICE false\n"
                + "123 andrei.lunin.fujitsu.test.store.Video PREMIUM_PRICE false\n" + "123 42 PREMIUM_PRICE false\n"
                + "4 Name PREMIUM_PRICE false\n" + "1 Name PREMIUM_PRICE false\n"
                + "9223372036854775807 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name BASIC_PRICE false\n" + "123 Name OLD_FILM false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n", this.videoList.toString());
    }

    @Test
    void testFindRentableVideoById2() {
        assertNull(this.videoList.findRentableVideoById(0L));
        assertEquals("123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name BASIC_PRICE false\n"
                + "123 Name OLD_FILM false\n" + "123 foo PREMIUM_PRICE false\n"
                + "123 andrei.lunin.fujitsu.test.store.Video PREMIUM_PRICE false\n" + "123 42 PREMIUM_PRICE false\n"
                + "4 Name PREMIUM_PRICE false\n" + "1 Name PREMIUM_PRICE false\n"
                + "9223372036854775807 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name BASIC_PRICE false\n" + "123 Name OLD_FILM false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n", this.videoList.toString());
    }

    @Test
    void testRemoveVideo() {
        assertFalse(this.videoList.removeVideo(null));
    }

    @Test
    void testChangeType() {
        assertFalse((new VideoList()).changeType(123L, Constants.PREMIUM_PRICE));
        assertFalse((new VideoList()).changeType(123L, null));
    }

    @Test
    void testToggleRented() {
        assertFalse((new VideoList()).toggleRented(123L));
    }

    @Test
    void testPrintAllVideoList() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by printAllVideoList()
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        this.videoList.printAllVideoList();
    }

    @Test
    void testPrintAllStoreList() {
        this.videoList.printAllStoreList();
        assertEquals("123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name BASIC_PRICE false\n"
                + "123 Name OLD_FILM false\n" + "123 foo PREMIUM_PRICE false\n"
                + "123 andrei.lunin.fujitsu.test.store.Video PREMIUM_PRICE false\n" + "123 42 PREMIUM_PRICE false\n"
                + "4 Name PREMIUM_PRICE false\n" + "1 Name PREMIUM_PRICE false\n"
                + "9223372036854775807 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name BASIC_PRICE false\n" + "123 Name OLD_FILM false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n" + "123 Name PREMIUM_PRICE false\n"
                + "123 Name PREMIUM_PRICE false\n", this.videoList.toString());
    }
}

