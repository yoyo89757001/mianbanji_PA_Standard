package megvii.testfacepass.pa.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhanqiang545 on 18/4/3.
 */

public class BitmapUtil {

    public final static int CV_ROTATE_90_CLOCKWISE = 0; //Rotate 90 degrees clockwise
    public final static int CV_ROTATE_180 = 1; //Rotate 180 degrees clockwise
    public final static int CV_ROTATE_90_COUNTERCLOCKWISE = 2; //Rotate 270 degrees clockwise
    public final static int CV_ROTATE_360 = 3; //Rotate 270 degrees clockwise

    //获取原bitmap图片
    public static Bitmap getBitmap(byte[] frame, int imageWidth, int imageHeight, int ori) {
        Bitmap bitmap = null;
        try {
            YuvImage image = new YuvImage(frame, ImageFormat.NV21, imageWidth, imageHeight, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, imageWidth, imageHeight), 100, stream);
            bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
            stream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        float rotate;
        if (ori == CV_ROTATE_90_CLOCKWISE) {
            rotate = 90;
        } else if (ori == CV_ROTATE_90_COUNTERCLOCKWISE) {
            rotate = 270;
        } else if (ori == CV_ROTATE_180) {
            rotate = 180;
        } else {
            rotate = 360;
        }
        if (bitmap != null) {
            bitmap = rotateBitmap(bitmap, rotate);
        }
        return bitmap;
    }

    //获取人脸抠图
    public static Bitmap getSmallBitmap(byte[] frame, int x, int y, int w, int h, int width, int height, int ori, Bitmap bitmap) {

        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        // 获得成功的图片
        try {
            if (ori == CV_ROTATE_90_CLOCKWISE ||
                    ori == CV_ROTATE_90_COUNTERCLOCKWISE) {
                width = height;
                height = width;
            }
            RectF rect;
            if (x >= 0 && y >= 0 && w >= 0 && h >= 0) {
                rect = new RectF(x, y, w + x, h + y);
                if (frame.length != 0) {
                    // 扣图扩大1.2 / 0314加float ratio / int nHeight,int nWidth,
                    int nw, nh, xn, yn, x0, y0;
                    float ratio = 1.45f;
                    int x1 = (int) rect.left;
                    int y1 = (int) rect.top;
                    int w1 = (int) rect.width() + x1;
                    int h1 = (int) rect.height() + y1;

                    x0 = (int) (x1 - w1 * (ratio - 1) * 0.5);
                    xn = (int) (x0 + w1 * ratio);
                    if (x0 < 0) {
                        x0 = 0;
                    }
                    if (xn > width - 1) {
                        xn = width - 1;
                    }
                    nw = xn - x0 + 1;

                    y0 = (int) (y1 - h1 * (ratio - 1));
                    yn = (int) (y0 + h1 * (1 + (ratio - 1) * 1.5));
                    if (y0 < 0) {
                        y0 = 0;
                    }
                    if (yn > height - 1) {
                        yn = height - 1;
                    }
                    nh = yn - y0 + 1;
                    return Bitmap.createBitmap(bitmap, (int) x0, (int) y0, (int) nw, (int) nh);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    //图片转为二进制数据
    public static byte[] bitmabToBytes(Bitmap bitmap2) {
      Bitmap bitmap = rotateBitmap(bitmap2,90);
        //将图片转化为位图
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            //将字节数组输出流转化为字节数组byte[]
            return baos.toByteArray();
        } catch (Exception ignored) {
        } finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                Log.d("MainActivity", e.getMessage() + "bitmap转byte异常");
                e.printStackTrace();
            }
        }
        Log.d("MainActivity", "返回空byte[]");
        return new byte[0];
    }

    public static Bitmap getCropBitmap(Bitmap bitmap,int x,int y,int w,int h){

        //图像宽高
        int btimapWidth = bitmap.getWidth();
        int btimapHeight = bitmap.getHeight();

        //超出边界处理
        if(x<0){
            x=0;
        }

        if(y<0){
            y=0;
        }

        if(x+w>btimapWidth){
            w = btimapWidth-x;
        }

        if(y+h>btimapHeight){
            h = btimapHeight-y;
        }

        // 计算人脸中心坐标点
        int x0 = x+w/2;
        int y0 = y+h/2;

        //人脸扩边,宽向外扩1.45倍，高向外扩1.6倍
        int cropFaceW = (int)(w*1.45);
        int cropFaceH = (int)(h*1.45);
        int cropFaceX = x0-cropFaceW/2;
        int cropFaceY = y0-cropFaceH/2;
        //上半部分再扩0.45倍
        cropFaceY = cropFaceY-(int)(h*0.45);
        cropFaceH = cropFaceH+(int)(h*0.45);

        if(cropFaceX<0){
            cropFaceX=0;
        }

        if(cropFaceY<0){
            cropFaceY = 0;
        }

        if(cropFaceX+cropFaceW>btimapWidth){
            cropFaceW = btimapWidth-cropFaceX;
        }

        if(cropFaceY+cropFaceH>btimapHeight){
            cropFaceH = btimapHeight-cropFaceY;
        }

        return Bitmap.createBitmap(bitmap, cropFaceX, cropFaceY, cropFaceW, cropFaceH);
    }


    //旋转bitmap
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

   public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
       try {
           int width = bm.getWidth();
           int height = bm.getHeight();
           Matrix matrix = new Matrix();
           matrix.setRotate(orientationDegree, (float) width / 2, (float) height / 2);
           float targetX = 0;
           float targetY = 0;
           if (orientationDegree == 90 || orientationDegree == 270) {
               if (width > height) {
                   targetX = (float) height / 2 - (float) width / 2;
                   targetY = 0 - targetX;
               } else {
                   targetY = (float) width / 2 - (float) height / 2;
                   targetX = 0 - targetY;
               }
           }
           matrix.postTranslate(targetX, targetY);
           Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);

           Paint paint = new Paint();
           Canvas canvas = new Canvas(bm1);
           canvas.drawBitmap(bm, matrix, paint);

           return bm1;
       } catch (OutOfMemoryError e) {
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {

        if (bitmap != null){

         Matrix m = new Matrix();

         m.postRotate(degress);

         bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                 m, true);

         return bitmap;

         }

         return bitmap;

    }


    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    public static void saveBitmapToSD(Bitmap bm, String filePath, String filename) {
        File dir = new File(filePath);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, filename);
        if (file.exists()){
            file.delete();
            file = new File(dir, filename);
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Bitmap getBitmapFromPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        return bitmap;
    }

}
