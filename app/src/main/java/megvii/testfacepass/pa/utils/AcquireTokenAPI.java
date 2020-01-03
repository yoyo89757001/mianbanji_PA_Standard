package megvii.testfacepass.pa.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

public class AcquireTokenAPI {

    public void requestToken(String url, String appId, String device, AcquireTokenListener listener) {
        AcquireTokenTask netTask = new AcquireTokenTask(url, appId, device, listener);
        netTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static class AcquireTokenTask extends AsyncTask<String, String, String> {
        private String mUrl;
        private String mAppId;
        private String mDevice;
        private AcquireTokenListener mListener;

        AcquireTokenTask(String url, String appId, String device, AcquireTokenListener listener) {
            this.mUrl = url;
            this.mAppId = appId;
            this.mDevice = device;
            this.mListener = listener;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder responseData = new StringBuilder();
            HttpURLConnection conn = null;
            try {
                URL url = new URL(mUrl);
                switch (url.getProtocol().toLowerCase()) {
                    case "https":
                        conn = (HttpsURLConnection) url.openConnection();
                        ((HttpsURLConnection) conn).getHostnameVerifier();
                        break;
                    case "http":
                        conn = (HttpURLConnection) url.openConnection();
                        break;
                    default:
                        return null;
                }

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);// 设置允许输出

                // 向header里添加信息。
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("appid", mAppId);

                // 添加body内容
                String requestBodyContent = getRequestBodyContent();
                OutputStream os = conn.getOutputStream();
                os.write(requestBodyContent.getBytes());
                os.flush();
                os.close();


                // 获取响应数据
                int code = conn.getResponseCode();
                Log.i("https", "code=" + code);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String retData;

                while ((retData = in.readLine()) != null) {
                    responseData.append(retData);
                }
                in.close();

            } catch (UnknownHostException e) {
                return null;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            if (responseData.toString().isEmpty()) {
                return null;
            }

            return responseData.toString();
        }

        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            if (responseData != null) {
                mListener.onSuccess(responseData);
            } else {
                mListener.onFail();
            }
        }

        private String getRequestBodyContent() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("device", mDevice);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

    }

    public interface AcquireTokenListener {
        void onSuccess(String response);

        void onFail();
    }
}
