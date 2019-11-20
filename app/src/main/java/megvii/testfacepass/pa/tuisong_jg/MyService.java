package megvii.testfacepass.pa.tuisong_jg;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pingan.ai.access.common.PaAccessControlMessage;
import com.pingan.ai.access.entiry.PaAccessFaceInfo;
import com.pingan.ai.access.manager.PaAccessControl;
import com.pingan.ai.access.result.PaAccessDetectFaceResult;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.QueryParam;
import com.yanzhenjie.andserver.annotation.RequestBody;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.framework.body.FileBody;
import com.yanzhenjie.andserver.framework.body.StringBody;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.LazyList;
import io.objectbox.query.Query;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import megvii.testfacepass.pa.MyApplication;
import megvii.testfacepass.pa.beans.BitahFaceBean;
import megvii.testfacepass.pa.beans.DaKaBean;
import megvii.testfacepass.pa.beans.DaKaBean_;
import megvii.testfacepass.pa.beans.IDBean;
import megvii.testfacepass.pa.beans.IDCardBean;
import megvii.testfacepass.pa.beans.IDCardBean_;
import megvii.testfacepass.pa.beans.ResBean;
import megvii.testfacepass.pa.beans.Subject;
import megvii.testfacepass.pa.beans.Subject_;
import megvii.testfacepass.pa.ui.SheZhiActivity2;
import megvii.testfacepass.pa.utils.BitmapUtil;
import megvii.testfacepass.pa.utils.DateUtils;
import megvii.testfacepass.pa.utils.ExcelUtil;
import megvii.testfacepass.pa.utils.FileUtil;
import megvii.testfacepass.pa.utils.GetDeviceId;



@RestController
@RequestMapping(path = "/app")
public class MyService {

    private static final String TAG = "MyService";
    private static WritableFont arial14font = null;
    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";

    private Box<Subject> subjectBox  = MyApplication.myApplication.getSubjectBox();
    private Box<DaKaBean> daKaBeanBox  = MyApplication.myApplication.getDaKaBeanBox();;
    private PaAccessControl paAccessControl=PaAccessControl.getInstance();
    private  String serialnumber= MyApplication.myApplication.getBaoCunBeanBox().get(123456).getJihuoma();

    @PostMapping("/deleteFacee")
     String deleteFacee(
            @RequestBody IDBean idBean) {
        if (idBean==null)
            return requsBean(-1,"数据为空");
        if (paAccessControl==null)
            return requsBean(-1,"识别算法未初始化");
        paAccessControl.stopFrameDetect();
        try {
            StringBuilder kaimen=new StringBuilder();
            for (IDBean.ResultBean id:idBean.getResult()) {
                PaAccessFaceInfo face = paAccessControl.queryFaceById(id.getId());
                if (face != null) {
                    paAccessControl.deleteFaceById(face.faceId);
                    Subject subject = subjectBox.query().equal(Subject_.teZhengMa, id.getId()).build().findUnique();
                    if (subject != null) {
                        File file = new File(MyApplication.SDPATH3, subject.getTeZhengMa() + ".png");
                        Log.d("MyService", "file删除():" + file.delete());
                        subjectBox.remove(subject);
                    }
                    paAccessControl.startFrameDetect();
                } else {
                    paAccessControl.startFrameDetect();
                    kaimen.append(id);
                    kaimen.append(",");
                }
            }
            if (kaimen.length()>0){
                Log.d("MyService", kaimen.toString());
                kaimen.delete(kaimen.length()-1,kaimen.length());
                return requsBean(0,kaimen.toString());
            }else {
                return requsBean(0,"删除成功");
            }

          }catch (Exception e){
            e.printStackTrace();
            paAccessControl.startFrameDetect();
            return requsBean(-1,e+"");
        }
    }

    //删除全部人员
    @GetMapping("/deleteAllFacee")
     String deleteAllFacee(){
        if (paAccessControl==null)
            return requsBean(-1,"识别算法未初始化");
        paAccessControl.stopFrameDetect();
        try {
           List<PaAccessFaceInfo> faces = paAccessControl.queryAllFaces();
            if (faces != null) {
                for (PaAccessFaceInfo face:faces){
                    paAccessControl.deleteFaceById(face.faceId);
                    Subject subject = subjectBox.query().equal(Subject_.teZhengMa, face.faceId).build().findUnique();
                    if (subject!=null){
                        File file =new File(MyApplication.SDPATH3,subject.getTeZhengMa()+".png");
                        Log.d("MyService", "file删除():" + file.delete());
                        subjectBox.remove(subject);
                    }
                }
                paAccessControl.startFrameDetect();
                return requsBean(0,"所有人员删除成功");
            }else {
                paAccessControl.startFrameDetect();
                return requsBean(0,"没有本地人员");
            }
        }catch (Exception e){
            e.printStackTrace();
            paAccessControl.startFrameDetect();
            return requsBean(-1,e+"");
        }
    }



    //        getName()，获取该文件的key，也就是表单中的name
//        getFilename()，获取该文件名称，可能为空
//        getContentType()，获取该文件的内容类型
//        isEmpty()，判断该文件是否是非空的
//        getSize()，获取文件大小
//        getBytes()，获取文件的byte数组，不推荐使用
//        getStream()，获取该文件的输入流
//        transferTo(File)，转移该文件到目标位置


    //新增人员
    @PostMapping("/addFace")
     String addFace(@RequestParam(name = "id") String id,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "departmentName",required = false)String bumen,
                         @RequestParam(name = "pepopleType",required = false)String pepopleType,
                         @RequestParam(name = "number",required = false)String number,
                         @RequestParam(name = "image") MultipartFile file
                ) throws IOException {//required = false
        if (paAccessControl==null)
            return requsBean(-1,"识别算法未初始化");

        paAccessControl.stopFrameDetect();
       Bitmap bitmap=readInputStreamToBitmap(file.getStream(),file.getSize());
        PaAccessDetectFaceResult detectResult = paAccessControl.
                detectFaceByBitmap(bitmap);

        if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
            BitmapUtil.saveBitmapToSD(bitmap, MyApplication.SDPATH3, id + ".png");
            //先查询有没有
            try {
                PaAccessFaceInfo face = paAccessControl.queryFaceById(id);
                if (face != null) {
                    paAccessControl.deleteFaceById(face.faceId);
                    Subject subject = subjectBox.query().equal(Subject_.teZhengMa, id).build().findUnique();
                    if (subject!=null)
                    subjectBox.remove(subject);
                }
                paAccessControl.addFace(id , detectResult.feature, MyApplication.GROUP_IMAGE);
                Subject subject = new Subject();
                subject.setTeZhengMa(id);
                subject.setId(System.currentTimeMillis());
                subject.setPeopleType(pepopleType+"");//0是员工 1是访客
//                subject.setBirthday(renShu.getBirthday());
                subject.setName(name);
               // subject.setIsOpen(Integer.parseInt(isOpen));
                subject.setDepartmentName(bumen);
//                subject.setStoreId(renShu.getStoreId());
              //  subject.setStoreName(renShu.getStoreName());
               // subject.setCompanyId(renShu.getCompanyId());
                //subject.setDepartmentName(renShu.getDepartmentName());

                subjectBox.put(subject);
                Log.d("MyReceiver", "单个员工入库成功"+subject.toString());
                paAccessControl.startFrameDetect();
                return requsBean(0,"成功");
            } catch (Exception e) {
                e.printStackTrace();
                paAccessControl.startFrameDetect();
                return requsBean(-1,e+"");
            }
        }else {
            paAccessControl.startFrameDetect();
            return requsBean(-1,"图片入库质量不合格");
        }
    }

    //修改人员
    @PostMapping("/editFace")
    String EditFace(@RequestParam(name = "id") String id,
                   @RequestParam(name = "name",required = false) String name,
                   @RequestParam(name = "departmentName" ,required = false)String bumen,
                   @RequestParam(name = "pepopleType",required = false)String pepopleType,
                   @RequestParam(name = "image",required = false) MultipartFile file
    ) throws IOException {
        if (paAccessControl==null)
            return requsBean(-1,"识别算法未初始化");

        paAccessControl.stopFrameDetect();
        PaAccessFaceInfo face = paAccessControl.queryFaceById(id);
        if (face!=null){
            PaAccessDetectFaceResult detectResult=null;
            Bitmap bitmap=null;
            if (file!=null){//有图片
                bitmap=readInputStreamToBitmap(file.getStream(),file.getSize());
                detectResult = paAccessControl.detectFaceByBitmap(bitmap);
                if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
                    BitmapUtil.saveBitmapToSD(bitmap, MyApplication.SDPATH3, id + ".png");
                    try {
                        paAccessControl.deleteFaceById(face.faceId);
                        paAccessControl.addFace(id , detectResult.feature, MyApplication.GROUP_IMAGE);
                        Subject subject = subjectBox.query().equal(Subject_.teZhengMa, id).build().findUnique();
                        if (subject!=null){
                            if (name!=null)
                                subject.setName(name);
                            if (bumen!=null){
                                subject.setDepartmentName(bumen);
                            }
                            if (pepopleType!=null){
                                subject.setPeopleType(pepopleType);
                            }
                            subject.setTeZhengMa(id);
                            subjectBox.put(subject);
                            paAccessControl.startFrameDetect();
                            return requsBean(0,"修改成功");
                        }else {
                            paAccessControl.startFrameDetect();
                            return  requsBean(-1,"未找到人员信息!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        paAccessControl.startFrameDetect();
                        return requsBean(-1,e+"");
                    }
                }else {
                    paAccessControl.startFrameDetect();
                    return requsBean(-1,"图片入库质量不合格");
                }
            }else {//没图片只修改其他值
                Subject subject = subjectBox.query().equal(Subject_.teZhengMa, id).build().findUnique();
                if (subject!=null){
                    if (name!=null)
                        subject.setName(name);
                    if (bumen!=null){
                        subject.setDepartmentName(bumen);
                    }
                    if (pepopleType!=null){
                        subject.setPeopleType(pepopleType);
                    }
                    subjectBox.put(subject);
                    paAccessControl.startFrameDetect();
                    return requsBean(0,"修改成功");
                }else {
                    paAccessControl.startFrameDetect();
                    return  requsBean(-1,"未找到人员信息!");
                }
            }
        }else {
            paAccessControl.startFrameDetect();
            return  requsBean(-1,"未找到人员信息!");
        }
    }

    //获取后缀名
    private  String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /*
     * Java文件操作 获取不带扩展名的文件名
     * */
    private   String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


    //新增人员
    @PostMapping("/addBitmapBatch")
    public String addFaceBatch(@RequestParam(name = "bitmapZip") MultipartFile file
    ) throws IOException {
        if (paAccessControl==null)
            return requsBean(-1,"识别算法未初始化");
        paAccessControl.stopFrameDetect();
        if (file.getFilename()==null){
            return requsBean(-1,"文件名为空");
        }
        Log.d("MyService", file.getSize()+"");

        file.transferTo(new File(MyApplication.SDPATH2 , file.getFilename()));
        ZipFile zipFile=null;
        List fileHeaderList=null;
        try {
            zipFile = new ZipFile(MyApplication.SDPATH2+File.separator+file.getFilename());
            zipFile.setFileNameCharset("GBK");
            fileHeaderList = zipFile.getFileHeaders();
            zipFile.setRunInThread(false); // true 在子线程中进行解压 false主线程中解压
            zipFile.extractAll(MyApplication.SDPATH2); // 将压缩文件解压到filePath中..
        } catch (ZipException e) {
            e.printStackTrace();
            return requsBean(-1,e.getMessage()+"");
        }
        if (fileHeaderList==null){
            return requsBean(-1,"解压文件失败");
        }
        Log.d("MyService", "fileHeaderList.size():" + fileHeaderList.size());
        int size=fileHeaderList.size();
        StringBuilder kaimen=new StringBuilder();
        for(int i = 0; i < size; i++) {
            FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
           // Log.d("MyService", MyApplication.SDPATH2 + File.separator + fileHeader.getFileName() +" 图片路径");
            Bitmap bitmap=BitmapFactory.decodeFile(MyApplication.SDPATH2+File.separator+fileHeader.getFileName());
          //  Log.d("MyService", "bitmap.getWidth():" + bitmap.getWidth());
            PaAccessDetectFaceResult detectResult = paAccessControl.detectFaceByBitmap(bitmap);
            String id = getFileNameNoEx(fileHeader.getFileName());
            if (detectResult!=null && detectResult.message== PaAccessControlMessage.RESULT_OK) {
                //先查询有没有
                try {
                   Subject subject= subjectBox.query().equal(Subject_.teZhengMa,id).build().findFirst();
                    paAccessControl.addFace(id , detectResult.feature, MyApplication.GROUP_IMAGE);
                   if (subject==null){
//                       Subject subject2 = new Subject();
//                       subject2.setTeZhengMa(id); //人员id==图片id
//                       subject2.setId(System.currentTimeMillis());
//                       subjectBox.put(subject2);
                       kaimen.append(id);
                       kaimen.append(",");
                     //  Log.d("MyService", "单个员工入库成功"+subject2.toString());
                   }else {
                       Log.d("MyService", "已经在库中"+subject.toString());
                   }
//                    PaAccessFaceInfo face = paAccessControl.queryFaceById(id);
//                    if (face != null) {
//                        paAccessControl.deleteFaceById(face.faceId);
//                        Subject subject = subjectBox.query().equal(Subject_.teZhengMa, id).build().findUnique();
//                        if (subject!=null)
//                            subjectBox.remove(subject);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   // paAccessControl.startFrameDetect();
                    kaimen.append(id);
                    kaimen.append(",");
                  //  return requsBean(-1,e+"");
                }
            }else {
                //paAccessControl.startFrameDetect();
                kaimen.append(id);
                kaimen.append(",");
               // return requsBean(-1,"质量检测失败");
            }
        }
        paAccessControl.startFrameDetect();
        if (kaimen.length()>0){
            Log.d("MyService", kaimen.toString());
            kaimen.delete(kaimen.length()-1,kaimen.length());
            return requsBean(0,kaimen.toString());
        }else {
            return requsBean(0,"成功");
        }
    }


    @PostMapping("/batchAddFace")
    public String batchAddFace(
            @RequestBody BitahFaceBean bitahFaceBean) {
        if (paAccessControl==null)
            return requsBean(-1,"识别算法未初始化");
        try {
            if (bitahFaceBean==null)
                return requsBean(-1,"数据为空");
            for (BitahFaceBean.ResultBean object : bitahFaceBean.getResult()){
                Subject subject = new Subject();
                subject.setId(System.currentTimeMillis());
                subject.setTeZhengMa(object.getId());
                subject.setDepartmentName(object.getDepartmentNa());
                subject.setName(object.getName());
                subjectBox.put(subject);
            }
            Log.d("MyService", "subjectBox.getAll().size():" + subjectBox.getAll().size());
            return requsBean(0,"成功");
        }catch (Exception e){
            e.printStackTrace();
            paAccessControl.startFrameDetect();
            return requsBean(-1,e+"");
        }
    }

    @PostMapping("/addIDcards")
    public String addIDcards( @RequestBody IDBean idBean) {
        try {
            if (idBean==null)
                return requsBean(-1,"数据为空");
            Box<IDCardBean> idCardBeanBox=MyApplication.myApplication.getIdCardBeanBox();
            for (IDBean.ResultBean object : idBean.getResult()){
                IDCardBean ii =new IDCardBean();
                ii.setId(System.currentTimeMillis());
                ii.setIdCard(object.getId());
                idCardBeanBox.put(ii);
            }
            Log.d("MyService", "subjectBox.getAll().size():" + idCardBeanBox.getAll().size());
            return requsBean(0,"成功");
        }catch (Exception e){
            e.printStackTrace();
            paAccessControl.startFrameDetect();
            return requsBean(-1,e+"");
        }
    }

    @PostMapping("/deleteIDcards")
    public String deleteIDcards(
            @RequestBody IDBean idBean) {
        try {
            if (idBean==null)
                return requsBean(-1,"数据为空");
            Box<IDCardBean> idCardBeanBox=MyApplication.myApplication.getIdCardBeanBox();
            for (IDBean.ResultBean object : idBean.getResult()){
                 List<IDCardBean> idCardBeanList = idCardBeanBox.query().equal(IDCardBean_.idCard,object.getId()).build().find();
                 for (IDCardBean dd : idCardBeanList){
                     idCardBeanBox.remove(dd);
                 }
            }
            Log.d("MyService", "subjectBox.getAll().size():" + idCardBeanBox.getAll().size());
            return requsBean(0,"成功");
        }catch (Exception e){
            e.printStackTrace();
            paAccessControl.startFrameDetect();
            return requsBean(-1,e+"");
        }
    }

    //删除全部人员
    @GetMapping(path = "/excel")
    public void getFile(HttpResponse response,@QueryParam(name = "time",required = false) String time){
       // Log.d("MyService", time+"time");
       // Log.d("MyService", "getMonthLastDay(2019,8):" + getMonthLastDay(2019,9));
        if (time!=null && !time.equals("")){
            String []ll = time.split("-");
            if (ll.length==2){
                try {
                    long min=0,max=0;
                    int t1= Integer.parseInt(ll[0]);
                    int t2= Integer.parseInt(ll[1]);
                    min = Long.parseLong(DateUtils.dataOne(time+"-"+1));//从这个月的一号开始
                    //获取这个月有多少天
                   // Log.d("MyService",min+"开始时间");
                    int ssdd=getMonthLastDay(t1,t2);
                    max = Long.parseLong(DateUtils.dataOne(time+"-"+ssdd));//这个月的最后一天
                   // Log.d("MyService",max+"结束时间");
                   // Log.d("MyService", DateUtils.time(min+""));
                   // Log.d("MyService", DateUtils.time(max+""));
                    if (min<=0 || max <= 0){
                        StringBody body=new StringBody("时间格式错误");
                        response.setBody(body);
                    }else {
                        //时间算好了开始查询
                        //创建文件夹
                        File ff = new File(MyApplication.SDPATH+File.separator+ "zips");
                        if (!ff.exists()){
                           Log.d(TAG, "创建zips文件夹:" + ff.mkdirs());
                        }
                        boolean flag=false;
                        LazyList<Subject> subjectLazyList = subjectBox.query().build().findLazy();
                        if (subjectLazyList.size()>0){
                            for (Subject subject:subjectLazyList){
                                LazyList<DaKaBean> daKaBeanList = daKaBeanBox.query().equal(DaKaBean_.id2, subject.getTeZhengMa())
                                        .between(DaKaBean_.time2,min,max)//过滤给定2者之间值
                                        .build().findLazy();
                                if (daKaBeanList.size()>0){
                                    String fileName = subject.getName()+DateUtils.timeHore(System.currentTimeMillis()+"")+".xls";
                                    File file = new File(MyApplication.SDPATH+File.separator+"zips"+File.separator+fileName);
                                    //文件夹是否已经存在
                                    if (file.exists()) {
                                       boolean ss = file.delete();
                                    }
                                    String[] title = {"ID", "姓名", "部门","时间"};
                                    initExcel(file.toString(), title);
                                    WritableWorkbook writebook = null;
                                    InputStream in = null;
                                    try {
                                        WorkbookSettings setEncode = new WorkbookSettings();
                                        setEncode.setEncoding(UTF8_ENCODING);
                                        in = new FileInputStream(file);
                                        Workbook workbook = Workbook.getWorkbook(in);
                                        writebook = Workbook.createWorkbook(file, workbook);
                                        WritableSheet sheet = writebook.getSheet(0);
                                        for (int j = 0; j < daKaBeanList.size(); j++) {
                                            DaKaBean projectBean =  daKaBeanList.get(j);
                                            List<String> list = new ArrayList<>();
                                            list.add(projectBean.getId2());
                                            list.add(projectBean.getName());
                                            list.add(projectBean.getBumen()+"");
                                            list.add(DateUtils.time(projectBean.getTime2()+""));
                                            for (int i = 0; i < list.size(); i++) {
                                                sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                                                if (list.get(i).length() <= 4) {
                                                    //设置列宽
                                                    sheet.setColumnView(i, list.get(i).length() + 8);
                                                } else {
                                                    //设置列宽
                                                    sheet.setColumnView(i, list.get(i).length() + 5);
                                                }
                                            }
                                            //设置行高
                                            sheet.setRowView(j + 1, 350);
                                        }
                                        writebook.write();
                                        flag=true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        StringBody body=new StringBody(e.getMessage()+"");
                                        response.setBody(body);
                                    } finally {
                                        if (writebook != null) {
                                            try {
                                                writebook.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (in != null) {
                                            try {
                                                in.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                            //循环完了，压缩文件；
                          //  File sourceFile = new File(MyApplication.SDPATH+File.separator+"zips");
                          //  File zipFile_ = new File(MyApplication.SDPATH);
                            // 生成的压缩文件
                            ZipFile zipFile = new ZipFile(MyApplication.SDPATH+
                                    File.separator+"刷脸记录.zip");
                            if (zipFile.getFile().exists()){
                                Log.d(TAG, "删除存在的zip:" + zipFile.getFile().delete());
                            }
                            ZipParameters parameters = new ZipParameters();
                            // 压缩方式
                            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                            // 压缩级别
                            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                            // 要打包的文件夹
                            File currentFile = new File(MyApplication.SDPATH+File.separator+"zips");
                            File[] fs = currentFile.listFiles();
                            if (fs!=null){
                                // 遍历test文件夹下所有的文件、文件夹
                                for (File f : fs) {
                                    if (f.isDirectory()) {
                                        zipFile.addFolder(f.getPath(), parameters);
                                    } else {
                                        zipFile.addFile(f, parameters);
                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.d(TAG, "删除zips文件夹:" + FileUtil.delete(ff.getCanonicalPath()));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                                if (flag){
                                    FileBody body = new FileBody(zipFile.getFile());
                                    response.addHeader("Content-Disposition", "attachment;filename="+zipFile.getFile().getName());
                                    response.setBody(body);
                                }else {
                                    StringBody body=new StringBody("没有该时间的刷脸记录");
                                    response.setBody(body);
                                }
                            }else {
                                StringBody body=new StringBody("压缩错误");
                                response.setBody(body);
                            }
                        }else {
                            StringBody body=new StringBody("暂无刷脸数据");
                            response.setBody(body);
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    StringBody body=new StringBody("时间格式错误");
                    response.setBody(body);
                }
            }else {
                StringBody body=new StringBody("时间格式错误");
                response.setBody(body);

            }

        }else {
           //获取当月时间
            try {
                long min=0,max=0;
                String time2 = DateUtils.tim(System.currentTimeMillis() + "");
                String[] ssyy = time2.split("-");
                int t1 = Integer.parseInt(ssyy[0]);
                int t2 = Integer.parseInt(ssyy[1]);
                min = Long.parseLong(DateUtils.dataOne(time2 + "-" + 1));//从这个月的一号开始
                //获取这个月有多少天
               // Log.d("MyService", min + "开始时间");
                int ssdd = getMonthLastDay(t1, t2);
                max = Long.parseLong(DateUtils.dataOne(time2 + "-" + ssdd));//这个月的最后一天
               // Log.d("MyService", max + "结束时间");
               // Log.d("MyService", DateUtils.time(min + ""));
               // Log.d("MyService", DateUtils.time(max + ""));
                if (min <= 0 || max <= 0) {
                    StringBody body = new StringBody("时间格式错误");
                    response.setBody(body);
                } else {
                    File ff = new File(MyApplication.SDPATH + File.separator + "zips");
                    if (!ff.exists()) {
                        Log.d(TAG, "创建zips文件夹:" + ff.mkdirs());
                    }
                    boolean flag = false;
                    LazyList<Subject> subjectLazyList = subjectBox.query().build().findLazy();
                    if (subjectLazyList.size() > 0) {
                        for (Subject subject : subjectLazyList) {
                            LazyList<DaKaBean> daKaBeanList = daKaBeanBox.query().equal(DaKaBean_.id2, subject.getTeZhengMa())
                                    .between(DaKaBean_.time2, min, max)//过滤给定2者之间值
                                    .build().findLazy();
                            if (daKaBeanList.size() > 0) {
                                String fileName = subject.getName() + DateUtils.timeHore(System.currentTimeMillis() + "") + ".xls";
                                File file = new File(MyApplication.SDPATH + File.separator + "zips" + File.separator + fileName);
                                //文件夹是否已经存在
                                if (file.exists()) {
                                    boolean ss = file.delete();
                                }
                                String[] title = {"ID", "姓名", "部门", "时间"};
                                initExcel(file.toString(), title);
                                WritableWorkbook writebook = null;
                                InputStream in = null;
                                try {
                                    WorkbookSettings setEncode = new WorkbookSettings();
                                    setEncode.setEncoding(UTF8_ENCODING);
                                    in = new FileInputStream(file);
                                    Workbook workbook = Workbook.getWorkbook(in);
                                    writebook = Workbook.createWorkbook(file, workbook);
                                    WritableSheet sheet = writebook.getSheet(0);
                                    for (int j = 0; j < daKaBeanList.size(); j++) {
                                        DaKaBean projectBean = daKaBeanList.get(j);
                                        List<String> list = new ArrayList<>();
                                        list.add(projectBean.getId2());
                                        list.add(projectBean.getName());
                                        list.add(projectBean.getBumen() + "");
                                        list.add(DateUtils.time(projectBean.getTime2() + ""));
                                        for (int i = 0; i < list.size(); i++) {
                                            sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                                            if (list.get(i).length() <= 4) {
                                                //设置列宽
                                                sheet.setColumnView(i, list.get(i).length() + 8);
                                            } else {
                                                //设置列宽
                                                sheet.setColumnView(i, list.get(i).length() + 5);
                                            }
                                        }
                                        //设置行高
                                        sheet.setRowView(j + 1, 350);
                                    }
                                    writebook.write();
                                    flag = true;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    StringBody body = new StringBody(e.getMessage() + "");
                                    response.setBody(body);
                                } finally {
                                    if (writebook != null) {
                                        try {
                                            writebook.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (in != null) {
                                        try {
                                            in.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        //循环完了，压缩文件；
                        // 生成的压缩文件
                        ZipFile zipFile = new ZipFile(MyApplication.SDPATH +
                                File.separator + "刷脸记录.zip");
                        if (zipFile.getFile().exists()) {
                            Log.d(TAG, "删除存在的zip:" + zipFile.getFile().delete());
                        }
                        ZipParameters parameters = new ZipParameters();
                        // 压缩方式
                        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                        // 压缩级别
                        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                        // 要打包的文件夹
                        File currentFile = new File(MyApplication.SDPATH + File.separator + "zips");
                        File[] fs = currentFile.listFiles();
                        if (fs != null) {
                            // 遍历test文件夹下所有的文件、文件夹
                            for (File f : fs) {
                                if (f.isDirectory()) {
                                    zipFile.addFolder(f.getPath(), parameters);
                                } else {
                                    zipFile.addFile(f, parameters);
                                }
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d(TAG, "删除zips文件夹:" + FileUtil.delete(ff.getCanonicalPath()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            if (flag) {
                                FileBody body = new FileBody(zipFile.getFile());
                                response.addHeader("Content-Disposition", "attachment;filename=" + zipFile.getFile().getName());
                                response.setBody(body);
                            } else {
                                StringBody body = new StringBody("没有该时间的刷脸记录");
                                response.setBody(body);
                            }
                        } else {
                            StringBody body = new StringBody("压缩错误");
                            response.setBody(body);
                        }
                    } else {
                        StringBody body = new StringBody("暂无刷脸数据");
                        response.setBody(body);
                    }
                }
                }catch(Exception e){
                    e.printStackTrace();
                    StringBody body = new StringBody(e.getMessage()+"");
                    response.setBody(body);
                }

        }

      //   FileBody body = new FileBody(file);
        // response.addHeader("Content-Disposition", "attachment;filename=AndServer.txt");
        // response.setBody(body);
        //这里我们添加了一个Content-Disposition的响应头，attachment的意思是告诉浏览器，
        // 这个文件应该被下载，filename=AndServer.txt的意思是告诉浏览器，这个文件默认被命名为AndServer.txt。
    }

    /**
     * 得到指定月的天数
     * */
    public static int getMonthLastDay(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    private String requsBean(int code,String msg){
        return JSON.toJSONString(new ResBean(code,msg,serialnumber));
    }

    private  Bitmap readInputStreamToBitmap(InputStream ins, long fileSize) {
        if (ins == null) {
            return null;
        }
        byte[] b;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int size = -1;
            int len = 0;// 已经接收长度
            size = ins.read(buffer);
            while (size != -1) {
                len = len + size;//
                bos.write(buffer, 0, size);
                if (fileSize == len) {// 接收完毕
                    break;
                }
                size = ins.read(buffer);
            }
            b = bos.toByteArray();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    /**
     * 初始化Excel
     *
     * @param fileName 导出excel存放的地址（目录）
     * @param colName excel中包含的列名（可以有多个）
     */
    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                boolean ss=  file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet("刷脸记录", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 校验提取出的原文件名字是否带格式
     * @param sourceFileName 要压缩的文件名
     * @return
     */
    private String checkString(String sourceFileName){
        if (sourceFileName.indexOf(".") > 0){
            sourceFileName = sourceFileName.substring(0,sourceFileName.length() - 4);
            Log.i(TAG, "checkString: 校验过的sourceFileName是：" + sourceFileName);
        }
        return sourceFileName;
    }

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);


        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
