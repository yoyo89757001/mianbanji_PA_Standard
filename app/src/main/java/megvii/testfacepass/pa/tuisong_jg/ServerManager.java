package megvii.testfacepass.pa.tuisong_jg;

import android.util.Log;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import megvii.testfacepass.pa.MyApplication;

public class ServerManager {

    private Server mServer;
    private MyServeInterface myServeInterface;


    public void setMyServeInterface(MyServeInterface myServeInterface) {
        this.myServeInterface = myServeInterface;
    }

    /**
     * Create server.
     */
    public ServerManager(final String ip, final int port) {

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        mServer = AndServer.serverBuilder(MyApplication.myApplication)
                .inetAddress(inetAddress)
                .port(port)
                .timeout(12, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        myServeInterface.onStarted(ip+":"+port);
                    }

                    @Override
                    public void onStopped() {
                        myServeInterface.onStopped();
                    }

                    @Override
                    public void onException(Exception e) {
                        myServeInterface.onException(e);
                    }
                })
                .build();
    }

    /**
     * Start server.
     */
    public void startServer() {
        if (mServer.isRunning()) {
            // TODO The server is already up.
        } else {
            mServer.startup();
        }
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        if (mServer.isRunning()) {
            mServer.shutdown();
        } else {
            Log.w("AndServer", "The server has not started yet.");
        }
    }

}
