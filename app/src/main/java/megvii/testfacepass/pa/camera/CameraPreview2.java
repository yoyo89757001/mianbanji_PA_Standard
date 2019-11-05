package megvii.testfacepass.pa.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by linyue on 16/1/2.
 */
public class CameraPreview2 extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera = null;

    private CameraPreviewListener2 listener;

    private float scaleW = 1;

    private float scaleH = 1;

    private int hight;

    public CameraPreview2(Context context) {
        super(context);
        init();
    }

    public CameraPreview2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraPreview2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    void setCamera(Camera camera) {
        this.camera = camera;
        restartPreview(getHolder());
    }

    private void restartPreview(SurfaceHolder holder) {
        if (camera != null) {
            if (holder.getSurface() == null) {
                return;
            }

            try {
                camera.stopPreview();
            } catch (Exception e) {
            }

            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
//                camera.startFaceDetection();
                if (listener != null) {
                    listener.onStartPreview();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setHight(int hight){
        this.hight=hight;
    }

    public void setScale(float scaleW, float scaleH) {
        this.scaleW = scaleW;
        this.scaleH = scaleH;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        restartPreview(holder);

//        RelativeLayout.LayoutParams  pp= (RelativeLayout.LayoutParams) this.getLayoutParams();
//        pp.height=(int)(hight*0.8);
//        this.setLayoutParams(pp);
//        this.invalidate();

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        restartPreview(holder);
    }

    public void setListener(CameraPreviewListener2 listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) (width * scaleW), (int) (height * scaleH));
    }

    public interface CameraPreviewListener2 {
        public void onStartPreview();
    }
}