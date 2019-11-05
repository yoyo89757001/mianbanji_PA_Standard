package megvii.testfacepass.pa.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import megvii.testfacepass.pa.R;
import megvii.testfacepass.pa.utils.SettingVar;

/**
 * Created by wangzhiqiang on 2017/11/22.
 */

public class SettingActivity extends Activity implements View.OnClickListener {

    LinearLayout settingLayout;
    Button okButton;
    Button cancelButton;
    TextView dangqianzhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        okButton = (Button) findViewById(R.id.ok);
        dangqianzhi=findViewById(R.id.dangqianzhi);
        cancelButton = (Button) findViewById(R.id.cancel);
        okButton.setOnClickListener(SettingActivity.this);
        cancelButton.setOnClickListener(this);
        SharedPreferences preferences = getSharedPreferences(SettingVar.SharedPrefrence, Context.MODE_PRIVATE);
        SettingVar.isSettingAvailable = preferences.getBoolean("isSettingAvailable", SettingVar.isSettingAvailable);
        SettingVar.cameraId = preferences.getInt("cameraId", SettingVar.cameraId);
        SettingVar.faceRotation = preferences.getInt("faceRotation", SettingVar.faceRotation);
        SettingVar.cameraPreviewRotation = preferences.getInt("cameraPreviewRotation", SettingVar.cameraPreviewRotation);
        SettingVar.cameraFacingFront = preferences.getBoolean("cameraFacingFront", SettingVar.cameraFacingFront);
        SettingVar.cameraPreviewRotation2 = preferences.getInt("cameraPreviewRotation2", SettingVar.cameraPreviewRotation2);
        SettingVar.faceRotation2 = preferences.getInt("faceRotation2", SettingVar.faceRotation2);
        SettingVar.msrBitmapRotation = preferences.getInt("msrBitmapRotation", SettingVar.msrBitmapRotation);

        dangqianzhi.setText("当前值:摄像头id:"+SettingVar.cameraId+"\n"+"预览方向值:"+SettingVar.cameraPreviewRotation+"\n"
                +"算法方向值:"+SettingVar.faceRotation+"\n"+"红外预览方向值:"+SettingVar.cameraPreviewRotation2+"\n"
                +"红外算法方向值:"+SettingVar.faceRotation2+"\n"+"陌生人抓拍图片方向:"+SettingVar.msrBitmapRotation);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                SettingVar.isSettingAvailable = true;
                SharedPreferences preferences = getSharedPreferences(SettingVar.SharedPrefrence, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("cameraFacingFront", SettingVar.cameraFacingFront);
                editor.putBoolean("isSettingAvailable", SettingVar.isSettingAvailable);
                editor.putInt("cameraId", SettingVar.cameraId);
                editor.putInt("faceRotation", SettingVar.faceRotation);
                editor.putInt("cameraPreviewRotation", SettingVar.cameraPreviewRotation);
                editor.putInt("faceRotation2", SettingVar.faceRotation2);
                editor.putInt("cameraPreviewRotation2", SettingVar.cameraPreviewRotation2);
                editor.putInt("msrBitmapRotation", SettingVar.msrBitmapRotation);
                editor.apply();

                Log.d("SettingActivity", "SettingVar.msrBitmapRotation:" + SettingVar.msrBitmapRotation);
                SettingActivity.this.finish();
                break;
            case R.id.cancel:

                SettingActivity.this.finish();

                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.front:
                if (checked) {
                    SettingVar.cameraFacingFront = true;
                    SettingVar.cameraId=0;
                }
                break;
            case R.id.back:
                if (checked) {
                    SettingVar.cameraFacingFront = false;
                    SettingVar.cameraId=1;
                }
                break;
        }
    }

    public void onCameraRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.camera0:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 0;
                }
                break;
            case R.id.camera90:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 90;
                }
                break;
            case R.id.camera180:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 180;
                }
                break;
            case R.id.camera270:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 270;
                }
                break;
        }
    }

    public void onCameraRadioButtonClicked2(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.camera02:
                if (checked) {
                    SettingVar.cameraPreviewRotation2 = 0;
                }
                break;
            case R.id.camera902:
                if (checked) {
                    SettingVar.cameraPreviewRotation2 = 90;
                }
                break;
            case R.id.camera1802:
                if (checked) {
                    SettingVar.cameraPreviewRotation2 = 180;
                }
                break;
            case R.id.camera2702:
                if (checked) {
                    SettingVar.cameraPreviewRotation2 = 270;
                }
                break;
        }
    }


    public void onCross(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.YES:
                if (checked) {
                    SettingVar.isCross = true;
                }
                break;
            case R.id.NO:
                if (checked) {
                    SettingVar.isCross = false;
                }
                break;
        }
    }

    public void onFaceRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.face0:
                if (checked) {
                    SettingVar.faceRotation = 0;
                }
                break;
            case R.id.face90:
                if (checked) {
                    SettingVar.faceRotation = 90;
                }
                break;
            case R.id.face180:
                if (checked) {
                    SettingVar.faceRotation = 180;
                }
                break;
            case R.id.face270:
                if (checked) {
                    SettingVar.faceRotation = 270;
                }
                break;
        }
    }
    public void onFaceRadioButtonClicked2(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.face02:
                if (checked) {
                    SettingVar.faceRotation2 = 0;
                }
                break;
            case R.id.face902:
                if (checked) {
                    SettingVar.faceRotation2 = 90;
                }
                break;
            case R.id.face1802:
                if (checked) {
                    SettingVar.faceRotation2 = 180;
                }
                break;
            case R.id.face2702:
                if (checked) {
                    SettingVar.faceRotation2 = 270;
                }
                break;
        }
    }
    public void onFaceRadioButtonClicked33(View view) {
      //  Log.d("SettingActivity", "0:" + 0);
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.face03:
                if (checked) {
                    Log.d("SettingActivity", "0:" + 0);
                    SettingVar.msrBitmapRotation = 0;
                }
                break;
            case R.id.face903:
                if (checked) {
                    Log.d("SettingActivity", "90:" + 90);
                    SettingVar.msrBitmapRotation = 90;
                }
                break;
            case R.id.face1803:
                if (checked) {
                    Log.d("SettingActivity", "180:" + 180);
                    SettingVar.msrBitmapRotation = 180;
                }
                break;
            case R.id.face2703:
                if (checked) {
                    Log.d("SettingActivity", "270:" + 270);
                    SettingVar.msrBitmapRotation = 270;
                }
                break;
        }
    }
}
