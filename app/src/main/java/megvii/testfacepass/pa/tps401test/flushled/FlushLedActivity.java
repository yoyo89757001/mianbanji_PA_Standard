//package megvii.testfacepass.tps401test.flushled;
//
//
//import com.example.tps401test.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class FlushLedActivity extends Activity implements OnClickListener {
//
//	private Button flush_led_open, flush_led_close;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_flush_led);
//		initUI();
//	}
//
//	private void initUI () {
//		flush_led_open = (Button) findViewById(R.id.flush_led_open);
//		flush_led_open.setOnClickListener(this);
//		flush_led_close = (Button) findViewById(R.id.flush_led_close);
//		flush_led_close.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		int ret = -1;
//		switch (v.getId()) {
//		case R.id.flush_led_open:
//			ret = TPS980PosUtil.setFlushLedPower(1);
//			Toast.makeText(FlushLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.flush_led_close:
//			ret = TPS980PosUtil.setFlushLedPower(0);
//			Toast.makeText(FlushLedActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		default:
//			break;
//		}
//	}
//
//}
