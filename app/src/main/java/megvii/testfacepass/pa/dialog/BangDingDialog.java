package megvii.testfacepass.pa.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import megvii.testfacepass.pa.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class BangDingDialog extends Dialog {
   // private TextView title2;
    private Button l1,l2;

    private EditText name,weizhi,leixing;
    public BangDingDialog(Context context) {
        super(context, R.style.dialog_style2);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xiugaidialog_bangding, null);

        leixing= (EditText) mView.findViewById(R.id.leixing);
        name= (EditText) mView.findViewById(R.id.name);
        weizhi= (EditText)mView.findViewById(R.id.weizhi);


        l1= (Button)mView. findViewById(R.id.queren);
        l2= (Button) mView.findViewById(R.id.quxiao);

        super.setContentView(mView);
    }





    public void setContents(String ss1, String ss2,String ss3){
       if (ss1!=null)
           name.setText(ss1);
        if (ss2!=null)
            leixing.setText(ss2);
        if (ss3!=null)
            weizhi.setText(ss3);

    }

    public String getName(){
        return name.getText().toString().trim();
    }
    public String getWeizhi(){
        return weizhi.getText().toString().trim();
    }
    public String getLeiXing(){
        return leixing.getText().toString().trim();
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

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnQueRenListener(View.OnClickListener listener){
        l1.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setQuXiaoListener(View.OnClickListener listener){
        l2.setOnClickListener(listener);
    }


}
