package megvii.testfacepass.pa.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


@Entity
public class XinXiIdBean {
    @Id
    private Long id;
    private Long ygid; //员工id
    private Long uuid;  //信息id

    public Long getYgid() {
        return ygid;
    }

    public void setYgid(Long ygid) {
        this.ygid = ygid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }
}
