package andrei.lunin.fujitsu.test.rent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class VideoRentTest {
    @Test
    void testConstructor() {
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertEquals(
                "id:123, customerId:123, videoId:123, rentStarted:0001-01-01T01:01, rentFinished:0001-01-01T01:01,"
                        + " videoReturned:null, bonusPaid:true",
                (new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true)).toString());
    }

    @Test
    void testEquals() {
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertThrows(NullPointerException.class,
                () -> (new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true)).equals(null));
    }

    @Test
    void testEquals2() {
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        assertFalse((new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true))
                .equals("Different type to VideoRent"));
    }

    @Test
    void testEquals3() {
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        VideoRent videoRent = new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true);
        assertTrue(videoRent.equals(videoRent));
        int expectedHashCodeResult = videoRent.hashCode();
        assertEquals(expectedHashCodeResult, videoRent.hashCode());
    }

    @Test
    void testEquals4() {
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        VideoRent videoRent = new VideoRent(123L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true);
        LocalDateTime rentStarted1 = LocalDateTime.of(1, 1, 1, 1, 1);
        VideoRent videoRent1 = new VideoRent(123L, 123L, 123L, rentStarted1, LocalDateTime.of(1, 1, 1, 1, 1), true);

        assertTrue(videoRent.equals(videoRent1));
        int notExpectedHashCodeResult = videoRent.hashCode();
        assertFalse(Objects.equals(notExpectedHashCodeResult, videoRent1.hashCode()));
    }

    @Test
    void testEquals5() {
        LocalDateTime rentStarted = LocalDateTime.of(1, 1, 1, 1, 1);
        VideoRent videoRent = new VideoRent(0L, 123L, 123L, rentStarted, LocalDateTime.of(1, 1, 1, 1, 1), true);
        LocalDateTime rentStarted1 = LocalDateTime.of(1, 1, 1, 1, 1);
        assertFalse(videoRent.equals(new VideoRent(123L, 123L, 123L, rentStarted1, LocalDateTime.of(1, 1, 1, 1, 1), true)));
    }
}

