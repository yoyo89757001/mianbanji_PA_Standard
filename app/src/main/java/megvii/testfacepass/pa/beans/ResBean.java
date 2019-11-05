package megvii.testfacepass.pa.beans;

public class ResBean {
    private int code;
    private String message;
    private String serialnumber;


    public ResBean(int code, String message, String serialnumber) {
        this.code = code;
        this.message = message;
        this.serialnumber = serialnumber;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
