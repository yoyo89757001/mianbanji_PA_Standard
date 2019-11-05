package megvii.testfacepass.pa.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import megvii.testfacepass.pa.R;
import megvii.testfacepass.pa.ui.SheZhiActivity2;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class MiMaDialog4 extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t0,fanhui,chongzhi,tt1,tt2,tt3,tt5,tt6,tt4;
    private LinearLayout queding;
    private StringBuilder builder=new StringBuilder();
    private int mima;
   // private MediaPlayer mMediaPlayer;
    private List<TextView> textViewList=new ArrayList<>();
    private SoundPool soundPool;
    //定义一个HashMap用于存放音频流的ID
    private HashMap<Integer, Integer> musicId= new HashMap<>();

    public MiMaDialog4(Context context, int mima) {
        super(context, R.style.dialog_mima);
        this.context=context;
        this.mima=mima;
        Log.d("MiMaDialog", "mima:" + mima);
        //初始化soundPool,设置可容纳12个音频流，音频流的质量为5，
        AudioAttributes abs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build() ;
        soundPool =  new SoundPool.Builder()
                .setMaxStreams(10)   //设置允许同时播放的流的最大值
                .setAudioAttributes(abs)   //完全可以设置为null
                .build() ;
        //通过load方法加载指定音频流，并将返回的音频ID放入musicId中

        musicId.put(1, soundPool.load(context, R.raw.didi, 1));
       // musicId.put(2, soundPool.load(this, R.raw.deng2, 1));
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.mima_item4, null);
        t1=mView.findViewById(R.id.t1);
        t1.setOnClickListener(this);
        t2=mView.findViewById(R.id.t2);
        t2.setOnClickListener(this);
        t3=mView.findViewById(R.id.t3);
        t3.setOnClickListener(this);
        t4=mView.findViewById(R.id.t4);
        t4.setOnClickListener(this);
        t5=mView.findViewById(R.id.t5);
        t5.setOnClickListener(this);
        t6=mView.findViewById(R.id.t6);
        t6.setOnClickListener(this);
        t7=mView.findViewById(R.id.t7);
        t7.setOnClickListener(this);
        t8=mView.findViewById(R.id.t8);
        t8.setOnClickListener(this);
        t9=mView.findViewById(R.id.t9);
        t9.setOnClickListener(this);
        t0=mView.findViewById(R.id.t0);
        t0.setOnClickListener(this);
        tt1=mView.findViewById(R.id.tt1);
        tt2=mView.findViewById(R.id.tt2);
        tt3=mView.findViewById(R.id.tt3);
        tt4=mView.findViewById(R.id.tt4);
        tt5=mView.findViewById(R.id.tt5);
        tt6=mView.findViewById(R.id.tt6);

        textViewList.add(tt1);
        textViewList.add(tt2);
        textViewList.add(tt3);
        textViewList.add(tt4);
        textViewList.add(tt5);
        textViewList.add(tt6);

        fanhui=mView.findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        chongzhi=mView.findViewById(R.id.chongzhi);
        chongzhi.setOnClickListener(this);
        queding=mView.findViewById(R.id.queding);
        queding.setOnClickListener(this);

        super.setContentView(mView);
    }



    private void bofang(){

        soundPool.play(musicId.get(1),1,1, 0, 0, 1);

    }


    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.t1: {
                bofang();
                if (builder.length()<6){
                    builder.append("1");
                    settext(builder.toString());
                }
                break;
            }
            case R.id.t2: {
                bofang();
                if (builder.length()<6){
                    builder.append("2");
                    settext(builder.toString());
                }
                break;
            }
            case R.id.t3: {
                bofang();
                if (builder.length()<6){
                    builder.append("3");
                    settext(builder.toString());
                }

                break;
            }
            case R.id.t4: {
                bofang();
                if (builder.length()<6){
                    builder.append("4");
                    settext(builder.toString());
                }
                break;
            }
            case R.id.t5: {
                bofang();
                if (builder.length()<6){
                    builder.append("5");
                    settext(builder.toString());
                }
                break;
            }
            case R.id.t6: {
                bofang();
                if (builder.length()<6){
                    builder.append("6");
                    settext(builder.toString());
                }
                break;
            }
            case R.id.t7: {
                bofang();
                if (builder.length()<6){
                    builder.append("7");
                    settext(builder.toString());
                }

                break;
            }
            case R.id.t8: {
                bofang();
                if (builder.length()<6){
                    builder.append("8");
                    settext(builder.toString());
                }

                break;
            }
            case R.id.t9: {
                bofang();
                if (builder.length()<6){
                    builder.append("9");
                    settext(builder.toString());
                }

                break;
            }
            case R.id.queding: {
                bofang();
                //确定
                if (builder.toString().equals(mima+"")){
                    context.startActivity(new Intent(context, SheZhiActivity2.class));
                    EventBus.getDefault().post("guanbimain");
                    dismiss();
                }else {
                    builder.delete(0,builder.length());
                    EventBus.getDefault().post("密码错误");
                    for (int i=0;i<6;i++){
                        textViewList.get(i).setText("");
                    }
                }

                break;
            }
            case R.id.t0: {
                bofang();
                if (builder.length()<6){
                    builder.append("0");
                    settext(builder.toString());
                }
                break;
            }
            case R.id.chongzhi: {
                bofang();
                //重置
                if (builder.length() > 0){
                    builder.delete(0, builder.length());
                    for (int i=0;i<6;i++){
                        textViewList.get(i).setText("");
                    }
                }

                Log.d("MiMaDialog", builder.toString()+"ppp");

                break;
            }
             case R.id.fanhui:
                 MiMaDialog4.this.dismiss();
                    break;

            }
        }

        private void settext(String s){
            char ss[]=s.toCharArray();
            int len=ss.length;
            for (int i=0;i<len;i++){
               textViewList.get(i).setText(String.valueOf(ss[i]));
            }

        }


//    /**
//     * 确定键监听器
//     * @param listener
//     */
//    public void setOnQueRenListener(View.OnClickListener listener){
//        l1.setOnClickListener(listener);
//    }
//    /**
//     * 取消键监听器
//     * @param listener
//     */
//    public void setQuXiaoListener(View.OnClickListener listener){
//        l2.setOnClickListener(listener);
//    }


}
