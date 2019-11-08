package megvii.testfacepass.pa.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class HuiFuBean {

    /**
     * id : 12345
     * pepopleType : 0
     * type : -1
     * msg : 说明
     * shortId : 123456
     */
    @Id(assignable = true)
    private Long id;
    private String pepopleId;
    private String pepopleType;
    private String type;
    private String msg;
    private String shortId;
    private String serialnumber;


    public HuiFuBean(Long id, String pepopleId, String pepopleType, String type, String msg, String shortId, String serialnumber) {
        this.id = id;
        this.pepopleId = pepopleId;
        this.pepopleType = pepopleType;
        this.type = type;
        this.msg = msg;
        this.shortId = shortId;
        this.serialnumber = serialnumber;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPepopleType() {
        return pepopleType;
    }

    public void setPepopleType(String pepopleType) {
        this.pepopleType = pepopleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getPepopleId() {
        return pepopleId;
    }

    public void setPepopleId(String pepopleId) {
        this.pepopleId = pepopleId;
    }
}
