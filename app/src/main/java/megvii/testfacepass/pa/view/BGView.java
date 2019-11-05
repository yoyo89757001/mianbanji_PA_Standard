package megvii.testfacepass.pa.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import megvii.testfacepass.pa.R;

public class BGView extends View {
    private int mtop=0;
    private Rect rectbg=null;
    private Rect rectQ=null;
    private Paint paintbg=null,paintY=null;
    private PorterDuffXfermode porterDuffXfermode=null;
    private int radius=0;
    private Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.quan_dian);
    private int arg=0;
    private int sud=30;
    private Shader mShader=null;

    public BGView(Context context) {
        super(context);
        init();
    }

    public BGView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BGView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectbg.set(0,0,getWidth(),getHeight());
        radius=(int)(getWidth()*0.24f);
        mtop=(int)(getHeight()*0.06f);
        rectQ.set(rectbg.centerX()-((int)(getWidth()*0.24f)+20),(rectbg.centerY()-mtop)-((int)(getWidth()*0.24f)+20),
                rectbg.centerX()+((int)(getWidth()*0.24f)+20),(rectbg.centerY()-mtop)+((int)(getWidth()*0.24f)+20));
        mShader = new LinearGradient(0,0,getWidth(),getHeight(),
                new int[] {Color.argb(255,0,0,0),
                        Color.argb(200,0,0,0),
                        Color.argb(100,0,0,0)},
                null,Shader.TileMode.REPEAT);

        paintbg.setShader(mShader);

      Log.d("BGView", rectQ.left+" "+rectQ.top+"  "+rectQ.right+"  "+rectQ.bottom);
    }

    private void init(){
        porterDuffXfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        rectbg=new Rect();
        rectQ=new Rect();


        paintbg=new Paint();
      //  paintbg.setARGB(160,0,0,0);
        paintbg.setStyle(Paint.Style.FILL);
        paintbg.setAntiAlias(true);
        paintbg.setXfermode(null);

        paintY=new Paint();
        paintY.setARGB(0,0,0,0);
        paintY.setStyle(Paint.Style.FILL);
        paintY.setAntiAlias(true);
        paintY.setXfermode(porterDuffXfermode);

    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectbg.width()<=0 || rectQ.width()<=0)
            return;
        arg=arg+sud;
        if (arg>=360)
        arg=0;

        canvas.drawRect(rectbg,paintbg);
        canvas.drawCircle(rectbg.centerX(),rectbg.centerY()-mtop,radius,paintY);
        canvas.save();
        canvas.rotate(arg,rectQ.centerX(),rectQ.centerY());
        canvas.drawBitmap(bitmap,null,rectQ,null);
        canvas.restore();
    }


}
