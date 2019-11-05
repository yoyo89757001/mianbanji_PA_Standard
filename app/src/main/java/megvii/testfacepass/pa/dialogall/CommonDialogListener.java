package megvii.testfacepass.pa.dialogall;

/**
 * Created by 于德海 on 2016/9/8.
 *
 * @version ${VERSION}
 * @decpter
 */
public interface CommonDialogListener {
    void show(String a, String t, int p);
    void cancel();
    void setDate(String a, int p, String t);
}
