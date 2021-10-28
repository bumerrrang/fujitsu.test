package andrei.lunin.fujitsu.test.store;

import andrei.lunin.fujitsu.test.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    Long id;
    private String name;
    private Constants type;
    private boolean rented;

    @Override
    public String toString() {
        return "id:" + this.id + ", name:" + this.name + ", type:" + this.type + ", rented:" + rented;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Video.class) {
            return false;
        }
        Video video = (Video) o;
        if (this.getId().equals(video.getId())) {
            return true;
        }
        return this.getName().equals(video.getName());
    }
}
