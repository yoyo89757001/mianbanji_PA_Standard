package megvii.testfacepass.pa;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.serialport.SerialPort;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.tencent.bugly.Bugly;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import megvii.testfacepass.pa.beans.BaoCunBean;
import megvii.testfacepass.pa.beans.BenDiJiLuBean;

import megvii.testfacepass.pa.beans.DaKaBean;

import megvii.testfacepass.pa.beans.HuiFuBean;
import megvii.testfacepass.pa.beans.IDCardBean;

import megvii.testfacepass.pa.beans.MyObjectBox;
import megvii.testfacepass.pa.beans.Subject;



import megvii.testfacepass.pa.dialogall.CommonData;
import megvii.testfacepass.pa.dialogall.CommonDialogService;
import megvii.testfacepass.pa.dialogall.ToastUtils;


/**
 * Created by Administrator on 2018/8/3.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static MyApplication myApplication;
    //private Box<ChengShiIDBean> chengShiIDBeanBox=null;
    private Box<BaoCunBean> baoCunBeanBox=null;
    private Box<Subject> subjectBox=null;
    //private Box<LunBoBean> lunBoBeanBox=null;
   // private Box<XinXiAll> xinXiAllBox=null;
   // private Box<XinXiIdBean> xinXiIdBeanBox= null;
   // private Box<GuanHuai> guanHuaiBox=null;
    private Box<HuiFuBean> huiFuBeanBox = null;
    private Box<BenDiJiLuBean> benDiJiLuBeanBox = null;
    private Box<DaKaBean> daKaBeanBox = null;
    private Box<IDCardBean> idCardBeanBox = null;
    public static final String GROUP_FRAME = "FrameGroup";
    public static final String GROUP_IMAGE = "ImageGroup";
    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"ruitongzipmbj";
    public static final String SDPATH2 = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"ruitongmbj";
    public static final String SDPATH3 = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"ruitongface";
   // protected OutputStream mOutputStream;
    //这个是给东莞机房的
    //public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            /* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences("android_serialport_api.sample_preferences", MODE_PRIVATE);
            String path = sp.getString("DEVICE", "/dev/ttyS2");
            int baudrate = Integer.decode(Objects.requireNonNull(sp.getString("BAUDRATE", "9600")));

            /* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

            try {
                mSerialPort = new SerialPort(new File(path), baudrate, 0);
            }catch (Exception e){
                e.printStackTrace();
            }
            /* Open the serial port */

        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        BoxStore mBoxStore = MyObjectBox.builder().androidContext(this).build();

        Bugly.init(getApplicationContext(), "90bbef222c", false);

      //  Log.d("MyApplication","机器码"+ FileUtil.getSerialNumber(this) == null ? FileUtil.getIMSI() : FileUtil.getSerialNumber(this));
        //全局dialog
        this.registerActivityLifecycleCallbacks(this);//注册
        CommonData.applicationContext = this;
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        CommonData.ScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
        Intent dialogservice = new Intent(this, CommonDialogService.class);
        startService(dialogservice);

        baoCunBeanBox= mBoxStore.boxFor(BaoCunBean.class);
        subjectBox= mBoxStore.boxFor(Subject.class);
        huiFuBeanBox= mBoxStore.boxFor(HuiFuBean.class);
      //  xinXiAllBox= mBoxStore.boxFor(XinXiAll.class);
       // xinXiIdBeanBox= mBoxStore.boxFor(XinXiIdBean.class);
      //  guanHuaiBox= mBoxStore.boxFor(GuanHuai.class);
       // chengShiIDBeanBox= mBoxStore.boxFor(ChengShiIDBean.class);
      //  todayBeanBox= mBoxStore.boxFor(TodayBean.class);
        benDiJiLuBeanBox= mBoxStore.boxFor(BenDiJiLuBean.class);
      //  chengShiIDBeanBox= mBoxStore.boxFor(ChengShiIDBean.class);
        daKaBeanBox= mBoxStore.boxFor(DaKaBean.class);
        idCardBeanBox= mBoxStore.boxFor(IDCardBean.class);

//        AutoSize.initCompatMultiProcess(this);
//        AutoSizeConfig.getInstance()
//
//                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
//                //如果没有这个需求建议不开启
//              //  .setCustomFragment(true)
//
//                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
//                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
//                .setExcludeFontScale(true)
//
//                //屏幕适配监听器
//                .setOnAdaptListener(new onAdaptListener() {
//                    @Override
//                    public void onAdaptBefore(Object target, Activity activity) {
//                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
////                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
////                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
//                    }
//
//                    @Override
//                    public void onAdaptAfter(Object target, Activity activity) {
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
//                    }
//                })
//
//        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)
//
//        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
//        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
//        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//                .setUseDeviceSize(true)
//
//        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
////                .setBaseOnWidth(false)
//
//        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
////                .setAutoAdaptStrategy(new AutoAdaptStrategy())
//        ;
//        customAdaptForExternal();


      BaoCunBean  baoCunBean = mBoxStore.boxFor(BaoCunBean.class).get(123456L);
        if (baoCunBean == null) {
            baoCunBean = new BaoCunBean();
            baoCunBean.setHoutaiDiZhi("http://");
            baoCunBean.setId(123456L);
            baoCunBean.setShibieFaceSize(50);
            baoCunBean.setShibieFaZhi(0.52f);
            baoCunBean.setRuKuFaceSize(70);
            baoCunBean.setRuKuMoHuDu(0.3f);
            baoCunBean.setHuoTiFZ(70);
            baoCunBean.setMima(123456);
            baoCunBean.setYusu(5);
            baoCunBean.setYudiao(5);
            baoCunBean.setMima2(123456);
            baoCunBean.setJihuoma("0000-0000-0000-0000-0000");
            baoCunBean.setHuoTi(false);
            baoCunBean.setDangqianShiJian("d");
            baoCunBean.setTianQi(false);
            baoCunBean.setTishiyu("欢迎光临");
            baoCunBean.setPort(8090);

            mBoxStore.boxFor(BaoCunBean.class).put(baoCunBean);
        }


        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))
                .commit();


    }

  //  public Box<TodayBean> getTodayBeanBox(){
      //  return todayBeanBox;
   // }

    public Box<BenDiJiLuBean> getBenDiJiLuBeanBox(){
        return benDiJiLuBeanBox;
    }
   // public Box<ChengShiIDBean> getChengShiIDBeanBox(){
      //  return chengShiIDBeanBox;
   // }

    public Box<Subject> getSubjectBox(){
        return subjectBox;
    }

    public Box<HuiFuBean> getHuiFuBeanBox(){
        return huiFuBeanBox;
    }
//
//    public Box<XinXiAll> getXinXiAllBox(){
//        return xinXiAllBox;
//    }
//    public Box<XinXiIdBean> getXinXiIdBeanBox(){
//        return xinXiIdBeanBox;
//    }
//    public Box<GuanHuai> getGuanHuaiBox(){
//        return guanHuaiBox;
//    }
    public Box<BaoCunBean> getBaoCunBeanBox(){
        return baoCunBeanBox;
    }
    public Box<DaKaBean> getDaKaBeanBox(){
        return daKaBeanBox;
    }
    public Box<IDCardBean> getIdCardBeanBox(){
        return idCardBeanBox;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ToastUtils.getInstances().cancel();
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /**
     * 给外部的三方库 {@link Activity} 自定义适配参数, 因为三方库的 {@link Activity} 并不能通过实现
     * { CustomAdapt} 接口的方式来提供自定义适配参数 (因为远程依赖改不了源码)
     * 所以使用 { ExternalAdaptManager} 来替代实现接口的方式, 来提供自定义适配参数
     */
    private void customAdaptForExternal() {
        /**
         * {@link ExternalAdaptManager} 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看 {@link ExternalAdaptManager} 的类注释
         */
       // AutoSizeConfig.getInstance().getExternalAdaptManager();

                //加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
                //如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
//                .addCancelAdaptOfActivity(DefaultErrorActivity.class)

                //为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
                //一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
                //就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
                //三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
                //由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
                //那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
                //即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
                //但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
                //经过测试 DefaultErrorActivity 的设计图宽度在 380dp - 400dp 显示效果都是比较舒服的
               // .addExternalAdaptInfoOfActivity(DefaultErrorActivity.class, new ExternalAdaptInfo(true, 400));
    }
}
