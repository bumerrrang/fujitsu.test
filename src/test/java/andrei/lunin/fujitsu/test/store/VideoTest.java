package andrei.lunin.fujitsu.test.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class VideoTest {
    @Test
    void testEquals() {
        assertThrows(NullPointerException.class, () -> (new Video()).equals(null));
        assertFalse((new Video()).equals("Different type to Video"));
        assertThrows(NullPointerException.class, () -> (new Video()).equals(new Video()));
    }

    @Test
    void testEquals2() {
        Video video = new Video();
        video.setName("Name");
        video.setId(0L);
        assertFalse(video.equals(new Video()));
    }

    @Test
    void testEquals3() {
        Video video = new Video();
        video.setName("Name");
        video.setId(0L);

        Video video1 = new Video();
        video1.setName("Name");
        assertTrue(video.equals(video1));
        int notExpectedHashCodeResult = video.hashCode();
        assertFalse(Objects.equals(notExpectedHashCodeResult, video1.hashCode()));
    }

    @Test
    void testEquals4() {
        Video video = new Video();
        video.setId(123L);

        Video video1 = new Video();
        video1.setId(123L);
        assertTrue(video.equals(video1));
        int notExpectedHashCodeResult = video.hashCode();
        assertFalse(Objects.equals(notExpectedHashCodeResult, video1.hashCode()));
    }

    @Test
    void testConstructor() {
        assertEquals("id:null, name:null, type:null, rented:false", (new Video()).toString());
    }
}

