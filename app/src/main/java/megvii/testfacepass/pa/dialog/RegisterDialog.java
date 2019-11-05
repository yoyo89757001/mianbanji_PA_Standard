package megvii.testfacepass.pa.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import megvii.testfacepass.pa.R;


/**
 * Created by zhanqiang545 on 18/4/23.
 */

@SuppressLint("ValidFragment")
public class RegisterDialog extends DialogFragment
        implements DialogInterface.OnKeyListener {

    private View mDialog;
    private TextView tvConfirm;
    private TextView tvReStart;
    private ImageView ivExit;
    private Bitmap bitmap;
    private EditText etId;

    public RegisterDialog(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        getDialog().setOnKeyListener(this);
        mDialog = inflater.inflate(R.layout.dialog_register, container);
        tvConfirm = mDialog.findViewById(R.id.tv_confirm);
        tvReStart = mDialog.findViewById(R.id.tv_re_start);
        ivExit = mDialog.findViewById(R.id.iv_exit);

        etId = mDialog.findViewById(R.id.et_id);

        ImageView ivFace = mDialog.findViewById(R.id.iv_face);
        ivFace.setImageBitmap(bitmap);

        initEvent();
        return mDialog;
    }



    private void initEvent() {
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnConfirmClickListener(OnConfirmClickListener);
                if (OnConfirmClickListener != null) {
                    String id = etId.getText().toString();
                    if(TextUtils.isEmpty(id)){
                        id = System.currentTimeMillis()+"";
                    }
                    OnConfirmClickListener.onConfirmClick(id);
                }
                dismiss();
            }
        });

        tvReStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setOnReDetectClickListener(onReDetectListener);
                if(onReDetectListener!=null){
                    onReDetectListener.onReDetectClick();
                }
                dismiss();
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnExitClickListener(onExitListener);
                if(onExitListener!=null){
                    onExitListener.onExitClick();
                }
                dismiss();
            }
        });
    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------
    private OnConfirmClickListener OnConfirmClickListener;
    public interface OnConfirmClickListener {
        void onConfirmClick(String id);
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        OnConfirmClickListener = listener;
    }

    //--------------------------------------------------------------------
    private OnReDetectClickListener onReDetectListener;

    public interface OnReDetectClickListener {
        void onReDetectClick();
    }

    public void setOnReDetectClickListener(OnReDetectClickListener listener) {
        onReDetectListener = listener;
    }

    //--------------------------------------------------------------------
    private OnExitClickListener onExitListener;

    public interface OnExitClickListener {
        void onExitClick();
    }

    public void setOnExitClickListener(OnExitClickListener listener) {
        onExitListener = listener;
    }

}
