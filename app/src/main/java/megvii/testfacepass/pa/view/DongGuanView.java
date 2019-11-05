package megvii.testfacepass.pa.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;



import androidx.annotation.Nullable;

import megvii.testfacepass.pa.R;

public class DongGuanView extends View {

  //  private Bitmap kuang= BitmapFactory.decodeResource(getResources(), R.drawable.mianbji2106);
  //  private Bitmap xian= BitmapFactory.decodeResource(getResources(), R.drawable.xian111);
    private Bitmap kuangBG= BitmapFactory.decodeResource(getResources(), R.drawable.mianbji209);
    private int width,hight;
    private Paint paint,paint2;
    private Rect tcBGRect,touxiangR;
    private int Y=0;
    //private ValueAnimator anim;
    private boolean isTC;
    private Bitmap touxiang=null;
    private String name;
    //private int frameHeight,frameWidth,w2,h2;
  //  private String quanxian="无进入权限";
   // private String shuaka="请刷ID卡";
    private String bumen;

    public DongGuanView(Context context) {
        super(context);
        intie();
    }

    public DongGuanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intie();
    }

    public DongGuanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intie();
    }


   private void intie(){
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(50);
        paint.setColor(Color.parseColor("#ffffff"));
       paint2=new Paint();
       paint2.setAntiAlias(true);
       paint2.setTextSize(40);
       paint2.setColor(Color.parseColor("#ffe77a"));
    }

    public  void setwh(int w,int h){
        this.width=w;
        this.hight=h;
        Log.d("DongGuanView", "w:" + w);
        Log.d("DongGuanView", "h:" + h);
       // int dou = (int) (w*0.05);
      //  int a=w/2;
       // int b=h/2;
       // rectkuang=new Rect(a-dou*3,b-dou*8,a+dou*3,b*2);
       // Log.d("DongGuanView", rectkuang.toString());
       // rectxian=new Rect(a-dou*3,b-dou*8,a+dou*3,b-dou*8+10);
//        anim = ValueAnimator.ofInt(b-dou*8, b-dou*2);
//        anim.setDuration(1000);
//        anim.setRepeatCount(-1);
//        anim.setRepeatMode(ValueAnimator.REVERSE);
//        Interpolator interpolator = new LinearInterpolator();
//        anim.setInterpolator(interpolator);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Y = (int) animation.getAnimatedValue();
//               // rectxian.top=Y-5;
//               // rectxian.bottom=Y+5;
//                // 获得改变后的值
//                	//		System.out.println(Y);
//                // 输出改变后的值
//                // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
//             //   view1.setScaleX(currentValue);
//             //   view1.setScaleY(currentValue);
//                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
//               invalidate();
//            }
//        });
       double siez = (h/100.0);

       tcBGRect=new Rect((int) (w*0.12), (int) (siez*80), (int) (w-(w*0.12)), (int) (siez*100));
       touxiangR=new Rect((int) (w*0.22),(int)(tcBGRect.centerY()-siez*6),(int) (w*0.38),(int)(tcBGRect.centerY()+siez*7));

//        ValueAnimator  anim2 = ValueAnimator.ofFloat(0, 1.0f);
//        anim2.setDuration(1000);
//        anim2.setRepeatCount(-1);
//        anim2.setRepeatMode(ValueAnimator.REVERSE);
//        Interpolator interpolator2 = new LinearInterpolator();
//        anim2.setInterpolator(interpolator2);
//        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float f = (float) animation.getAnimatedValue();
//
//
//            }
//        });

    }



    public void setTC(Bitmap bitmap,String name,String bumen){
        isTC=true;
        if (bitmap==null){
            kuangBG= BitmapFactory.decodeResource(getResources(), R.drawable.erroy_bg);
        }
        touxiang=bitmap;
        this.name=name;
        this.bumen=bumen;
        if (this.bumen==null){
            this.bumen="暂无";
        }
       // 得到使用该paint写上text的时候,像素为多少
       // float textLength = paint.measureText(name);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        if (rectkuang != null && Y != 0){
//         //   Log.d("DongGuanView", rectkuang.toString());
//            canvas.drawBitmap(kuang, null, rectkuang, paint);
//
//        }
//
//        if (rectxian != null && Y != 0) {
//            canvas.drawBitmap(xian, null, rectxian, paint);
//        }

        if (isTC){//绘制弹出
            if (touxiang!=null && bumen!=null){
                canvas.drawBitmap(kuangBG, null, tcBGRect, paint);
                canvas.drawBitmap(touxiang, null, touxiangR, paint);
                canvas.drawText(name,tcBGRect.centerX()+30-name.length()*10,tcBGRect.centerY()-20,paint);
                canvas.drawText(bumen,tcBGRect.centerX()+30-bumen.length()*10,tcBGRect.centerY()+60,paint2);
            }

        }

    }



    public void clera(){
        //anim.pause();
        name=null;
        bumen=null;
        isTC=false;
       // shuaka="请刷ID卡";
        touxiangR.set(0,0,0,0);
      //  rectxian.set(0,0,0,0);
      //  rectkuang.set(0,0,0,0);
        tcBGRect.set(0,0,0,0);
        invalidate();
    }
    public void start(){
     //   anim.start();
      //  int dou = (int) (width*0.05);
       // int a=width/2;
        //int b=hight/2;
        double siez = (hight/100.0);
        tcBGRect=new Rect((int) (width*0.12), (int) (siez*80), (int) (width-(width*0.12)), (int) (siez*100));
        touxiangR=new Rect((int) (width*0.22),(int)(tcBGRect.centerY()-siez*6),(int) (width*0.38),(int)(tcBGRect.centerY()+siez*7));
       // rectkuang=new Rect(a-dou*3,b-dou*8,a+dou*3,b-dou*2);
       // rectxian=new Rect(a-dou*3,b-dou*8,a+dou*3,b-dou*8+10);
        invalidate();
    }

}
