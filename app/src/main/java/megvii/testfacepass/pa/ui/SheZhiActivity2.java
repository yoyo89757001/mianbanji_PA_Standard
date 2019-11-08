package megvii.testfacepass.pa.ui;

import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pingan.ai.access.common.PaAccessControlMessage;
import com.pingan.ai.access.manager.PaAccessControl;
import com.pingan.ai.access.result.PaAccessDetectFaceResult;
import com.sdsmdg.tastytoast.TastyToast;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;

import megvii.testfacepass.pa.MyApplication;
import megvii.testfacepass.pa.R;
import megvii.testfacepass.pa.beans.BaoCunBean;

import megvii.testfacepass.pa.beans.ChengShiIDBean;
import megvii.testfacepass.pa.beans.DaKaBean;


import megvii.testfacepass.pa.beans.JsonBean;
import megvii.testfacepass.pa.beans.Subject;
import megvii.testfacepass.pa.dialog.BangDingDialog;
import megvii.testfacepass.pa.dialog.XiuGaiDiZhiDialog;
import megvii.testfacepass.pa.dialog.XiuGaiHuoTiFZDialog;
import megvii.testfacepass.pa.dialog.XiuGaiMiMaDialog;
import megvii.testfacepass.pa.dialog.XiuGaiRuKuFZDialog;
import megvii.testfacepass.pa.dialog.XiuGaiSBFZDialog;
import megvii.testfacepass.pa.dialog.XiuGaiYuYinDialog;
import megvii.testfacepass.pa.dialog.XiuGaigGSMDialog;
import megvii.testfacepass.pa.dialog.YuYingDialog;

import megvii.testfacepass.pa.dialogall.ToastUtils;
import megvii.testfacepass.pa.utils.BitmapUtil;
import megvii.testfacepass.pa.utils.DateUtils;
import megvii.testfacepass.pa.utils.DiaLogUtil;

import megvii.testfacepass.pa.utils.FaceInit;
import megvii.testfacepass.pa.utils.FileUtil;

import megvii.testfacepass.pa.utils.GsonUtil;
import megvii.testfacepass.pa.utils.RestartAPPTool;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SheZhiActivity2 extends Activity {
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl6)
    RelativeLayout rl6;
    @BindView(R.id.rl7)
    RelativeLayout rl7;
    @BindView(R.id.rl8)
    RelativeLayout rl8;
    @BindView(R.id.switchs)
    Switch switchs;
    @BindView(R.id.switchs5)
    Switch switchs5;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.rl9)
    RelativeLayout rl9;
    @BindView(R.id.rl13)
    RelativeLayout rl13;
    @BindView(R.id.rl14)
    RelativeLayout rl14;
    @BindView(R.id.rl15)
    RelativeLayout rl15;
    @BindView(R.id.rl16)
    RelativeLayout rl16;
    @BindView(R.id.rl17)
    RelativeLayout rl17;
    @BindView(R.id.rl28)
    RelativeLayout rl28;
    @BindView(R.id.daochu)
    Button daochu;
    @BindView(R.id.fanhui_ll)
    LinearLayout fanhui_ll;
    @BindView(R.id.chengshi)
    TextView chengshi;
  //  JiaZaiDialog dddd;
    private ZLoadingDialog zLoadingDialog;
    private  final String group_name = "facepasstestx";
    private BangDingDialog bangDingDialog=null;
    private Box<BaoCunBean> baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
   // public OkHttpClient okHttpClient = null;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();//省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private Box<ChengShiIDBean> chengShiIDBeanBox;
    private static String usbPath = null;
   // private int shibai;
    private Box<DaKaBean> daKaBeanBox=MyApplication.myApplication.getDaKaBeanBox();
    private Box<Subject> subjectBox=MyApplication.myApplication.getSubjectBox();
    private boolean isT=true;
    /* SDK 实例对象 */
   // FacePassHandler mFacePassHandler;
    private int shibai=-1;
    private StringBuilder stringBuilder2 = new StringBuilder();
    private PaAccessControl paAccessControl=null;
    private boolean isFF =false,isFG=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi2);
        ButterKnife.bind(this);
        paAccessControl = PaAccessControl.getInstance();
        //ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        options1Items.add(new JsonBean("天波"));
        options1Items.add(new JsonBean("涂鸦"));
        baoCunBeanDao = MyApplication.myApplication.getBaoCunBeanBox();
       // chengShiIDBeanBox = MyApplication.myApplication.getChengShiIDBeanBox();
        baoCunBean=baoCunBeanDao.get(123456L);
       // mFacePassHandler=MyApplication.myApplication.getFacePassHandler();
        EventBus.getDefault().register(this);//订阅

        if (baoCunBean.isHuoTi()){
            switchs.setChecked(true);
        }else {
            switchs.setChecked(false);
        }
        switchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    baoCunBean.setHuoTi(true);
                    baoCunBeanDao.put(baoCunBean);
                    Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "活体验证已开启", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                } else {
                    baoCunBean.setHuoTi(false);
                    baoCunBeanDao.put(baoCunBean);
                    Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "活体验证已关闭", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }
            }
        });

        fanhui_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                initJsonData();
//            }
//        }).start();


        switchs5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isFF){
                    isFF = true;
                    ZLoadingDialog zLoadingDialog = new ZLoadingDialog(SheZhiActivity2.this);

                    zLoadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                            .setLoadingColor(Color.parseColor("#0d2cf9"))//颜色
                            .setHintText("请放入IC卡...")
                            .setHintTextSize(16) // 设置字体大小 dp
                            .setHintTextColor(Color.WHITE)  // 设置字体颜色
                            .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                            .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                            .show();

                }else {
                    isFF=false;

                }


            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }



//    @Override
//    public void onNewIntent(Intent intent) {
//      //  super.onNewIntent(intent);
//       // Log.d("SheZhiActivity2", "intent:" + intent);
//        processIntent(intent);
//    }




    @OnClick({R.id.rl1,R.id.rl11,R.id.rl12, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6, R.id.rl7,R.id.rl8, R.id.rl9,R.id.rl13,R.id.rl14,R.id.rl15,R.id.rl16,R.id.rl17,R.id.rl28,R.id.daochu,R.id.rl33})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl1: {
                final XiuGaiDiZhiDialog diZhiDialog = new XiuGaiDiZhiDialog(SheZhiActivity2.this);
                diZhiDialog.setCanceledOnTouchOutside(false);
                diZhiDialog.setContents("设置后端地址", baoCunBean.getHoutaiDiZhi(), null);
                diZhiDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setHoutaiDiZhi(diZhiDialog.getUrl());
                        baoCunBeanDao.put(baoCunBean);
                        diZhiDialog.dismiss();
                    }
                });
                diZhiDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog.dismiss();
                    }
                });
                DiaLogUtil.init(diZhiDialog);
                diZhiDialog.show();

                break;
            }
            case R.id.rl11: {
                final XiuGaiDiZhiDialog diZhiDialog = new XiuGaiDiZhiDialog(SheZhiActivity2.this);
                diZhiDialog.setCanceledOnTouchOutside(false);
                diZhiDialog.setContents("设置刷脸回调地址", baoCunBean.getShualianhuidiaodizhi(), null);
                diZhiDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setShualianhuidiaodizhi(diZhiDialog.getUrl());
                        baoCunBeanDao.put(baoCunBean);
                        diZhiDialog.dismiss();
                    }
                });
                diZhiDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog.dismiss();
                    }
                });
                DiaLogUtil.init(diZhiDialog);
                diZhiDialog.show();

                break;
            }
            case R.id.rl12: {
                final XiuGaiDiZhiDialog diZhiDialog = new XiuGaiDiZhiDialog(SheZhiActivity2.this);
                diZhiDialog.setCanceledOnTouchOutside(false);
                diZhiDialog.setContents("设置端口号", baoCunBean.getPort()+"", null);
                diZhiDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int pp = Integer.valueOf(diZhiDialog.getUrl());
                            baoCunBean.setPort(pp);
                            baoCunBeanDao.put(baoCunBean);
                            diZhiDialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "输入的端口号非数字", TastyToast.LENGTH_LONG, TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();
                        }

                    }
                });
                diZhiDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog.dismiss();
                    }
                });
                DiaLogUtil.init(diZhiDialog);
                diZhiDialog.show();

                break;
            }
            case R.id.rl33:
                final XiuGaiYuYinDialog diZhiDialogyy = new XiuGaiYuYinDialog(SheZhiActivity2.this);
                diZhiDialogyy.setCanceledOnTouchOutside(false);
                diZhiDialogyy.setContents(baoCunBean.getTishiyu(), null);
                diZhiDialogyy.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setTishiyu(diZhiDialogyy.getUrl());
                        baoCunBeanDao.put(baoCunBean);
                        diZhiDialogyy.dismiss();
                    }
                });
                diZhiDialogyy.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialogyy.dismiss();
                    }
                });
                DiaLogUtil.init(diZhiDialogyy);
                diZhiDialogyy.show();

                break;
            case R.id.rl2:
                bangDingDialog = new BangDingDialog(SheZhiActivity2.this);
                bangDingDialog.setCanceledOnTouchOutside(false);
                bangDingDialog.setContents(baoCunBean.getJihuoma()+"",null);
                bangDingDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String jihuoma=bangDingDialog.getZhuCeMa();
                        String[] jhm = jihuoma.split("-");
                        if (jhm.length==5){
                            baoCunBean.setJihuoma(jihuoma);
                            baoCunBeanDao.put(baoCunBean);
                            Log.d("SheZhiActivity2", "保存激活码成功");
                        }
                        FaceInit init=new FaceInit(SheZhiActivity2.this);
                        init.init(jihuoma,baoCunBean);
                        bangDingDialog.jiazai();
                    }
                });
                bangDingDialog.setCanceledOnTouchOutside(false);
                bangDingDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bangDingDialog.dismiss();
                    }
                });
                DiaLogUtil.init(bangDingDialog);
                bangDingDialog.show();
                break;
            case R.id.rl3:
                YuYingDialog yuYingDialog = new YuYingDialog(SheZhiActivity2.this);
                yuYingDialog.show();
                break;
            case R.id.rl4:
                //识别阀值
                final XiuGaiSBFZDialog sbfzDialog = new XiuGaiSBFZDialog(SheZhiActivity2.this);
                sbfzDialog.setContents(baoCunBean.getShibieFaZhi() + "");
                sbfzDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            baoCunBean.setShibieFaZhi(Float.valueOf(sbfzDialog.getfazhi()));
                            baoCunBeanDao.put(baoCunBean);
                            sbfzDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                sbfzDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sbfzDialog.dismiss();
                    }
                });
                DiaLogUtil.init(sbfzDialog);
                sbfzDialog.show();
                break;
            case R.id.rl5:
                final XiuGaiHuoTiFZDialog huoTiFZDialog = new XiuGaiHuoTiFZDialog(SheZhiActivity2.this);
                huoTiFZDialog.setContents(baoCunBean.getHuoTiFZ() + "", null);
                huoTiFZDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            baoCunBean.setHuoTiFZ(Integer.valueOf(huoTiFZDialog.getFaZhi()));
                            baoCunBeanDao.put(baoCunBean);
                            huoTiFZDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                huoTiFZDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        huoTiFZDialog.dismiss();
                    }
                });
                DiaLogUtil.init(huoTiFZDialog);
                huoTiFZDialog.show();

                break;
            case R.id.rl6:

                startActivity(new Intent(SheZhiActivity2.this, SettingActivity.class));

                break;
            case R.id.rl7:
                final XiuGaiRuKuFZDialog dialog = new XiuGaiRuKuFZDialog(SheZhiActivity2.this);
                dialog.setContents(baoCunBean.getRuKuFaceSize() + "", baoCunBean.getRuKuMoHuDu() + "");
                dialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            baoCunBean.setRuKuFaceSize(Integer.valueOf(dialog.getFZ()));
                            baoCunBean.setRuKuMoHuDu(Float.valueOf(dialog.getMoHuDu()));
                            baoCunBeanDao.put(baoCunBean);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                DiaLogUtil.init(dialog);
                dialog.show();
                break;
            case R.id.rl8:
                //修改设置密码

                final XiuGaiMiMaDialog diZhiDialog2 = new XiuGaiMiMaDialog(SheZhiActivity2.this);
                diZhiDialog2.setCanceledOnTouchOutside(false);
                diZhiDialog2.setContents(baoCunBean.getMima()+"", "修改设置密码");
                diZhiDialog2.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (Integer.valueOf(diZhiDialog2.getUrl()).equals(Integer.valueOf(diZhiDialog2.getUrl2()))){
                                baoCunBean.setMima(Integer.valueOf(diZhiDialog2.getUrl()));
                                baoCunBeanDao.put(baoCunBean);
                                diZhiDialog2.dismiss();
                            }else {
                                Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "两次密码不一致", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "密码非数字", TastyToast.LENGTH_LONG, TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();
                        }

                    }
                });
                diZhiDialog2.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog2.dismiss();
                    }
                });
                DiaLogUtil.init(diZhiDialog2);
                diZhiDialog2.show();


                break;
            case R.id.rl13:
                //修改门禁密码

                final XiuGaiMiMaDialog diZhiDialog3 = new XiuGaiMiMaDialog(SheZhiActivity2.this);
                diZhiDialog3.setCanceledOnTouchOutside(false);
                diZhiDialog3.setContents(baoCunBean.getMima2()+"", "修改门禁密码");
                diZhiDialog3.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (Integer.valueOf(diZhiDialog3.getUrl()).equals(Integer.valueOf(diZhiDialog3.getUrl2()))){
                                baoCunBean.setMima2(Integer.valueOf(diZhiDialog3.getUrl()));
                                baoCunBeanDao.put(baoCunBean);
                                diZhiDialog3.dismiss();
                            }else {
                                Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "两次密码不一致", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "密码非数字", TastyToast.LENGTH_LONG, TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();
                        }

                    }
                });
                diZhiDialog3.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog3.dismiss();
                    }
                });
                DiaLogUtil.init(diZhiDialog3);
                diZhiDialog3.show();


                break;
            case R.id.rl14:
                //公司名
                final XiuGaigGSMDialog diZhiDialogs = new XiuGaigGSMDialog(SheZhiActivity2.this);
                diZhiDialogs.setCanceledOnTouchOutside(false);
                diZhiDialogs.setContents(baoCunBean.getWenzi1(), null);
                diZhiDialogs.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setWenzi1(diZhiDialogs.getUrl());
                        baoCunBeanDao.put(baoCunBean);
                        diZhiDialogs.dismiss();
                    }
                });
                diZhiDialogs.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialogs.dismiss();
                    }
                });

                DiaLogUtil.init(diZhiDialogs);
                diZhiDialogs.show();


                break;

            case R.id.rl9:

                startActivity(new Intent(SheZhiActivity2.this, YuLanActivity.class));

                break;
            case R.id.rl15:

                final XiuGaigGSMDialog xiuGaigGSMDialog=new XiuGaigGSMDialog(SheZhiActivity2.this);
                xiuGaigGSMDialog.setContents(baoCunBean.getWenzi1(),null);
                xiuGaigGSMDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baoCunBean.setWenzi1(xiuGaigGSMDialog.getUrl());
                        baoCunBeanDao.put(baoCunBean);
                        xiuGaigGSMDialog.dismiss();
                    }
                });
                xiuGaigGSMDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        xiuGaigGSMDialog.dismiss();
                    }
                });
                xiuGaigGSMDialog.show();

//                zLoadingDialog = new ZLoadingDialog(SheZhiActivity2.this);
//
//                zLoadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
//                        .setLoadingColor(Color.parseColor("#0d2cf9"))//颜色
//                        .setHintText("导出中...")
//                        .setHintTextSize(16) // 设置字体大小 dp
//                        .setHintTextColor(Color.WHITE)  // 设置字体颜色
//                        .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
//                        .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
//                        .show();
//
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<DaKaBean> daKaBeanList=daKaBeanBox.getAll();
//                        if (daKaBeanList.size()==0){
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    zLoadingDialog.dismiss();
//                                    EventBus.getDefault().post("暂无数据可以导出");
//                                }
//                            });
//
//                        }
//                        SystemClock.sleep(2000 );
//                        File file = new File(MyApplication.SDPATH+File.separator+DateUtils.timeHore(System.currentTimeMillis()+"")+".xls");
//                        //文件夹是否已经存在
//                        if (file.exists()) {
//                            file.delete();
//                        }
//                        String[] title = {"id", "姓名", "部门", "人员类型", "时间"};
//                        String fileName = file.toString();
//                        ExcelUtil.initExcel(fileName, title);
//                        ExcelUtil.writeObjListToExcel(daKaBeanList, fileName, SheZhiActivity2.this,daKaBeanBox);
//
//                    }
//                }).start();


                break;
            case R.id.rl16:

                startActivity(new Intent(SheZhiActivity2.this, UserListActivity.class));

                break;
            case R.id.rl17:
                rl17.setEnabled(false);

                if (usbPath!=null){
                    ToastUtils.getInstances().showDialog("获取图片","获取图片",0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<String> strings=new ArrayList<>();
                            FileUtil.getAllFiles(usbPath+ File.separator+"入库照片",strings);
                           // FacePassHandler facePassHandler=MyApplication.myApplication.getFacePassHandler();
                          //  Log.d("SheZhiActivity", "facePassHandler:" + facePassHandler);
                            if (paAccessControl==null)
                                return;
                            int size=strings.size();
                            if (size==0){
                                EventBus.getDefault().post("未找到入库图片");
                            }
                            for (int i=0;i<size;i++){
                                try {
                                    String sp=strings.get(i);
                                    String name="";
                                    String names= sp.substring(sp.lastIndexOf("/")+1,sp.length());
                                    name=names.replace(".jpg","")
                                            .replace(".png","")
                                            .replace(".jpeg","");


                                    long ididiid=System.currentTimeMillis();
                                    BitmapUtil.saveBitmapToSD(BitmapFactory.decodeFile(sp), MyApplication.SDPATH3, ididiid + ".png");
                                    PaAccessDetectFaceResult detectResult = paAccessControl.
                                            detectFaceByFile(MyApplication.SDPATH3+File.separator+ididiid+".png");

                                    if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
                                       // Log.d("TSXXChuLi", "detectResult.message:" + detectResult.message);
                                        //先查询有没有
                                        try {
//                                            PaAccessFaceInfo face = paAccessControl.queryFaceById(ididiid+"");
//                                            if (face!=null){
//                                                Log.d("TSXXChuLi", "删除已有的人脸:" + paAccessControl.deleteFaceById(face.faceId));
//                                                Subject subject = subjectBox.query().equal(Subject_.teZhengMa, ididiid).build().findUnique();
//                                                if (subject!=null)
//                                                    subjectBox.remove(subject);
//                                            }
                                            paAccessControl.addFace(ididiid+"", detectResult.feature, MyApplication.GROUP_IMAGE);

                                            Subject subject = new Subject();
                                            subject.setTeZhengMa(ididiid+""); //人员id==图片id
                                            subject.setName(ididiid+"");
                                            subject.setPeopleType("员工");
                                            subject.setId(ididiid);
                                            subjectBox.put(subject);
                                            Log.d("MyReceiver", "单个员工入库成功");


                                        } catch(Exception e){
                                            e.printStackTrace();

                                        }
                                    }else {
                                        shibai++;
                                        stringBuilder2.append("入库添加图片失败:")
                                                .append("姓名:")
                                                .append(name).append("时间:")
                                                .append(DateUtils.time(System.currentTimeMillis() + ""))
                                                .append("\n");
                                    }

                                    ToastUtils.getInstances().setDate("入库中"+((float)i/(float) size)*100f+"%",0,"失败了:"+shibai);

                                } catch (Exception e) {
                                   // e.printStackTrace();
                                    stringBuilder2.append("入库添加图片失败:")
                                            .append("姓名:")
                                            .append(strings.get(i)).append("时间:")
                                            .append(DateUtils.time(System.currentTimeMillis() + ""))
                                            .append("错误码:").append(e.getMessage()).append("\n");
                                }

                            }
                            String ss=stringBuilder2.toString();

                            if (ss.length()>0){

                                try {
                                    FileUtil.savaFileToSD("失败记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",ss);
                                    ToastUtils.getInstances().setDate("入库完成",0,"有失败的记录,已保存到本地根目录");
                                    stringBuilder2.delete(0, stringBuilder2.length());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }else {
                                if (shibai!=-1)
                                ToastUtils.getInstances().setDate("入库完成",0,"全部入库成功，没有失败记录");
                            }

                        }

                    }).start();

                }else {
                    EventBus.getDefault().post("请先拔插一下U盘");
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!SheZhiActivity2.this.isFinishing())
                                rl17.setEnabled(true);
                            }
                        });

                    }
                }).start();
                break;
            case R.id.daochu:
                isT=false;
                kaiPing();
                finish();
                break;
            case R.id.rl28:
                //选择城市
                //if (options1Items.size() > 0 ) {
                    showPickerView();
//                } else {
//                    Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "城市数据准备中...请稍后", TastyToast.LENGTH_LONG, TastyToast.INFO);
//                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                    tastyToast.show();
//                }
                break;
        }
    }

    private void kaiPing(){
        Intent intent = new Intent();
        intent.setAction("LYD_SHOW_NAVIGATION_BAR");
        intent.putExtra("type",1);
        this.sendBroadcast(intent);
        sendBroadcast(new Intent("com.android.internal.policy.impl.showNavigationBar"));
        sendBroadcast(new Intent("com.android.systemui.statusbar.phone.statusopen"));
    }


    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
        if (isT){
            RestartAPPTool.restartAPP(SheZhiActivity2.this);
        }
      //  startActivity(new Intent(SheZhiActivity2.this, MainActivity2.class));


    }
//
//    companyId  公司ID
//    name  机器名称
//    machineType 机器类型：1：人证合一面板机  2：纯刷卡
//    machineCode 机器码
//    machineUrl  设备ip地址
//    machineAddress  设备位置
    //绑定
    private void link_uplodexiazai(String name,String leixing,String weizhi){
        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient= new OkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("companyId","DG001")
                .add("name", name)
                .add("machineType",leixing)
                .add("machineCode", FileUtil.getSerialNumber(this) == null ? FileUtil.getIMSI() : FileUtil.getSerialNumber(this))
                .add("machineUrl", "http://"+Objects.requireNonNull(FileUtil.getIPAddress(getApplicationContext()))+":8090/app")
                .add("machineAddress", weizhi)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi()+"/front/wisdom/app/addDevice");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(SheZhiActivity2.this,"请求失败，请检查地址和网络",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{
                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "注册码"+ss);
					final JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity2.this,jsonObject.get("msg").getAsString(),TastyToast.LENGTH_LONG,TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
					//final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity2.this,"返回数据异常",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                }

            }
        });
    }
    //

    //绑定ic
    private void link_uplodeic(String nameic){
        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient= new OkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody body = new FormBody.Builder()
                .add("companyId","DG001")
                .add("ic_card", nameic)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.header("user-agent","Koala Admin")
                //.post(requestBody)
                //.get()
                .post(body)
                .url("http://192.168.2.175:8980/front/wisdom/app/add_ic_card");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(SheZhiActivity2.this,"请求失败，请检查地址和网络",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.d("AllConnects", "请求成功"+call.request().toString());
                //获得返回体
                try{
                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("AllConnects", "注册码"+ss);
                    final JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity2.this,jsonObject.get("msg").getAsString(),TastyToast.LENGTH_LONG,TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    //final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity2.this,"返回数据异常",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
                }

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (zLoadingDialog!=null){
            if (event.equals("daochujilu")){
                Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "导出成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();
                zLoadingDialog.dismiss();
                zLoadingDialog=null;
                return;
            }
            if (event.equals("daochujiluyc")){
                Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "导出失败", TastyToast.LENGTH_LONG, TastyToast.INFO);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();
                zLoadingDialog.dismiss();
                zLoadingDialog=null;
                return;
            }
        }



        Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, event, TastyToast.LENGTH_LONG, TastyToast.INFO);
        tastyToast.setGravity(Gravity.CENTER, 0, 0);
        tastyToast.show();

    }

//    private void initJsonData() {//解析数据
//
//        /**
//         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
//         * 关键逻辑在于循环体
//         *
//         * */
//        String JsonData = FileUtil.getJson(this, "province.json");//获取assets目录下的json文件数据
//
//        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
//
//        /**
//         * 添加省份数据
//         *
//         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
//         * PickerView会通过getPickerViewText方法获取字符串显示出来。
//         */
//        options1Items = jsonBean;
//
//        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
//            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
//
//            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
//                String CityName = jsonBean.get(i).getCityList().get(c).getName();
//                CityList.add(CityName);//添加城市
//
//                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//
//                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
//                if (jsonBean.get(i).getCityList().get(c).getArea() == null
//                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
//                    City_AreaList.add("");
//                } else {
//
//                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
//                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
//
//                        City_AreaList.add(AreaName);//添加该城市所有地区数据
//                    }
//                }
//                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
//            }
//
//            /**
//             * 添加城市数据
//             */
//            options2Items.add(CityList);
//
//            /**
//             * 添加地区数据
//             */
//            options3Items.add(Province_AreaList);
//        }
//    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return detail;
    }


    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                final String tx = options1Items.get(options1).getPickerViewText();
                chengshi.setText(tx);
                baoCunBean.setDangqianChengShi2(tx);
                baoCunBeanDao.put(baoCunBean);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        String bidui = tx;
//                        List<ChengShiIDBean> shiIDBeanList = chengShiIDBeanBox.getAll();
//                        int size = shiIDBeanList.size();
//                        for (int i = 0; i < size; i++) {
//                            ChengShiIDBean bean = shiIDBeanList.get(i);
//                            if (bidui.contains(bean.getProvince()) && bidui.contains(bean.getCity()) && bidui.contains(bean.getDistrict())) {
//                                baoCunBean.setDangqianChengShi(bean.getId());
//                                baoCunBeanDao.put(baoCunBean);
//                                Log.d("SheZhiActivity", "找到了城市id");
//                                break;
//                            }
//
//                        }
//                        if (baoCunBean.getDangqianChengShi() == null) {
//                            baoCunBean.setDangqianChengShi("1");
//                            baoCunBeanDao.put(baoCunBean);
//                        }
                    }
                }).start();

                //  Toast.makeText(SheZhiActivity.this, options3Items.get(options1).get(options2).get(options3), Toast.LENGTH_SHORT).show();
            }
        })
                .setTitleText("机器选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(30)
                .build();

        pvOptions.setPicker(options1Items);//一级选择器
    //    pvOptions.setPicker(options1Items, options2Items);//二级选择器
     //   pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public static class UsbBroadCastReceiver extends BroadcastReceiver {

        public UsbBroadCastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null && intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                usbPath = intent.getData().getPath();
                List<String> sss = FileUtil.getMountPathList();
                int size = sss.size();
                for (int i = 0; i < size; i++) {

                    if (sss.get(i).contains(usbPath)) {
                        usbPath = sss.get(i);
                    }

                }
                EventBus.getDefault().post(usbPath);

                Log.d("UsbBroadCastReceiver", usbPath);
            }


        }
    }



}
