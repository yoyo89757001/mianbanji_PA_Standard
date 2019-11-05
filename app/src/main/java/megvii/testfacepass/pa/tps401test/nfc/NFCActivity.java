package megvii.testfacepass.pa.tps401test.nfc;




import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

import com.common.pos.api.util.Utils;

import megvii.testfacepass.pa.R;

public class NFCActivity extends Activity {
	
	private TextView show_nfc_message;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		initUI();
		NfcManager mNfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
        mNfcAdapter = mNfcManager.getDefaultAdapter();
        if (mNfcAdapter == null) {
        	show_nfc_message.setText(R.string.tv_nfc_notsupport);
        } else if ((mNfcAdapter != null) && (!mNfcAdapter.isEnabled())) {
        	show_nfc_message.setText(R.string.tv_nfc_notwork);
        } else if ((mNfcAdapter != null) && (mNfcAdapter.isEnabled())) {
        	show_nfc_message.setText(R.string.tv_nfc_working);
        }
        mPendingIntent =PendingIntent.getActivity(this, 0, new Intent(this,getClass()), 0);
		init_NFC();
	}
	
	private void initUI () {
		show_nfc_message = (TextView) findViewById(R.id.show_nfc_message);
	}
	
	@Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent().getAction())) {
                processIntent(this.getIntent());
            }
        }
    }
	
	@Override
    public void onNewIntent(Intent intent) {
        processIntent(intent);
    }
    
    public void processIntent(Intent intent) {
    	String data = null;
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = tag.getTechList();
        byte[] ID = new byte[20];
        data = tag.toString();
        ID =  tag.getId();
        data += "\n\nUID:\n" +Utils.bytesToHexString(ID);
        data += "\nData format:";
        for (String tech : techList) {
            data += "\n" + tech;
        }
        show_nfc_message.setText(data);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            stopNFC_Listener();
        }
    }
    
    private void init_NFC() {
    	IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
    }

    private void stopNFC_Listener() {
        mNfcAdapter.disableForegroundDispatch(this);
    }

}
