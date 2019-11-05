//package megvii.testfacepass.tps401test.relay;
//
//import com.common.pos.api.util.TPS980PosUtil;
//import com.example.tps401test.R;
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class RelayActivity extends Activity implements OnClickListener {
//
//	private Button relay_open, relay_close;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_relay);
//		initUI();
//	}
//
//	private void initUI () {
//		relay_open = (Button) findViewById(R.id.relay_open);
//		relay_open.setOnClickListener(this);
//		relay_close = (Button) findViewById(R.id.relay_close);
//		relay_close.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		int ret = -1;
//		switch (v.getId()) {
//		case R.id.relay_open:
//			ret = TPS980PosUtil.setRelayPower(1);
//			Toast.makeText(RelayActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.relay_close:
//			ret = TPS980PosUtil.setRelayPower(0);
//			Toast.makeText(RelayActivity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		default:
//			break;
//		}
//	}
//}
