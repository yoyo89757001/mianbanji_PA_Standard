package megvii.testfacepass.pa.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import megvii.testfacepass.pa.R;
import megvii.testfacepass.pa.ui.SheZhiActivity2;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class MiMaDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    private StringBuilder builder=new StringBuilder();
    private int mima;
    private MediaPlayer mMediaPlayer;

    public MiMaDialog(Context context,int mima) {
        super(context, R.style.dialog_style44);
        this.context=context;
        this.mima=mima;
        Log.d("MiMaDialog", "mima:" + mima);

        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.mima_item, null);
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
        t10=mView.findViewById(R.id.t10);
        t10.setOnClickListener(this);
        t11=mView.findViewById(R.id.t11);
        t11.setOnClickListener(this);
        t12=mView.findViewById(R.id.t12);
        t12.setOnClickListener(this);



        super.setContentView(mView);
    }



    private void bofang(){

        mMediaPlayer=MediaPlayer.create(context, R.raw.didi);
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//              //  mMediaPlayer.release();
//              //  mMediaPlayer=null;
//            }
//        });
        mMediaPlayer.start();

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
                builder.append("1");

                break;
            }
            case R.id.t2: {
                bofang();
                builder.append("2");
                break;
            }
            case R.id.t3: {
                bofang();
                builder.append("3");
                break;
            }
            case R.id.t4: {
                bofang();
                builder.append("4");
                break;
            }
            case R.id.t5: {
                bofang();
                builder.append("5");
                break;
            }
            case R.id.t6: {
                bofang();
                builder.append("6");
                break;
            }
            case R.id.t7: {
                bofang();
                builder.append("7");
                break;
            }
            case R.id.t8: {
                bofang();
                builder.append("8");
                break;
            }
            case R.id.t9: {
                bofang();
                builder.append("9");
                break;
            }
            case R.id.t10: {
                bofang();
                //确定
                if (builder.toString().equals(mima+"")){
                    context.startActivity(new Intent(context,SheZhiActivity2.class));
                    EventBus.getDefault().post("guanbimain");
                    dismiss();
                }else {
                    builder.delete(0,builder.length());
                    EventBus.getDefault().post("密码错误");
                }

                break;
            }
            case R.id.t11: {
                bofang();
                builder.append("0");
                break;
            }
            case R.id.t12: {
                bofang();
                //删除
                if (builder.length()>1)
                builder.deleteCharAt(builder.length()-1);
                //Log.d("MiMaDialog", builder.toString());

                break;
            }
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
