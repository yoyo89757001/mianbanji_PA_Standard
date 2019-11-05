//package megvii.testfacepass.tps401test.led;
//
//import com.common.pos.api.util.TPS980PosUtil;
//import com.example.tps401test.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class LightActivity extends Activity implements OnClickListener {
//	private Button blue_open, blue_close, green_open, green_close;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.light_activity);
//		initUI();
//	}
//
//	private void initUI() {
//		blue_open = (Button) findViewById(R.id.btn_blue_open);
//		blue_open.setOnClickListener(this);
//		blue_close = (Button) findViewById(R.id.btn_blue_close);
//		blue_close.setOnClickListener(this);
//		green_open = (Button) findViewById(R.id.btn_green_open);
//		green_open.setOnClickListener(this);
//		green_close = (Button) findViewById(R.id.btn_green_close);
//		green_close.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		int ret = -1;
//		switch (v.getId()) {
//		case R.id.btn_blue_open:
//			ret = TPS980PosUtil.setLedPower(1);
//			Toast.makeText(LightActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.btn_blue_close:
//			ret = TPS980PosUtil.setLedPower(0);
//			Toast.makeText(LightActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//
//		case R.id.btn_green_open:
//			ret = TPS980PosUtil.setGREENLedPower(1);
//			Toast.makeText(LightActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.btn_green_close:
//			ret = TPS980PosUtil.setGREENLedPower(0);
//			Toast.makeText(LightActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		default:
//			break;
//		}
//	}
//}
