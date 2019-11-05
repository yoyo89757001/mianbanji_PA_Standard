package megvii.testfacepass.pa.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class LunBoBean {

    @Id
    private Long id;
    private String path;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
