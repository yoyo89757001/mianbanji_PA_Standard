package megvii.testfacepass.pa.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

public class ToastUtils {

    public static void show (final Activity context, final String s){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast tastyToast = TastyToast.makeText(context, s, TastyToast.LENGTH_LONG, TastyToast.INFO);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();
            }
        });

    }

    public static void show2 (final Activity context, final String s){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast tastyToast = TastyToast.makeText(context, s, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();
            }
        });

    }

}
