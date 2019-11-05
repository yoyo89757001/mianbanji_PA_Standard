//package megvii.testfacepass.tps401test.rs485;
//
//import com.common.pos.api.util.TPS980PosUtil;
//import com.example.tps401test.R;
//import com.example.tps401test.relay.RelayActivity;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class Rs485Activity extends Activity implements OnClickListener {
//
//	private Button rs485_send, rs485_receive;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_rs485);
//		initUI();
//	}
//
//	private void initUI () {
//		rs485_send = (Button) findViewById(R.id.rs485_send);
//		rs485_send.setOnClickListener(this);
//		rs485_receive = (Button) findViewById(R.id.rs485_receive);
//		rs485_receive.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		int ret = -1;
//		switch (v.getId()) {
//		case R.id.rs485_send:
//			ret = TPS980PosUtil.setRs485Status(1);
//			Toast.makeText(Rs485Activity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		case R.id.rs485_receive:
//			ret = TPS980PosUtil.setRs485Status(0);
//			Toast.makeText(Rs485Activity.this, "" + ret, Toast.LENGTH_SHORT).show();
//			break;
//		default:
//			break;
//		}
//	}
//}
