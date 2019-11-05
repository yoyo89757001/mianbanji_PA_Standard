//package megvii.testfacepass.tps401test.led;
//
//import com.common.pos.api.util.ShellUtils;
//import com.example.tps401test.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//
//public class LightnessActivity extends Activity {
//
//	Button btn_commit_1, btn_commit_50, btn_commit_100, btn_commit_150, btn_commit_200, btn_commit_255;
//	EditText et_inputLevel;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.lightness_activity);
//		btn_commit_1 = (Button) findViewById(R.id.btn_lightness_0);
//		btn_commit_50 = (Button) findViewById(R.id.btn_lightness_50);
//		btn_commit_100 = (Button) findViewById(R.id.btn_lightness_100);
//		btn_commit_150 = (Button) findViewById(R.id.btn_lightness_150);
//		btn_commit_200 = (Button) findViewById(R.id.btn_lightness_200);
//		btn_commit_255 = (Button) findViewById(R.id.btn_lightness_255);
//		et_inputLevel = (EditText) findViewById(R.id.et_lightness);
//		btn_commit_1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ShellUtils.execCommand("echo " + 0 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
//			}
//
//		});
//		btn_commit_50.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ShellUtils.execCommand("echo " + 50 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
//			}
//
//		});
//		btn_commit_255.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ShellUtils.execCommand("echo " + 255 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
//			}
//
//		});
//		btn_commit_100.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ShellUtils.execCommand("echo " + 100 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
//			}
//
//		});
//		btn_commit_150.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ShellUtils.execCommand("echo " + 150 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
//			}
//
//		});
//		btn_commit_200.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ShellUtils.execCommand("echo " + 200 + " >/sys/class/backlight/rk28_bl_sub/brightness", true);// usb
//			}
//
//		});
//	}
//
//}
