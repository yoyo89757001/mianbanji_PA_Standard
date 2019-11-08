package megvii.testfacepass.pa.tuisong_jg;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.yanzhenjie.andserver.annotation.RequestBody;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.objectbox.Box;
import megvii.testfacepass.pa.MyApplication;
import megvii.testfacepass.pa.beans.BitahFaceBean;
import megvii.testfacepass.pa.beans.IDBean;
import megvii.testfacepass.pa.beans.IDCardBean;
import megvii.testfacepass.pa.beans.IDCardBean_;
import megvii.testfacepass.pa.beans.ResBean;
import megvii.testfacepass.pa.beans.Subject;
import megvii.testfacepass.pa.beans.Subject_;
import megvii.testfacepass.pa.utils.BitmapUtil;
import megvii.testfacepass.pa.utils.GetDeviceId;



@RestController
@RequestMapping(path = "/app")
public class MyService {

    private Box<Subject> subjectBox  = MyApplication.myApplication.getSubjectBox();;
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
                         @RequestParam(name = "departmentName")String bumen,
                         @RequestParam(name = "pepopleType")String pepopleType,
                         @RequestParam(name = "image") MultipartFile file
                ) throws IOException {
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
}
