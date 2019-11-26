package megvii.testfacepass.pa.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraCharacteristics;



import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pingan.ai.access.impl.OnPaAccessControlInitListener;
import com.pingan.ai.access.manager.PaAccessControl;
import com.pingan.ai.auth.common.SDKType;
import com.pingan.ai.auth.manager.PaLicenseManager;



import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.io.IOException;
import java.util.List;
import io.objectbox.Box;
import megvii.testfacepass.pa.MyApplication;
import megvii.testfacepass.pa.R;
import megvii.testfacepass.pa.beans.BaoCunBean;

import megvii.testfacepass.pa.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;



public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    //80个的平安算法
  //  private static final String APP_ID = "cURlaUppZEhDZEZoaW1KQmpScXJTUjA5YzlLTmVZeWZXSmd0eFJUVG10VT0=";
 //   private static final String APP_KEY = "21332D4DFCD12784";
  //  private static final String AUTH_PRODUCT_URL = "https://biap-dev-auth.pingan.com/dev-auth-web/biap/device/v3/activeDeviceAuthInfo";
//    private static final String AUTH_METHOD = "/dev-auth-web/biap/device/v2/activeDeviceAuthInfo"; //Robin URL变为v2
    private ProgressDialog mProgressDialog;
    private BaoCunBean baoCunBean;
    private Box<BaoCunBean> baoCunBeanBox=MyApplication.myApplication.getBaoCunBeanBox();
    private static boolean isL=true;

    static {
        try {
                //Robin pace_face_detect.so需要在授权之前加载
              System.loadLibrary("pace_face_detect");
        }
        catch (UnsatisfiedLinkError var1) {
            Log.e("Robin", "detection" + var1.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        baoCunBean=baoCunBeanBox.get(123456L);

        methodRequiresTwoPermission();

    }

    private final int RC_CAMERA_AND_LOCATION=10000;

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.EXPAND_STATUS_BAR,
                Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.ACCESS_FINE_LOCATION,
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
        for (String s:list){
            Log.d("BaseActivity", s);
        }
        Log.d("BaseActivity", "list.size():" + list.size());
        Toast.makeText(BaseActivity.this,"权限被拒绝无法正常使用app",Toast.LENGTH_LONG).show();
        finish();

    }


    private void initPaAccessControl() {

        mProgressDialog = new ProgressDialog(this);
        showDialog(true);
        // 查询，批量插入等耗时操作，请开启线程。请勿开启多线程同时进行SDK不同的操作，等一个动作完成后，再进行其他操作
        // 必须要等SDK初始化完成后才能进行后续操作
        if (baoCunBean.getAppurl()==null || baoCunBean.getAppid()==null|| baoCunBean.getAppkey()==null){
            showDialog(false);
            ToastUtils.show2(BaseActivity.this, "初始化失败,激活码为空");
            startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class));
            finish();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
               // if (baoCunBean.getAppid()!=null && baoCunBean.getAppkey()!=null && baoCunBean.getAppurl()!=null){
                    PaLicenseManager.getInstance()
                            // .setAppId(APP_ID)
                            // .setAppKey(APP_KEY)
                            // .setURL(AUTH_PRODUCT_URL)
                            .setAppId( baoCunBean.getAppid())
                            .setAppKey(baoCunBean.getAppkey())
                            .setURL(baoCunBean.getAppurl())
                            .setSDKType(SDKType.ONEVN)
                            .initAuthority(BaseActivity.this, new PaLicenseManager.InitListener() {
                                @Override
                                public void onInitSuccess(String s) {
                                    PaAccessControl paAccessControl = PaAccessControl.getInstance();
                                    paAccessControl.setLogEnable(false);
                                    paAccessControl.initPaAccessControl(MyApplication.myApplication, new OnPaAccessControlInitListener() {
                                        @Override
                                        public void onSuccess() {
                                            showDialog(false);
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
                                        public void onError(int i) {
                                            showDialog(false);
                                            ToastUtils.show2(BaseActivity.this, "初始化失败1,请检查网络"+i);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                                                    finish();
                                                }
                                            });

                                        }
                                    });

                                }

                                @Override
                                public void onInitFailed(int i) {
                                    showDialog(false);
                                    ToastUtils.show2(BaseActivity.this, "初始化失败2,请检查网络"+i);
                                    startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
                                    finish();
                                }
                            });
//                }else {
//                    showDialog(false);
//                    ToastUtils.show2(BaseActivity.this, "初始化失败,缺少激活数据");
//                    startActivity(new Intent(BaseActivity.this,MianBanJiActivity3.class).putExtra("dddd",-1));
//                    finish();
//                }



            }
        }).start();
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

        //开启信鸽的日志输出，线上版本不建议调用
      //  XGPushConfig.enableDebug(getApplicationContext(), true);
        //ed02bf3dc1780d644f0797a9153963b37ed570a5
 /*
        注册信鸽服务的接口
        如果仅仅需要发推送消息调用这段代码即可
        */
//        XGPushManager.registerPush(getApplicationContext(),
//                new XGIOperateCallback() {
//                    @Override
//                    public void onSuccess(Object data, int flag) {
//                        isL=false;
//                        String deviceId=null;
//                        baoCunBean.setXgToken(data+"");
//                        Log.w("MainActivity", "+++ register push sucess. token:" + data + "flag" + flag);
//
//                        if (baoCunBean.getTuisongDiZhi()==null || baoCunBean.getTuisongDiZhi().equals("")) {
//                             deviceId = GetDeviceId.getDeviceId(BaseActivity.this);
//                            if (deviceId==null){
//                                ToastUtils.show2(BaseActivity.this,"获取设备唯一标识失败");
//
//                            }else {
//                                Log.d("BaseActivity", deviceId+"设备唯一标识");
//                                baoCunBean.setTuisongDiZhi(deviceId);
//                                baoCunBeanBox.put(baoCunBean);
//                            }
//                        }else {
//                            Log.d("BaseActivity", baoCunBean.getTuisongDiZhi()+"设备唯一标识");
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                initPaAccessControl();
//                            }
//                        });
//                        link_uplod(baoCunBean.getTuisongDiZhi(),data+"");
//
//                    }
//                    @Override
//                    public void onFail(Object data, int errCode, String msg) {
//                        isL=false;
//                        Log.w("MainActivity",
//                                "+++ register push fail. token:" + data
//                                        + ", errCode:" + errCode + ",msg:"
//                                        + msg);
//
//                        ToastUtils.show2(BaseActivity.this,"注册信鸽推送失败,请检查网络"+msg);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                initPaAccessControl();
//                            }
//                        });
//
//                    }
//
//                });
//        try {
//            CameraManager manager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
//            isHardwareSupported(manager.getCameraCharacteristics("1"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        initPaAccessControl();
    }

    // CameraCharacteristics  可通过 CameraManager.getCameraCharacteristics() 获取
    private int isHardwareSupported(CameraCharacteristics characteristics) {
        Integer deviceLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        if (deviceLevel == null) {
            Log.e("camera2", "can not get INFO_SUPPORTED_HARDWARE_LEVEL");
            return -1;
        }
        switch (deviceLevel) {
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL:
                Log.w("camera2", "hardware supported level:LEVEL_FULL");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY:
                Log.w("camera2", "hardware supported level:LEVEL_LEGACY");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3:
                Log.w("camera2", "hardware supported level:LEVEL_3");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED:
                Log.w("camera2", "hardware supported level:LEVEL_LIMITED");
                break;
        }
        return deviceLevel;
    }


    //更新信鸽token
    private void link_uplod(String deviceId,  String token){
        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient= new OkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("machineCode", deviceId+"")
                .add("machineToken",token+"")
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi()+"/app/updateToken");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
                EventBus.getDefault().post("网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                //  Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                String ss=null;
                try{
                    ResponseBody body = response.body();
                    ss=body.string().trim();
                    Log.d("AllConnects", "更新信鸽token:"+ss);


                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                }

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
