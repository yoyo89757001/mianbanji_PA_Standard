//package megvii.testfacepass.pa.tuisong_jg;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Environment;
//import android.os.SystemClock;
//import android.util.Log;
//import android.util.Xml;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.Target;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.liulishuo.filedownloader.BaseDownloadTask;
//import com.liulishuo.filedownloader.FileDownloadListener;
//import com.liulishuo.filedownloader.FileDownloader;
//import com.pingan.ai.access.common.PaAccessControlMessage;
//import com.pingan.ai.access.common.PaAccessDetectConfig;
//import com.pingan.ai.access.entiry.PaAccessFaceInfo;
//import com.pingan.ai.access.manager.PaAccessControl;
//import com.pingan.ai.access.result.PaAccessDetectFaceResult;
//
//
//
//import net.lingala.zip4j.core.ZipFile;
//import net.lingala.zip4j.exception.ZipException;
//import net.lingala.zip4j.model.FileHeader;
//
//import org.greenrobot.eventbus.EventBus;
//import org.xmlpull.v1.XmlPullParser;
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//import io.objectbox.Box;
//
//
//import megvii.testfacepass.pa.MyApplication;
//import megvii.testfacepass.pa.beans.BaoCunBean;
//import megvii.testfacepass.pa.beans.BeiJingBean;
//import megvii.testfacepass.pa.beans.GuanHuai;
//import megvii.testfacepass.pa.beans.LingShiSubject;
//import megvii.testfacepass.pa.beans.LunBoBean;
//import megvii.testfacepass.pa.beans.Subject;
//import megvii.testfacepass.pa.beans.TuiSongBean;
//import megvii.testfacepass.pa.beans.XGBean;
//import megvii.testfacepass.pa.beans.XinXiAll;
//import megvii.testfacepass.pa.beans.XinXiIdBean;
//import megvii.testfacepass.pa.beans.XinXiIdBean_;
//import megvii.testfacepass.pa.dialogall.ToastUtils;
//import megvii.testfacepass.pa.utils.BitmapUtil;
//import megvii.testfacepass.pa.utils.DateUtils;
//import megvii.testfacepass.pa.utils.FileUtil;
//import megvii.testfacepass.pa.utils.GlideCacheUtil;
//import megvii.testfacepass.pa.utils.GsonUtil;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//
//public class TSXXChuLi {
//
//
//    private static final String TAG = "信鸽消息";
//
//    private Box<BaoCunBean> baoCunBeanDao = MyApplication.myApplication.getBaoCunBeanBox();
//    private Box<Subject> subjectBox = MyApplication.myApplication.getSubjectBox();
//    private Box<LunBoBean> lunBoBeanBox = MyApplication.myApplication.getLunBoBeanBox();
//    private static final String group_name = "facepasstestx";
//    private Box<XinXiAll> xinXiAllBox = MyApplication.myApplication.getXinXiAllBox();
//    private Box<XinXiIdBean> xinXiIdBeanBox = MyApplication.myApplication.getXinXiIdBeanBox();
//   // private StringBuilder stringBuilder = null;
//    private StringBuilder stringBuilder2 = new StringBuilder();
//    private StringBuilder stringBuilderId = new StringBuilder();
//    String path2 = null;
//    private int TIMEOUT = 30 * 1000;
//    private Context context;
//    private PaAccessControl paAccessControl;
//    private final String SDPATH = MyApplication.SDPATH;
//    public static boolean isDW = true;
//   // private PaAccessControl facePassHandler = null;
//    private Box<GuanHuai> guanHuaiBox = MyApplication.myApplication.getGuanHuaiBox();
//    private BaoCunBean baoCunBean=null;
//    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .writeTimeout(30000, TimeUnit.MILLISECONDS)
//            .connectTimeout(30000, TimeUnit.MILLISECONDS)
//            .readTimeout(30000, TimeUnit.MILLISECONDS)
////				    .cookieJar(new CookiesManager())
//            //         .retryOnConnectionFailure(true)
//            .build();
//
//    public TSXXChuLi() {
//        baoCunBean=baoCunBeanDao.get(123456L);
//
//    }
//
//    public void setData(XGBean xgBean , Context context, PaAccessControl paAccessControl) {
//        //给后台发送成功收到推送回调
//
//        this.paAccessControl=paAccessControl;
//        this.context=context;
//      //  this.facePassHandler=facePassHandler;
//        JsonObject jsonObject = GsonUtil.parse(xgBean.getXg_msg()).getAsJsonObject();
//        if (jsonObject.get("title").getAsString().equals("人员入库") || jsonObject.get("title").getAsString().equals("访客入库")) {
//            FileDownloader.setup(context);
//            isDW = true;
//            SystemClock.sleep(2200);
//            //baoCunBean.setZhanhuiId(jsonObject.get("content").getAsJsonObject().get("id").getAsInt()+"");
//            //baoCunBean.setGonggao(jsonObject.get("content").getAsJsonObject().get("screenId").getAsInt()+"");
//            //baoCunBeanDao.put(baoCunBean);
//            //Intent intent2=new Intent("gxshipingdizhi");
//            //context.sendBroadcast(intent2);
//            if (stringBuilderId.length() > 0) {
//                stringBuilderId.delete(0, stringBuilderId.length());
//            }
//            path2 = baoCunBean.getHoutaiDiZhi().substring(0, baoCunBean.getHoutaiDiZhi().length() - 5) +
//                    jsonObject.get("url").getAsString();
//            Log.d(TAG, path2);
//            File file = new File(SDPATH);
//            if (!file.exists()) {
//                Log.d(TAG, "file.mkdirs():" + file.mkdirs());
//            }
//            if (isDW) {
//                isDW = false;
//                Log.d(TAG, "进入下载");
//                FileDownloader.getImpl().create(path2)
//                        .setPath(SDPATH + File.separator + System.currentTimeMillis() + ".zip")
//                        .setCallbackProgressTimes(300)
//                        .setMinIntervalUpdateSpeed(400)
//                        .setListener(new FileDownloadListener() {
//                            @Override
//                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                Log.d(TAG, "pending" + soFarBytes);
//                            }
//
//                            @Override
//                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                                //已经连接上
//                                Log.d(TAG, "isContinue:" + isContinue);
//                                showNotifictionIcon( 100, "下载中", "下载入库图片中 " + ((float)soFarBytes/1048576.0f) + "M");
//
//                            }
//
//                            @Override
//                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                Log.d(TAG, "soFarBytes:" + soFarBytes + task.getUrl());
//                                //进度
//                                isDW = false;
//                                if (task.getUrl().equals(path2)) {
//
//                                    ToastUtils.getInstances().setDate("下载中",  100, "下载入库图片中 " + ((float)soFarBytes/1048576.0f) + "M");
//                                    //	showNotifictionIcon(,,);
//                                }
//                            }
//
//                            @Override
//                            protected void blockComplete(BaseDownloadTask task) {
//                                //完成
//                            }
//
//                            @Override
//                            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
//                                //重试
//                                Log.d(TAG, ex.getMessage() + "重试 " + retryingTimes);
//                            }
//
//                            @Override
//                            protected void completed(BaseDownloadTask task) {
//                                //完成整个下载过程
//                                if (task.getUrl().equals(path2)) {
//                                    isDW = true;
//                                    String ss = SDPATH + File.separator + (task.getFilename().substring(0, task.getFilename().length() - 4));
//                                    File file = new File(ss);
//                                    if (!file.exists()) {
//                                        Log.d(TAG, "创建文件状态:" + file.mkdir());
//                                    }
//                                    showNotifictionIcon(0, "解压中", "解压人脸库中");
//                                    jieya(SDPATH + File.separator + task.getFilename(), ss);
//
//                                    Log.d(TAG, "task.isRunning():" + task.isRunning() + task.getFilename());
//
//                                    if (baoCunBean != null && baoCunBean.getZhanghuId() != null)
//                                        link_uplodexiazai();
//                                }
//                            }
//
//                            @Override
//                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//
//                            }
//
//                            @Override
//                            protected void error(BaseDownloadTask task, Throwable e) {
//                                //出错
//                                if (task.getUrl().equals(path2)) {
//                                    isDW = true;
//
//                                    Log.d(TAG, "task.isRunning():" + task.getFilename() + "失败" + e);
//                                }
//                                showNotifictionIcon(0, "下载失败", "" + e);
//                            }
//
//                            @Override
//                            protected void warn(BaseDownloadTask task) {
//                                //在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务
//
//                            }
//                        }).start();
//
//            }
//
//        }
//        Gson gson = new Gson();
//        TuiSongBean renShu = gson.fromJson(jsonObject, TuiSongBean.class);
//
//        gengxingzhuangtai2(renShu.getFailureId());
//
//        //1 新增 2修改//3是删除
//        switch (renShu.getTitle()) {
//            case "单个访客入库":
//                link_dangeFangke(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//            case "单个访客删除":
//                link_dangeFangke(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//            case "单个访客修改":
//                link_dangeFangke(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//            case "单个人员入库"://员工
//
//                link_dangeYuanGong(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//
//                break;
//
//            case "小邮局入库":
//                link_youju(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//            case "关怀入库":
//                link_guanhuai(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//
//            case "底图更新":
//                link_beijing(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//            case "信息推送":
//                link_xinxituisong(renShu.getId(), renShu.getStatus(), renShu.getUrl());
//                break;
//
//
//        }
//
//
//    }
//
//
//    private static void showNotifictionIcon(float p, String title, String contextss) {
//        //Log.d(TAG, "尽量");
//
//        ToastUtils.getInstances().showDialog(title,contextss, (int) p);
//
//    }
//
//    private void jieya(String pathZip, final String path222){
//
//        ZipFile zipFile=null;
//        List fileHeaderList=null;
//        try {
//            // Initiate ZipFile object with the path/name of the zip file.
//            zipFile = new ZipFile(pathZip);
//            zipFile.setFileNameCharset("GBK");
//            fileHeaderList = zipFile.getFileHeaders();
//            // Loop through the file headers
//            Log.d(TAG, "fileHeaderList.size():" + fileHeaderList.size());
//
//            for (int i = 0; i < fileHeaderList.size(); i++) {
//                FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
//                //	FileHeader fileHeader2 = (FileHeader) fileHeaderList.get(0);
//
//                //Log.d(TAG, fileHeader2.getFileName());
//
//                if (fileHeader.getFileName().contains(".xml")){
//                    zipFile.extractFile( fileHeader.getFileName(), path222);
//                    Log.d(TAG, "找到了"+i+"张照片");
//                }
//
//
//                // Various other properties are available in FileHeader. Please have a look at FileHeader
//                // class to see all the properties
//            }
//        } catch (final ZipException e) {
//
//            showNotifictionIcon(0,"解压失败",e.getMessage()+"");
//        }
//        //   UnZipfile.getInstance(SheZhiActivity.this).unZip(zz,trg,zipHandler);
//
//        //拿到XML
//        showNotifictionIcon(0,"解析XML中","解析XML中。。。。");
//        List<String> xmls=new ArrayList<>();
//        final List<String> xmlList= FileUtil.getAllFileXml(path222,xmls);
//        if (xmlList==null || xmlList.size()==0){
//            showNotifictionIcon(0,"没有找到Xml文件","没有找到Xml文件。。。。");
//            return;
//        }
//        //解析XML
//        try {
//            FileInputStream fin=new FileInputStream(xmlList.get(0));
//            //Log.d("SheZhiActivity", "fin:" + fin);
//            final List<Subject> subjectList=  pull2xml(fin);
//            Log.d(TAG, "XMLList值:" + subjectList);
//            if (subjectList!=null && subjectList.size()>0){
//                //排序
//                Collections.sort(subjectList, new Subject());
//                Log.d("SheZhiActivity", "解析成功,文件个数:"+subjectList.size());
//                if (zipFile!=null){
//                    zipFile.setRunInThread(true); // true 在子线程中进行解压 ,
//                    // false主线程中解压
//                    zipFile.extractAll(path222); // 将压缩文件解压到filePath中..
//                }
//
//                //先登录旷视
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        getOkHttpClient3(subjectList,path222);
//                    }
//                }).start();
//
//
//
//                final int size= subjectList.size();
//                Log.d("ffffff", "size:" + size);
//
//            }else {
//                showNotifictionIcon(0,"解析失败","人脸库XML解析失败");
//
//            }
//
//        } catch (Exception e) {
//            showNotifictionIcon(0,"解析失败","人脸库XML解析异常");
//            Log.d("SheZhiActivity", e.getMessage()+"解析XML异常");
//        }
//
//    }
//
//    //入库
//    private void getOkHttpClient3(final List<Subject> subjectList, final String trg){
//
//
//        if (paAccessControl==null){
//            showNotifictionIcon(0,"识别模块初始化失败","识别模块初始化失败无法入库");
//            return;
//        }
//        final int size=subjectList.size();
//        int t=0;
//        Log.d(TAG, "size:" + size);
//        //循环
//        for (int j=0;j<size;j++) {
//            //	Log.d(TAG, "i:" + j);
//            String filePath=null;
//            while (true){
//                try {
//                    Thread.sleep(300);
//                    t++;
//                  //  Log.d(TAG, "2循环");
//                    // 获取后缀名
//                    //String sname = name.substring(name.lastIindexOf("."));
//                    filePath=trg+ File.separator+subjectList.get(j).getId()+(subjectList.get(j).getPhoto().
//                            substring(subjectList.get(j).getPhoto().lastIndexOf(".")));
//                    File file=new File(filePath);
//                    if ((file.isFile() && file.length()>0)|| t==100){
//                        t=0;
//                        //	Log.d(TAG, "file.length():" + file.length()+"   t:"+t);
//                        break;
//                    }
//                }catch (Exception e){
//                    filePath=null;
//                    Log.d(TAG, e.getMessage()+"检测文件是否存在异常");
//                    break;
//                }
//
//            }
//            //Log.d(TAG, "((float)j / (float) size * 100):" + ((float)j / (float) size * 100));
//            showNotifictionIcon((int) ((float)j / (float) size * 100),"入库中","入库中"+(int) ((float)j / (float) size * 100)+"%");
//            if (filePath!=null){
//                long idididi =subjectList.get(j).getId();
//                BitmapUtil.saveBitmapToSD(BitmapFactory.decodeFile(filePath), MyApplication.SDPATH3, idididi + ".png");
//                PaAccessDetectFaceResult detectResult = paAccessControl.
//                        detectFaceByFile(MyApplication.SDPATH3+File.separator+idididi+".png");
//
//                if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
//                    Log.d("TSXXChuLi", "detectResult.message:" + detectResult.message);
////
//                    //先查询有没有
//                    try {
//                        PaAccessFaceInfo face = paAccessControl.queryFaceById(idididi + "");
//                        if (face != null) {
//                            Log.d("TSXXChuLi", "删除已有的人脸:" + paAccessControl.deleteFaceById(face.faceId));
//                        }
//                        paAccessControl.addFace(idididi+"", detectResult.feature, MyApplication.GROUP_IMAGE);
//
//
//                        subjectList.get(j).setTeZhengMa(idididi+""); //人员id==图片id
//                        subjectList.get(j).setId(idididi);
//                        subjectList.get(j).setDaka(0);
//                        subjectBox.put(subjectList.get(j));
//                        Log.d("MyReceiver", "单个员工入库成功");
//                        paAccessControl.startFrameDetect();
//                        stringBuilderId.append(subjectList.get(j).getId());
//                        stringBuilderId.append(",");
//                        Log.d(TAG,"批量入库成功："+ subjectList.get(j).getId());
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        paAccessControl.startFrameDetect();
//                        stringBuilder2.append("入库添加图片失败:").append("ID:")
//                                .append(subjectList.get(j).getId()).append("姓名:")
//                                .append(subjectList.get(j).getName()).append("时间:")
//                                .append(DateUtils.time(System.currentTimeMillis() + ""))
//                                .append("错误码:").append(e.getMessage()).append("\n");
//
//                    }
//                }else {
//                    stringBuilder2.append("入库添加图片失败:").append("ID:")
//                            .append(subjectList.get(j).getId()).append("姓名:")
//                            .append(subjectList.get(j).getName()).append("时间:")
//                            .append(DateUtils.time(System.currentTimeMillis() + ""))
//                            .append("\n");
//                }
//
//            }else {
//                stringBuilder2.append("入库失败文件不存在:").append("ID:")
//                        .append(subjectList.get(j).getId()).append("姓名:")
//                        .append(subjectList.get(j).getName()).append("时间:")
//                        .append(DateUtils.time(System.currentTimeMillis() + ""))
//                        .append("\n");
//            }
//
//        }
//        //   Log.d("SheZhiActivity", "循环完了");
//
//        String ss=stringBuilder2.toString();
//
//        if (ss.length()>0){
//
//            try {
//                FileUtil.savaFileToSD("失败记录"+DateUtils.timesOne(System.currentTimeMillis()+"")+".txt",ss);
//                showNotifictionIcon(0,"入库完成","有失败的记录,已保存到本地根目录");
//                stringBuilder2.delete(0, stringBuilder2.length());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }else {
//            showNotifictionIcon(0,"入库完成","全部入库成功，没有失败记录");
//        }
//        if (stringBuilderId.length()>0){
//            gengxingzhuangtai();
//        }
//
//    }
//
//
//    private List<Subject> pull2xml(InputStream is) throws Exception {
//        //Log.d(TAG, "jiexi 111");
//        List<Subject> list  = new ArrayList<>();;
//        Subject student = null;
//        //创建xmlPull解析器
//        XmlPullParser parser = Xml.newPullParser();
//        ///初始化xmlPull解析器
//        parser.setInput(is, "utf-8");
//        //读取文件的类型
//        int type = parser.getEventType();
//        //无限判断文件类型进行读取
//        while (type != XmlPullParser.END_DOCUMENT) {
//            switch (type) {
//                //开始标签
//                case XmlPullParser.START_TAG:
//                    if ("Root".equals(parser.getName())) {
////						String id=parser.getAttributeValue(0);
////						if (baoCunBean.getZhanghuId()==null || !baoCunBean.getZhanghuId().equals(id)){
////							Log.d(TAG, "jiexi222 ");
////							showNotifictionIcon(context,0,"解析XML失败","xml账户ID不匹配");
////							Log.d(TAG, "333jiexi ");
////							return null;
////						}
//
//                    } else if ("Subject".equals(parser.getName())) {
//
//                        student=new Subject();
//                        student.setId(Long.valueOf(parser.getAttributeValue(0)));
//                        student.setSid(parser.getAttributeValue(0));
//
//                    } else if ("name".equals(parser.getName())) {
//                        //获取name值
//                        String name = parser.nextText();
//                        if (name!=null){
//                            student.setName(URLDecoder.decode(name, "UTF-8"));
//                        }
//
//                    } else if ("companyId".equals(parser.getName())) {
//                        //获取nickName值
//                        String companyId = parser.nextText();
//                        if (companyId!=null){
//                            student.setPhone(companyId);
//                        }
//                    }else if ("companyName".equals(parser.getName())) {
//                        //获取nickName值
//                        String companyName = parser.nextText();
//                        if (companyName!=null){
//                            student.setCompanyName(URLDecoder.decode(companyName, "UTF-8"));
//                        }
//                    }
//                    else if ("workNumber".equals(parser.getName())) {
//                        //获取nickName值
//                        String workNumber = parser.nextText();
//                        if (workNumber!=null){
//                            student.setWorkNumber(URLDecoder.decode(workNumber, "UTF-8"));
//                        }
//                    }
//                    else if ("sex".equals(parser.getName())) {
//                        //获取nickName值
//                        String sex = parser.nextText();
//                        if (sex!=null){
//                            student.setSex(URLDecoder.decode(sex, "UTF-8"));
//                        }
//                    }
//                    else if ("phone".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setPhone(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("peopleType".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setPeopleType(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("email".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setEmail(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("position".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setPosition(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("employeeStatus".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setEmployeeStatus(Integer.valueOf(nickName));
//                        }
//                    }
//                    else if ("quitType".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setIsOpen(Integer.valueOf(nickName));
//                        }
//                    }
//                    else if ("remark".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setRemark(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("photo".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setPhoto(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("storeId".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setStoreId(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("storeName".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setStoreName(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("entryTime".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setEntryTime(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("birthday".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setBirthday(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//                    else if ("departmentName".equals(parser.getName())) {
//                        //获取nickName值
//                        String nickName = parser.nextText();
//                        if (nickName!=null){
//                            student.setDepartmentName(URLDecoder.decode(nickName, "UTF-8"));
//                        }
//                    }
//
//                    break;
//                //结束标签
//                case XmlPullParser.END_TAG:
//                    if ("Subject".equals(parser.getName())) {
//                        list.add(student);
//                    }
//                    break;
//            }
//            //继续往下读取标签类型
//            type = parser.next();
//        }
//        return list;
//    }
//
//
//
//
//
//
//    /**
//     * 压缩图片（质量压缩）
//     * @param bitmap
//     */
//    public static File compressImage(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            options -= 10;//每次都减少10
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            //long length = baos.toByteArray().length;
//        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String filename = format.format(date);
//        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            try {
//                fos.write(baos.toByteArray());
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//        }
//        //	recycleBitmap(bitmap);
//        return file;
//    }
////	public static void recycleBitmap(Bitmap... bitmaps) {
////		if (bitmaps==null) {
////			return;
////		}
////		for (Bitmap bm : bitmaps) {
////			if (null != bm && !bm.isRecycled()) {
////				bm.recycle();
////			}
////		}
////	}
//
//
//
//    //提交下载状态
//    private void link_uplodexiazai(){
//
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient= new OkHttpClient();
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("id",baoCunBean.getZhanhuiId())
//                .add("downloads","1")
//                .build();
//        Log.d(TAG, baoCunBean.getZhanhuiId()+"展会id");
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi()+"/appSaveExDownloads.do");
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//                try{
//
//                    ResponseBody body = response.body();
//                    String ss=body.string().trim();
//                    Log.d("AllConnects", "上传下载次数状态"+ss);
//
////					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
////					Gson gson=new Gson();
////					final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);
//
//
//                }catch (Exception e){
//                    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
//                }
//
//            }
//        });
//    }
//
//
//    //单个访客
//    private void link_dangeFangke(final String id, final int status, final String url){
//
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("id",id)
//                //	.add("downloads","1")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi()+url);
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//
//                synchronized (TSXXChuLi.this){
//                    paAccessControl.stopFrameDetect();
//                    try{
//                        ResponseBody body = response.body();
//                        final String ss=body.string().trim();
//                        Log.d("AllConnects", "单个员工"+ss);
//                        JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                        Gson gson=new Gson();
//                        final LingShiSubject renShu=gson.fromJson(jsonObject, LingShiSubject.class);
//                        if (status!=3){
//                            Bitmap bitmap=null,bitmapTX=null;
//                            try {
//                                bitmap = Glide.with(context).asBitmap()
//                                        .load(baoCunBean.getHoutaiDiZhi().replace("/front","")+renShu.getPhoto())
//                                        // .sizeMultiplier(0.5f)
//                                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                        .get();
//                            } catch (InterruptedException | ExecutionException e) {
//                                e.printStackTrace();
//                            }
//                            if (bitmap!=null) {
//
//                                BitmapUtil.saveBitmapToSD(bitmap, MyApplication.SDPATH3, renShu.getId() + ".png");
//                                PaAccessDetectFaceResult detectResult = paAccessControl.
//                                        detectFaceByFile(MyApplication.SDPATH3+File.separator+renShu.getId()+".png");
//
//                                if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
//                                    Log.d("TSXXChuLi", "detectResult.message:" + detectResult.message);
////
//                                    //先查询有没有
//                                    try {
//                                        PaAccessFaceInfo face = paAccessControl.queryFaceById(renShu.getId()+"");
//                                        if (face!=null){
//                                            Log.d("TSXXChuLi", "删除已有的人脸:" + paAccessControl.deleteFaceById(face.faceId));
//                                        }
//                                        paAccessControl.addFace(renShu.getId()+"", detectResult.feature, MyApplication.GROUP_IMAGE);
//
//                                        Subject subject = new Subject();
//                                        subject.setTeZhengMa(renShu.getId()+""); //人员id==图片id
//                                        subject.setId(renShu.getId());
//                                        subject.setPeopleType(renShu.getPeopleType());
//                                        subject.setDaka(0);
//                                        subject.setBirthday(renShu.getBirthday());
//                                        subject.setName(renShu.getName());
//                                        subject.setEntryTime(renShu.getEntryTime());
//                                        subject.setPhone(renShu.getPhone());
//                                        subject.setEmail(renShu.getEmail());
//                                        subject.setRemark(renShu.getRemark());
//                                        subject.setPosition(renShu.getPosition());
//                                        subject.setWorkNumber(renShu.getWorkNumber());
//                                        subject.setStoreId(renShu.getStoreId());
//                                        subject.setStoreName(renShu.getStoreName());
//                                        subject.setCompanyId(renShu.getCompanyId());
//                                        subject.setDepartmentName(renShu.getDepartmentName());
//                                        subjectBox.put(subject);
//                                        if (renShu.getDisplayPhoto()!=null && !renShu.getDisplayPhoto().equals("")){
//                                            try {
//                                                bitmapTX = Glide.with(context).asBitmap()
//                                                        .load(baoCunBean.getHoutaiDiZhi().replace("/front","")+renShu.getDisplayPhoto())
//                                                        // .sizeMultiplier(0.5f)
//                                                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                                        .get();
//                                            } catch (InterruptedException | ExecutionException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                        //保存头像
//                                        String path=null;
//                                        if (bitmapTX!=null){
//                                            String fn = renShu.getId()+".png";
//                                            path=saveBitmap2File2(bitmapTX, MyApplication.SDPATH + File.separator + fn, 80);
//                                            GlideCacheUtil.getInstance().clearImageAllCache(MyApplication.myApplication);
//                                        }
//                                        subject.setDisplayPhoto(path);
//                                        subjectBox.put(subject);
//                                        Log.d("MyReceiver", "单个员工入库成功");
//                                        paAccessControl.startFrameDetect();
//
//                                    } catch(Exception e){
//                                        e.printStackTrace();
//                                        paAccessControl.startFrameDetect();
//                                    }
//
//                                }else {
//                                    if (detectResult!=null)
//                                        showNotifictionIcon(0,"入库失败","错误码:"+detectResult.message);
//                                    else
//                                        showNotifictionIcon(0,"入库失败","detectResult=null");
//
//                                    paAccessControl.startFrameDetect();
//                                }
//
//                            }else {
//                                showNotifictionIcon(0,"入库失败","下载图片失败 "+renShu.getName());
//                                paAccessControl.startFrameDetect();
//                            }
//
//                        }else {
//                            //删除
//                            try {
//                                PaAccessFaceInfo face = paAccessControl.queryFaceById(id);
//                                if (face!=null){
//                                    Log.d("TSXXChuLi", "deleteLocalFace:" + paAccessControl.deleteFaceById(face.faceId));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            subjectBox.remove(Long.valueOf(id));
//                            paAccessControl.startFrameDetect();
//                            Log.d("MyReceiver", "单个员工删除成功");
//                        }
//
//                    }catch (Exception e){
//                        Log.d("MyReceiver", "dddd");
//                        showNotifictionIcon( 0,"入库出错","出现异常"+e.getMessage());
//                        PaAccessControl.getInstance().startFrameDetect();
//                    }
//                }
//
//            }
//        });
//    }
//
//
//    //单个员工
//    private void link_dangeYuanGong(final String id, final int status, final String url){
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("id",id)
//                //	.add("downloads","1")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi()+url);
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//
//                synchronized (TSXXChuLi.this){
//                    paAccessControl.stopFrameDetect();
//                try{
//                    ResponseBody body = response.body();
//                    final String ss=body.string().trim();
//                    Log.d("AllConnects", "单个员工"+ss);
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    final LingShiSubject renShu=gson.fromJson(jsonObject, LingShiSubject.class);
//                    if (status!=3){
//                        Bitmap bitmap=null,bitmapTX=null;
//                        try {
//                            bitmap = Glide.with(context).asBitmap()
//                                    .load(baoCunBean.getHoutaiDiZhi().replace("/front","")+renShu.getPhoto())
//                                    // .sizeMultiplier(0.5f)
//                                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                    .get();
//                        } catch (InterruptedException | ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        if (bitmap!=null) {
//
//                            BitmapUtil.saveBitmapToSD(bitmap, MyApplication.SDPATH3, renShu.getId() + ".png");
//                            PaAccessDetectFaceResult detectResult = paAccessControl.
//                                    detectFaceByFile(MyApplication.SDPATH3+File.separator+renShu.getId()+".png");
//
//                            if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
//                                Log.d("TSXXChuLi", "detectResult.message:" + detectResult.message);
//                                //先查询有没有
//                                try {
//                                    PaAccessFaceInfo face = paAccessControl.queryFaceById(renShu.getId()+"");
//                                    if (face!=null){
//                                        Log.d("TSXXChuLi", "删除已有的人脸:" + paAccessControl.deleteFaceById(face.faceId));
//                                    }
//                                    paAccessControl.addFace(renShu.getId()+"", detectResult.feature, MyApplication.GROUP_IMAGE);
//
//                                    Subject subject = new Subject();
//                                    subject.setTeZhengMa(renShu.getId()+""); //人员id==图片id
//                                    subject.setId(renShu.getId());
//                                    subject.setPeopleType(renShu.getPeopleType());
//                                    subject.setDaka(0);
//                                    subject.setBirthday(renShu.getBirthday());
//                                    subject.setName(renShu.getName());
//                                    subject.setEntryTime(renShu.getEntryTime());
//                                    subject.setPhone(renShu.getPhone());
//                                    subject.setEmail(renShu.getEmail());
//                                    subject.setRemark(renShu.getRemark());
//                                    subject.setPosition(renShu.getPosition());
//                                    subject.setWorkNumber(renShu.getWorkNumber());
//                                    subject.setStoreId(renShu.getStoreId());
//                                    subject.setStoreName(renShu.getStoreName());
//                                    subject.setCompanyId(renShu.getCompanyId());
//                                    subject.setDepartmentName(renShu.getDepartmentName());
//                                    subjectBox.put(subject);
//                                    if (renShu.getDisplayPhoto()!=null && !renShu.getDisplayPhoto().equals("")){
//                                        try {
//                                            bitmapTX = Glide.with(context).asBitmap()
//                                                    .load(baoCunBean.getHoutaiDiZhi().replace("/front","")+renShu.getDisplayPhoto())
//                                                    // .sizeMultiplier(0.5f)
//                                                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                                    .get();
//                                        } catch (InterruptedException | ExecutionException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    //保存头像
//                                    String path=null;
//                                    if (bitmapTX!=null){
//                                        String fn = renShu.getId()+".png";
//                                        path=saveBitmap2File2(bitmapTX, MyApplication.SDPATH + File.separator + fn, 80);
//                                        GlideCacheUtil.getInstance().clearImageAllCache(MyApplication.myApplication);
//                                    }
//                                    subject.setDisplayPhoto(path);
//                                    subjectBox.put(subject);
//                                    Log.d("MyReceiver", "单个员工入库成功");
//                                    paAccessControl.startFrameDetect();
//
//                                } catch(Exception e){
//                                    e.printStackTrace();
//                                    paAccessControl.startFrameDetect();
//                                }
//
//                            }else {
//                                if (detectResult!=null)
//                                showNotifictionIcon(0,"入库失败","错误码:"+detectResult.message);
//                                else
//                                    showNotifictionIcon(0,"入库失败","detectResult=null");
//
//                                paAccessControl.startFrameDetect();
//                            }
//
//                        }else {
//                            showNotifictionIcon(0,"入库失败","下载图片失败 "+renShu.getName());
//                            paAccessControl.startFrameDetect();
//                        }
//
//                    }else {
//                        //删除
//                        try {
//                            PaAccessFaceInfo face = paAccessControl.queryFaceById(id);
//                            if (face!=null){
//                                Log.d("TSXXChuLi", "deleteLocalFace:" + paAccessControl.deleteFaceById(face.faceId));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        subjectBox.remove(Long.valueOf(id));
//                        paAccessControl.startFrameDetect();
//                        Log.d("MyReceiver", "单个员工删除成功");
//                    }
//
//                }catch (Exception e){
//                    Log.d("MyReceiver", "dddd");
//                    showNotifictionIcon( 0,"入库出错","出现异常"+e.getMessage());
//                    PaAccessControl.getInstance().startFrameDetect();
//                }
//            }
//          }
//        });
//    }
//
//
//
//    //关怀
//    private void link_guanhuai(final String id, final int status, final String url){
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient= new OkHttpClient();
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("id",id)
//                //	.add("downloads","1")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi()+url);
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//                try{
//                    ResponseBody body = response.body();
//                    final String ss=body.string().trim();
//                    Log.d("AllConnects", "节日"+ss);
//
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    // 1生日提醒 2入职关怀 3节日关怀）
//                    final GuanHuai youJuBean=gson.fromJson(jsonObject,GuanHuai.class);
//                    if (status!=3){
//                        //添加
//                        Log.d("MyReceiver", "关怀入库:" + guanHuaiBox.put(youJuBean));
//                    }else {
//                        //删除
//                        guanHuaiBox.remove(youJuBean.getId());
//                        Log.d("MyReceiver", "删除关怀");
//                    }
//
//                }catch (Exception e){
//                    showNotifictionIcon( 0,"节日","出现异常"+e.getMessage());
//                }
//
//            }
//        });
//    }
//
//
//
//    //关怀
//    private void link_youju(final String id, final int status, final String url){
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient= new OkHttpClient();
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("id",id)
//                //	.add("downloads","1")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi()+url);
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//                try{
//                    ResponseBody body = response.body();
//                    final String ss=body.string().trim();
//                    Log.d("AllConnects", "邮局"+ss);
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    final GuanHuai youJuBean=gson.fromJson(jsonObject,GuanHuai.class);
//                    if (status!=3){
//                        Log.d("MyReceiver", "邮局入库:" + guanHuaiBox.put(youJuBean));
//                    }else {
//                        //删除
//                        guanHuaiBox.remove(youJuBean.getId());
//                        Log.d("MyReceiver", "删除邮局");
//                    }
//                }catch (Exception e){
//                    showNotifictionIcon( 0,"邮局","出现异常"+e.getMessage());
//                }
//
//            }
//        });
//    }
//
//    //关怀
//    private void link_beijing(final String id, final int status, final String url){
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("id",id)
//                //	.add("downloads","1")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(url);
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//                try{
//                    ResponseBody body = response.body();
//                    final String ss=body.string().trim();
//                    Log.d("AllConnects", "背景"+ss);
//
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    final BeiJingBean beiJingBean=gson.fromJson(jsonObject,BeiJingBean.class);
//                    Bitmap bitmap=null,bitmapLog = null;
//                    try {
//                        bitmap = Glide.with(context).asBitmap()
//                                .load(beiJingBean.getBackgroundUrl())
//                                // .sizeMultiplier(0.5f)
//                                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                .get();
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                       // if (beiJingBean.getBackgroundUrl()!=null && !beiJingBean.getBackgroundUrl().equals(""))
//                      //  showNotifictionIcon( 0,"底图更新","下载背景底图失败"+e.getMessage());
//                    }
//                    try {
//                        bitmapLog = Glide.with(context).asBitmap()
//                                .load(beiJingBean.getBackgroundLog())
//                                // .sizeMultiplier(0.5f)
//                                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                .get();
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                        if (beiJingBean.getBackgroundLog()!=null && !beiJingBean.getBackgroundLog().equals(""))
//                        showNotifictionIcon( 0,"底图更新","下载背景Log失败"+e.getMessage());
//                    }
//                    if (bitmapLog!=null ) {
//                        try {
//
//                            File file = new File(MyApplication.SDPATH + File.separator + "logo_rt.png");
//                            FileOutputStream out = new FileOutputStream(file);
//                            bitmapLog.compress(Bitmap.CompressFormat.PNG, 100, out);
//                            out.flush();
//                            out.close();
//                            baoCunBean.setXiaBanTime(MyApplication.SDPATH + File.separator + "logo_rt.png");
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            showNotifictionIcon(0, "底图更新", "出现异常" + e.getMessage());
//                        }
//                    }
////                    if (bitmap!=null ){
////                        try {
////
////                            File file = new File(MyApplication.SDPATH+File.separator+"beijing_rt.png");
////                            FileOutputStream out = new FileOutputStream(file);
////                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
////                            out.flush();
////                            out.close();
////                            baoCunBean.setTouxiangzhuji(MyApplication.SDPATH+File.separator+"beijing_rt.png");
////
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                            showNotifictionIcon( 0,"底图更新","出现异常"+e.getMessage());
////                        }
////
////                    }else {
////                        showNotifictionIcon( 0,"底图更新","下载背景底图失败");
////                    }
//
//                    baoCunBean.setWenzi1(beiJingBean.getBackgroundTitle());
//                    baoCunBeanDao.put(baoCunBean);
//                    EventBus.getDefault().post("ditu123");
//
//                    if (baoCunBean!=null)
//                    return;
//
//                    final String vv=beiJingBean.getCarouselVideo();
//                    String voiddd=null;
//                    if (vv!=null){
//                        voiddd=vv.substring(vv.lastIndexOf("/"),vv.length());
//                    }
//                    Log.d("MyReceiver",  SDPATH+voiddd+"\n"+vv);
//                    //下载视频
//
//                    if (baoCunBean.getShiPingWeiZhi()!=null && new File(baoCunBean.getShiPingWeiZhi()).isFile() &&
//                            !baoCunBean.getShiPingWeiZhi().equals(SDPATH +  voiddd)){
//                        File ff=new File(baoCunBean.getShiPingWeiZhi());
//                        Log.d("MyReceiver", "ff.删除原有视频文件:" + ff.delete());
//                    }
//
//                    final String finalVoiddd = voiddd;
//                    if (!new File(SDPATH +  voiddd).isFile()) {
//                        FileDownloader.getImpl().create(vv)
//                                .setPath(SDPATH + voiddd)
//                                .setCallbackProgressTimes(600)
//                                .setMinIntervalUpdateSpeed(600)
//                                .setListener(new FileDownloadListener() {
//                                    @Override
//                                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                        //Log.d(TAG, "pending"+soFarBytes);
//                                        Log.d("MyReceiver", task.getUrl() + "pending"+totalBytes);
//                                    }
//
//                                    @Override
//                                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                                        //已经连接上
//                                        //	Log.d("MyReceiver", "task.getSmallFileTotalBytes():" + task.getSmallFileTotalBytes());
//                                        //	Log.d("MyReceiver", task.getUrl() + "connected"+totalBytes);
//                                        //Log.d(TAG, "isContinue:" + isContinue);
//                                        showNotifictionIcon(100, "下载中", "下载视频中 " + (soFarBytes /1048576)  + "M");
//
//                                    }
//
//                                    @Override
//                                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                        //Log.d("MyReceiver", "soFarBytes:" + soFarBytes +"  -  "+ totalBytes);
//                                        //进度
//                                        //isDW=false;
////										Log.d("MyReceiver", "task.getSmallFileTotalBytes():" + task.getSmallFileTotalBytes());
////										Log.d("MyReceiver", "task.getSmallFileTotalBytes():" + task.getLargeFileTotalBytes());
////										Log.d("MyReceiver", "task.getSmallFileTotalBytes():" + task.getTotalBytes());
//
//                                        if (task.getUrl().equals(vv)) {
//                                            ToastUtils.getInstances().setDate("下载中", 100, "下载视频中 " + ((float)soFarBytes/1048576.0f) + "M");
//                                            //	showNotifictionIcon(,,);
//                                        }
//                                    }
//
//                                    @Override
//                                    protected void blockComplete(BaseDownloadTask task) {
//                                        //完成
//                                        Log.d("MyReceiver", task.getUrl() + "完成2");
//                                    }
//
//                                    @Override
//                                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
//                                        //重试
//                                        Log.d("MyReceiver", ex.getMessage() + "重试 " + retryingTimes);
//                                    }
//
//                                    @Override
//                                    protected void completed(BaseDownloadTask task) {
//                                        Log.d("MyReceiver", task.getUrl() + "完成1");
//                                        //完成整个下载过程
//                                        if (task.getUrl().equals(vv)) {
//                                            baoCunBean.setShiPingWeiZhi(SDPATH + finalVoiddd);
//                                            baoCunBeanDao.put(baoCunBean);
//                                            EventBus.getDefault().post("shiping");
//                                        }
//                                    }
//
//                                    @Override
//                                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                                        Log.d("MyReceiver", task.getUrl() + "暂停");
//                                    }
//
//                                    @Override
//                                    protected void error(BaseDownloadTask task, Throwable e) {
//                                        Log.d("MyReceiver", "task.isRunning():" + task.getFilename() + "失败" + e);
//                                        //出错
//
//                                        showNotifictionIcon(0, "下载失败", "" + e);
//                                    }
//
//                                    @Override
//                                    protected void warn(BaseDownloadTask task) {
//                                        //在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务
//                                        Log.d("MyReceiver", task.getUrl() + "等待");
//                                    }
//                                }).start();
//                    }
//
//                    //下载轮播图片
//
//                    List<LunBoBean> bbbb=lunBoBeanBox.getAll();
//                    for (LunBoBean bb:bbbb){
//                        File file=new File(bb.getPath());
//                        Log.d("MyReceiver", "删除原有轮播图:" + file.delete());
//                    }
//
//                    lunBoBeanBox.removeAll();
//                    String lb=beiJingBean.getCarouselImg();
//                    Log.d("MyReceiver", lb);
//                    String []lunbg=lb.split(",");
//                    //Log.d("MyReceiver", "lunbg.length:" + lunbg.length);
//                    for (String slb :lunbg){
//                        Bitmap bitmapLB=null;
//                        try {
//                            if (slb!=null && !slb.equals("")){
//                                bitmapLB = Glide.with(context).asBitmap()
//                                        .load(slb)
//                                        // .sizeMultiplier(0.5f)
//                                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                        .get();
//                            }else {
//                                continue;
//                            }
//
//                        } catch (InterruptedException | ExecutionException e) {
//                            e.printStackTrace();
//                            showNotifictionIcon( 0,"底图更新","下载轮播图失败"+e.getMessage());
//                        }
//                        if (bitmapLB!=null ){
//                            try {
//                                String ph=MyApplication.SDPATH+File.separator+System.currentTimeMillis()+".png";
//                                File file = new File(ph);
//                                FileOutputStream out = new FileOutputStream(file);
//                                bitmapLB.compress(Bitmap.CompressFormat.PNG, 100, out);
//                                out.flush();
//                                out.close();
//                                LunBoBean bb=new LunBoBean();
//                                bb.setPath(ph);
//                                lunBoBeanBox.put(bb);
//                                Log.d("MyReceiver222", "添加轮播图"+bb);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                showNotifictionIcon( 0,"轮播图更新","出现异常"+e.getMessage());
//                            }
//
//                        }else {
//                            showNotifictionIcon( 0,"轮播图更新","下载背景底图失败");
//                        }
//                    }
//                    if (lunBoBeanBox.getAll().size()>0)
//                        EventBus.getDefault().post("lunbotu");
//
//
//                }catch (Exception e){
//                    showNotifictionIcon( 0,"底图更新","出现异常"+e.getMessage());
//                }
//
//            }
//        });
//    }
//
//    //从老黄后台拿批量信息
//    private void link_xinxituisong(final String id, final int status,String url){
//
//       // final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//
//        RequestBody body = new FormBody.Builder()
//                .add("id", id)
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
//                .header("Content-Type", "application/json")
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi() + url);
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败" + e.getMessage());
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功" + call.request().toString());
//                //获得返回体
//                try {
//                    ResponseBody body = response.body();
//                    String ss = body.string().trim();
//                    Log.d("AllConnects", "信息推送" + ss);
//
//                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson = new Gson();
//                    XinXiAll xiAll = gson.fromJson(jsonObject, XinXiAll.class);
//                    if (status!=3){
//                        //更新
//                        //先查询子数据有没有,有就不管他，没有就新增
//                        List<XinXiIdBean> xx = xinXiIdBeanBox.query().equal(XinXiIdBean_.uuid, xiAll.getId()).build().find();
//                        if (xx.size()<=0){
//                            String ids[] = xiAll.getEmployeeId().split(",");
//                            for (String id1 : ids) {
//                                XinXiIdBean xiIdBean = new XinXiIdBean();
//                                xiIdBean.setYgid(Long.valueOf(id1));
//                                xiIdBean.setUuid(xiAll.getId());
//                                Log.d("MyReceiver", "新增消息2：" + xinXiIdBeanBox.put(xiIdBean));
//                            }
//                        }
//                        Log.d("MyReceiver", "新增消息1:" + xinXiAllBox.put(xiAll));
//
//                    }else {
//                        //删除
//                        if (xiAll.getEmployeeId().equals("0")){
//                            xinXiAllBox.remove(xiAll.getId());
//                            Log.d("MyReceiver", "删除"+xiAll.getId());
//                        }
//                        List<XinXiIdBean> xx = xinXiIdBeanBox.query().equal(XinXiIdBean_.uuid, xiAll.getId()).build().find();
//                        for (XinXiIdBean id1 : xx) {
//                            xinXiIdBeanBox.remove(id1.getId());
//                            Log.d("MyReceiver", "删除"+id1);
//                        }
//
//                    }
//
//
//
//                } catch (Exception e) {
//
//                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
//                }
//            }
//        });
//
//    }
//
//
//    private void gengxingzhuangtai(){
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
////        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
////                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
////                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//////				.cookieJar(new CookiesManager())
////                //.retryOnConnectionFailure(true)
////                .build();
//
//        RequestBody body = new FormBody.Builder()
//                .add("pictureId", stringBuilderId.toString())
//                //.add("cardNumber", id + "")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
//                //.header("Content-Type", "application/json")
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi() + "/app/employeeStatus");
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求成功" + call.request().toString());
//                //获得返回体
//                try {
//                    //没了删除，所有在添加前要删掉所有
//
//                    ResponseBody body = response.body();
//                    String ss = body.string().trim();
//                    Log.d("AllConnects", "更新后台状态" + ss);
//
//                } catch (Exception e) {
//
//                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
//                }
//            }
//        });
//    }
//
//
////    private void XiaZaiTuPian(Context context, RenYuanInFo renYuanInFo){
////        Glide.with(context).asBitmap().load("").into(new SimpleTarget<Bitmap>() {
////            @Override
////            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
////
////            }
////
////            @Override
////            public void onLoadFailed(@Nullable Drawable errorDrawable) {
////                super.onLoadFailed(errorDrawable);
////
////            }
////        });
////        //Log.d("MyReceiver", "图片0");
////        Bitmap bitmap=null;
////        try {
////            bitmap = Glide.with(context).asBitmap()
////                    .load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
////                    // .sizeMultiplier(0.5f)
////                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
////                    .get();
////        } catch (InterruptedException | ExecutionException e) {
////            e.printStackTrace();
////            stringBuilder.append("从瑞瞳后台下载图片失败记录:")
////                    .append("图片地址").append(baoCunBean.getHoutaiDiZhi()).append("/upload/compare/").append(renYuanInFo.getPhoto_ids())
////                    .append("时间:")
////                    .append(DateUtils.time(System.currentTimeMillis()+""))
////                    .append("\n");
////        }
////
////        if (bitmap!=null){
////
////
////
////        }else {
////
////            Intent intent=new Intent("shoudongshuaxin");
////            intent.putExtra("date","登录失败");
////            context.sendBroadcast(intent);
////
////        }
////    }
//
//
//    /***
//     *保存bitmap对象到文件中
//     * @param bm
//     * @param path
//     * @param quality
//     * @return
//     */
//    public String saveBitmap2File2(Bitmap bm, final String path, int quality) {
//        if (null == bm || bm.isRecycled()) {
//            Log.d("InFoActivity", "回收|空");
//            return null;
//        }
//        try {
//            File file = new File(path);
//            if (file.exists()) {
//                file.delete();
//                file = new File(path);
//            }
//            BufferedOutputStream bos = new BufferedOutputStream(
//                    new FileOutputStream(file));
//            bm.compress(Bitmap.CompressFormat.PNG, quality, bos);
//            bos.flush();
//            bos.close();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//
//            if (!bm.isRecycled()) {
//                bm.recycle();
//            }
//            bm = null;
//        }
//        return path;
//    }
//
//    private void gengxingzhuangtai2(String id){
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        RequestBody body = new FormBody.Builder()
//               // .add("failureMachineCode", XGPushConfig.getToken(context))
//                .add("id", id + "")
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
//                //.header("Content-Type", "application/json")
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi() + "/app/findFailurePush");
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "更新后台状态" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "更新后台状态" + call.request().toString());
//                //获得返回体
//                try {
//                    //没了删除，所有在添加前要删掉所有
//
//                    ResponseBody body = response.body();
//                    String ss = body.string().trim();
//                    Log.d("AllConnects", "更新后台状态" + ss);
//
//                } catch (Exception e) {
//
//                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
//                }
//            }
//        });
//    }
//
//    /**
//     * 获取本地化后的config
//     * 注册和比对使用不同的设置
//     */
//    private void initConfig1() {
//        //Robin 使用注册的设置
//        PaAccessDetectConfig faceDetectConfig = PaAccessControl.getInstance().getPaAccessDetectConfig();
//        faceDetectConfig.setMinBrightnessThreshold(30);
//        faceDetectConfig.setMaxBrightnessThreshold(240);
//        //TODO Robin 注册图片模糊度可以设置0.9f（最大值1.0）这样能让底图更清晰。比对的模糊度可以调低一点，这样能加快识别速度，识别模糊度建议设置0.1f
//        faceDetectConfig.setBlurnessThreshold(0.8f);
//        faceDetectConfig.setLivenessEnabled(false); //Robin 注册不进行活体检测
//        PaAccessControl.getInstance().setPaAccessDetectConfig(faceDetectConfig);
//    }
//
//    /**
//     * 获取本地化后的config
//     * 注册和比对使用不同的设置
//     */
//    private void initConfig2() {
//        //Robin 使用注册的设置
//        PaAccessDetectConfig faceDetectConfig = PaAccessControl.getInstance().getPaAccessDetectConfig();
//        faceDetectConfig.setMinBrightnessThreshold(30);
//        faceDetectConfig.setMaxBrightnessThreshold(240);
//        //TODO Robin 注册图片模糊度可以设置0.9f（最大值1.0）这样能让底图更清晰。比对的模糊度可以调低一点，这样能加快识别速度，识别模糊度建议设置0.1f
//        faceDetectConfig.setBlurnessThreshold(0.2f);
//        faceDetectConfig.setLivenessEnabled(false); //Robin 注册不进行活体检测
//        PaAccessControl.getInstance().setPaAccessDetectConfig(faceDetectConfig);
//    }
//
//}
