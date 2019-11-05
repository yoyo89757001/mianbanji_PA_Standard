//package megvii.testfacepass.tps401test.screen;
//
//import com.common.pos.api.util.TPS980PosUtil;
//import com.example.tps401test.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class GetScreenCountActivity extends Activity {
//	TextView tv_count;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_screen);
//		tv_count = (TextView) findViewById(R.id.show_scrrenCount);
//
//		int ret = -1;
//		ret = TPS980PosUtil.getScreenCount();
//		Toast.makeText(GetScreenCountActivity.this, "ret---------->>>>         " + ret, Toast.LENGTH_LONG).show();
//		if (ret == 111) {
//			// 返回111时，一个屏幕
//			tv_count.setText("设备有   " + 1 + "  个屏幕");
//		} else if (ret == -1) {
//			// 读取不到时，一个屏幕
//			tv_count.setText("设备有   " + 1 + "  个屏幕");
//		} else if (ret == 112) {
//			// 返回112时，两个屏幕
//			tv_count.setText("设备有   " + 2 + "  个屏幕");
//		} else {
//			// 返回其他时，一个屏幕
//			tv_count.setText("设备有   " + 1 + "  个屏幕");
//		}
//	}
//}
