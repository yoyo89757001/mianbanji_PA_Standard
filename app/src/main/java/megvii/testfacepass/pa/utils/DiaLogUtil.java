package megvii.testfacepass.pa.utils;

import android.app.Dialog;
import android.view.Gravity;
import android.view.WindowManager;

public class DiaLogUtil {

    public static void init(Dialog dialog){
        WindowManager.LayoutParams params= dialog.getWindow().getAttributes();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setAttributes(params);
    }

}
