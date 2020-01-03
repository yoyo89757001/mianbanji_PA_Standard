package megvii.testfacepass.pa.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pingan.ai.access.impl.OnPaAccessControlInitListener;
import com.pingan.ai.access.manager.PaAccessControl;
import com.pingan.ai.auth.manager.PaLicenseManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.objectbox.Box;
import megvii.testfacepass.pa.MyApplication;
import megvii.testfacepass.pa.R;
import megvii.testfacepass.pa.beans.BaoCunBean;

import megvii.testfacepass.pa.utils.AcquireTokenAPI;
import megvii.testfacepass.pa.utils.GetDeviceId;
import megvii.testfacepass.pa.utils.ToastUtils;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;



public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{


    static final String tokenApiUrl = "https://biap-dev-auth-test.pingan.com:10565/dev-auth-web/biap/demo/acticatecode/acquiretoken";
    static final String device = "stest-dev";
    private ProgressDialog mProgressDialog;
    private BaoCunBean baoCunBean;
    private Box<BaoCunBean> baoCunBeanBox=MyApplication.myApplication.getBaoCunBeanBox();
    private static boolean isL=true;
    private SharedPreferences mSharedPreferences;

    static {
        try {
            //Robin pace_face_detect.so需要在授权之前加载
            System.loadLibrary("pace_face_detect");
        } catch (UnsatisfiedLinkError var1) {
            Log.e("Robin", "detection" + var1.toString());
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        baoCunBean=baoCunBeanBox.get(123456L);
        mSharedPreferences = getSharedPreferences("SP", Context.MODE_PRIVATE);
        methodRequiresTwoPermission();

    }

    private final int RC_CAMERA_AND_LOCATION=10000;

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA,
                Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WAKE_LOCK,
                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.INTERNET};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // 已经得到许可，就去做吧 //第一次授权成功也会走这个方法
            Log.d("BaseActivity", "成功获得权限");
            start();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要授予app权限,请点击确定",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        Log.d("BaseActivity", "list.size():" + list.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        Log.d("BaseActivity", "list.size():" + list.size());
        Toast.makeText(BaseActivity.this,"权限被拒绝无法正常使用app",Toast.LENGTH_LONG).show();

    }

    /**
     * 授权初始化(首次联网授权通过后，不进行token校验，否则必须传token值)
     */
    private void startAuthAndInitSDK(final String token) {
        showDialog(true);
        //getAppid用作获取token // getAppkey用作激活
        if (baoCunBean.getAppurl()==null || baoCunBean.getAppid()==null|| baoCunBean.getAppkey()==null){
            showDialog(false);
            ToastUtils.show2(BaseActivity.this, "初始化失败,激活码为空");
            startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class));
            finish();
            return;
        }

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                PaLicenseManager.getInstance()
                        .setToken(token)
                        .setURL(baoCunBean.getAppurl())
                        .setAppId(baoCunBean.getAppkey())
                        .initAuthority(BaseActivity.this, new PaLicenseManager.InitListener() {
                            @Override
                            public void onInitSuccess(String authRes) {
                                PaAccessControl paAccessControl = PaAccessControl.getInstance();
                                paAccessControl.setLogEnable(true);
                                paAccessControl.initPaAccessControl(MyApplication.myApplication, new OnPaAccessControlInitListener() {
                                    @Override
                                    public void onSuccess() {
                                        showDialog(false);
                                        executorService.shutdown();
                                        ToastUtils.show(BaseActivity.this, "初始化成功");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class));
                                                finish();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(final int message) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtils.show2(BaseActivity.this, "初始化失败1,请检查网络"+message);
                                                showDialog(false);
                                                startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                                                finish();
                                            }
                                        });
                                        showDialog(false);
                                        executorService.shutdown();
                                    }
                                });
                            }

                            @Override
                            public void onInitFailed(int code) {
                                showDialog(false);
                                executorService.shutdown();
                                ToastUtils.show2(BaseActivity.this, "初始化失败1,请检查网络"+code);
                                startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                                finish();
                            }

                        });
            }
        });
    }


    private void showDialog(final boolean value) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(BaseActivity.this);
                }
                if (value) {
                    mProgressDialog.setCancelable(false);
                    if (!BaseActivity.this.isFinishing())
                        mProgressDialog.show();
                } else {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private void start(){
        //初始化
        File file = new File(MyApplication.SDPATH);
        if (!file.exists()) {
            Log.d("ggg", "file.mkdirs():" + file.mkdirs());
        }
        File file2 = new File(MyApplication.SDPATH2);
        if (!file2.exists()) {
            Log.d("ggg", "file.mkdirs():" + file2.mkdirs());
        }
        File file3 = new File(MyApplication.SDPATH3);
        if (!file3.exists()) {
            Log.d("ggg", "file.mkdirs():" + file3.mkdirs());
        }

        if (baoCunBean.getTuisongDiZhi()==null || baoCunBean.getTuisongDiZhi().equals("")) {
            String deviceId = GetDeviceId.getDeviceId(BaseActivity.this);
            if (deviceId == null) {
                ToastUtils.show2(BaseActivity.this, "获取设备唯一标识失败");

            } else {
                Log.d("BaseActivity", deviceId + "设备唯一标识");
                baoCunBean.setTuisongDiZhi(deviceId);
                baoCunBeanBox.put(baoCunBean);
            }
        }else {
            Log.d("BaseActivity", baoCunBean.getTuisongDiZhi());
        }
        String token;
        token = mSharedPreferences.getString("token", "");
        if (TextUtils.isEmpty(token)) {
            requestToken();
        } else {
            startAuthAndInitSDK(token);
        }

    }



    private void requestToken() {
        if (baoCunBean.getAppurl()==null || baoCunBean.getAppid()==null|| baoCunBean.getAppkey()==null){
            showDialog(false);
            ToastUtils.show2(BaseActivity.this, "初始化失败,激活码为空");
            startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class));
            finish();
            return;
        }

        showDialog(true);
        new AcquireTokenAPI().requestToken(tokenApiUrl, baoCunBean.getAppid(), device, new AcquireTokenAPI.AcquireTokenListener() {
            @Override
            public void onSuccess(String response) {
                String token;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    Log.e("ssss", "get token response json: " + response);
                    if ("0".equals(code) && jsonObject.has("data")) {
                        String dataJson = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(dataJson);
                        token = jsonObject1.getString("token");
                        if (mSharedPreferences != null) {
                            mSharedPreferences.edit().putString("token", token).apply();
                        }
                        startAuthAndInitSDK(token);
                    } else {
                        showDialog(false);
                        Toast.makeText(getApplicationContext(), "获取token网络请求失败", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showDialog(false);
                    Toast.makeText(getApplicationContext(), "获取token,Json解析异常", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                    finish();
                }
            }

            @Override
            public void onFail() {
                showDialog(false);
                Toast.makeText(getApplicationContext(), "获取token网络请求失败", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                finish();
            }
        });
    }


    public static class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Intent i = new Intent(context, BaseActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
