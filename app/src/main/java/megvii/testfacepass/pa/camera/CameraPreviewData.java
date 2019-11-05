package megvii.testfacepass.pa.camera;

/**
 * Created by linyue on 16/1/3.
 */
public class CameraPreviewData {
    public byte[] nv21Data;

    public int width;

    public int height;

    public int rotation;

    public int front;

    public CameraPreviewData(byte[] nv21Data, int width, int height, int rotation, int front) {
        super();
        this.nv21Data = nv21Data;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.front = front;
    }
}
