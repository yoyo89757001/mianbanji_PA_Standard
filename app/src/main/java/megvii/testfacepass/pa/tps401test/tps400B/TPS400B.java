//package megvii.testfacepass.tps401test.tps400B;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.common.pos.api.util.TPS980PosUtil;
//import com.example.tps401test.R;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.TextView;
//
//public class TPS400B extends Activity {
//
//	private TextView show_proximity_sensor;
//	private Timer mTimer;
//	private TimerTask mTimerTask;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_proximity_sensor);
//		initUI();
//	}
//
//	private void initUI() {
//		show_proximity_sensor = (TextView) findViewById(R.id.show_proximity_sensor);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		if (mTimerTask == null) {
//			mTimerTask = new TimerTask() {
//
//				@Override
//				public void run() {
//					StringBuffer buffer = new StringBuffer();
//					for (int i = 0; i < 4; i++) {
//						int ret = TPS980PosUtil.getPriximitySensorStatus_400B(i);
//						buffer.append(i + "的状态值为：" + ret + "\n");
//					}
//					Message mMessage = new Message();
//					mMessage.what = 0;
//					mMessage.obj = buffer.toString();
//					mHandler.sendMessage(mMessage);
//				}
//			};
//		}
//		if (mTimer == null) {
//			mTimer = new Timer();
//		}
//		mTimer.schedule(mTimerTask, 0, 1000);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		if (mTimer != null) {
//			mTimer.cancel();
//			mTimer = null;
//		}
//		if (mTimerTask != null) {
//			mTimerTask.cancel();
//			mTimerTask = null;
//		}
//	}
//
//	@SuppressLint("HandlerLeak")
//	Handler mHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case 1:
//				String str1 = (String) msg.obj;
//				show_proximity_sensor.setText(str1);
//				// show_proximity_sensor.setText(R.string.tv_proximity_sensor_someone);
//				break;
//			case 0:
//				String str = (String) msg.obj;
//				show_proximity_sensor.setText(str);
//				break;
//			// default:
//			// show_proximity_sensor.setText(R.string.tv_proximity_sensor_error);
//			// break;
//			}
//		}
//	};
//
//}
