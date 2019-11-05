package megvii.testfacepass.pa.tuisong_jg;

public interface MyServeInterface {

    public void onStarted(String ip);
    public void onStopped();
    public void onException(Exception err);
}
