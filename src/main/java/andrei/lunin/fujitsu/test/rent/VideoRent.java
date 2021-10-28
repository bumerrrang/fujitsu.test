package andrei.lunin.fujitsu.test.rent;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoRent {
    private Long id;
    private Long customerId;
    private Long videoId;
    private LocalDateTime rentStarted;
    private LocalDateTime rentFinished;
    private LocalDateTime videoReturned;
    private boolean bonusPaid;

    public VideoRent(Long id, Long customerId, Long videoId, LocalDateTime rentStarted, LocalDateTime rentFinished, boolean bonusPaid) {
        this.id = id;
        this.customerId = customerId;
        this.videoId = videoId;
        this.rentStarted = rentStarted;
        this.rentFinished = rentFinished;
        this.bonusPaid = bonusPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != VideoRent.class) {
            return false;
        }
        VideoRent videoRent = (VideoRent) o;
        return this.getId().equals(videoRent.getId());
    }

    @Override
    public String toString() {
        return "id:" + id + ", customerId:" + this.customerId +
                ", videoId:" + videoId + ", rentStarted:" + this.rentStarted +
                ", rentFinished:" + this.rentFinished + ", videoReturned:" + this.videoReturned +
                ", bonusPaid:" + this.bonusPaid;
    }
}
