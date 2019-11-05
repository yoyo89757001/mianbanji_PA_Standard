package megvii.testfacepass.pa.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Uid;

/**
 * Created by Administrator on 2017/9/15.
 */
@Entity

public class BaoCunBean {
    @Id(assignable = true)
    private Long id;
    private int port;//端口
    private String zhujiDiZhi;
    private int moban;
    private String tuisongDiZhi; //用来存信鸽token
    private String gonggao;
    private boolean isShowMoshengren;
    private boolean isShowShiPingLiu;
    private boolean isHengOrShu;
    private int yusu;
    private int yudiao;
    private int boyingren;
    private String zhanghuId;
    private String wenzi;
    private int size;
    private String touxiangzhuji;
    private String houtaiDiZhi;
    private String huiyiId;
    private String wenzi1;
    private int size1;
    private String guanggaojiMing;
    private String shiPingWeiZhi;
    private String zhanhuiId;
    private String zhanhuiBianMa;
    private float shibieFaZhi;
    private int shibieFaceSize;
    private float ruKuMoHuDu;
    private int ruKuFaceSize;
    private boolean isHuoTi;
    private int huoTiFZ;
    private String shualianhuidiaodizhi;
    private String dangqianChengShi2;
    private String dangqianShiJian;
    private boolean isTianQi;
    private String shangBanTime;
    private String xiaBanTime;
    private int mima;
    private int mima2;
    private String xgToken;
    private String jihuoma;
    private String appid;
    private String appkey;
    private String appurl;
    private String tishiyu;
    private String name;
    private String leixing;
    private String weizhi;
    private String ip;
    private String xuliehao;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getXuliehao() {
        return xuliehao;
    }

    public void setXuliehao(String xuliehao) {
        this.xuliehao = xuliehao;
    }

    public String getTishiyu() {
        return tishiyu;
    }

    public void setTishiyu(String tishiyu) {
        this.tishiyu = tishiyu;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getJihuoma() {
        return jihuoma;
    }

    public void setJihuoma(String jihuoma) {
        this.jihuoma = jihuoma;
    }

    public String getXgToken() {
        return xgToken;
    }

    public void setXgToken(String xgToken) {
        this.xgToken = xgToken;
    }

    public int getMima2() {
        return mima2;
    }

    public void setMima2(int mima2) {
        this.mima2 = mima2;
    }

    public int getMima() {
        return mima;
    }

    public void setMima(int mima) {
        this.mima = mima;
    }

    public String getShangBanTime() {
        return shangBanTime;
    }

    public void setShangBanTime(String shangBanTime) {
        this.shangBanTime = shangBanTime;
    }

    public String getXiaBanTime() {
        return xiaBanTime;
    }

    public void setXiaBanTime(String xiaBanTime) {
        this.xiaBanTime = xiaBanTime;
    }

    public String getDangqianShiJian() {
        return dangqianShiJian;
    }

    public void setDangqianShiJian(String dangqianShiJian) {
        this.dangqianShiJian = dangqianShiJian;
    }

    public boolean isTianQi() {
        return isTianQi;
    }

    public void setTianQi(boolean tianQi) {
        isTianQi = tianQi;
    }

    public String getDangqianChengShi2() {
        return dangqianChengShi2;
    }

    public void setDangqianChengShi2(String dangqianChengShi2) {
        this.dangqianChengShi2 = dangqianChengShi2;
    }

    public String getShualianhuidiaodizhi() {
        return shualianhuidiaodizhi;
    }

    public void setShualianhuidiaodizhi(String shualianhuidiaodizhi) {
        this.shualianhuidiaodizhi = shualianhuidiaodizhi;
    }

    public int getHuoTiFZ() {
        return huoTiFZ;
    }

    public void setHuoTiFZ(int huoTiFZ) {
        this.huoTiFZ = huoTiFZ;
    }

    public float getShibieFaZhi() {
        return shibieFaZhi;
    }

    public void setShibieFaZhi(float shibieFaZhi) {
        this.shibieFaZhi = shibieFaZhi;
    }

    public int getShibieFaceSize() {
        return shibieFaceSize;
    }

    public void setShibieFaceSize(int shibieFaceSize) {
        this.shibieFaceSize = shibieFaceSize;
    }

    public float getRuKuMoHuDu() {
        return ruKuMoHuDu;
    }

    public void setRuKuMoHuDu(float ruKuMoHuDu) {
        this.ruKuMoHuDu = ruKuMoHuDu;
    }

    public int getRuKuFaceSize() {
        return ruKuFaceSize;
    }

    public void setRuKuFaceSize(int ruKuFaceSize) {
        this.ruKuFaceSize = ruKuFaceSize;
    }

    public boolean isHuoTi() {
        return isHuoTi;
    }

    public void setHuoTi(boolean huoTi) {
        isHuoTi = huoTi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getZhujiDiZhi() {
        return zhujiDiZhi;
    }

    public void setZhujiDiZhi(String zhujiDiZhi) {
        this.zhujiDiZhi = zhujiDiZhi;
    }

    public int getMoban() {
        return moban;
    }

    public void setMoban(int moban) {
        this.moban = moban;
    }

    public String getTuisongDiZhi() {
        return tuisongDiZhi;
    }

    public void setTuisongDiZhi(String tuisongDiZhi) {
        this.tuisongDiZhi = tuisongDiZhi;
    }

    public String getGonggao() {
        return gonggao;
    }

    public void setGonggao(String gonggao) {
        this.gonggao = gonggao;
    }

    public boolean isShowMoshengren() {
        return isShowMoshengren;
    }

    public void setShowMoshengren(boolean showMoshengren) {
        isShowMoshengren = showMoshengren;
    }

    public boolean isShowShiPingLiu() {
        return isShowShiPingLiu;
    }

    public void setShowShiPingLiu(boolean showShiPingLiu) {
        isShowShiPingLiu = showShiPingLiu;
    }

    public boolean isHengOrShu() {
        return isHengOrShu;
    }

    public void setHengOrShu(boolean hengOrShu) {
        isHengOrShu = hengOrShu;
    }

    public int getYusu() {
        return yusu;
    }

    public void setYusu(int yusu) {
        this.yusu = yusu;
    }

    public int getYudiao() {
        return yudiao;
    }

    public void setYudiao(int yudiao) {
        this.yudiao = yudiao;
    }

    public int getBoyingren() {
        return boyingren;
    }

    public void setBoyingren(int boyingren) {
        this.boyingren = boyingren;
    }

    public String getZhanghuId() {
        return zhanghuId;
    }

    public void setZhanghuId(String zhanghuId) {
        this.zhanghuId = zhanghuId;
    }

    public String getWenzi() {
        return wenzi;
    }

    public void setWenzi(String wenzi) {
        this.wenzi = wenzi;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTouxiangzhuji() {
        return touxiangzhuji;
    }

    public void setTouxiangzhuji(String touxiangzhuji) {
        this.touxiangzhuji = touxiangzhuji;
    }

    public String getHoutaiDiZhi() {
        return houtaiDiZhi;
    }

    public void setHoutaiDiZhi(String houtaiDiZhi) {
        this.houtaiDiZhi = houtaiDiZhi;
    }

    public String getHuiyiId() {
        return huiyiId;
    }

    public void setHuiyiId(String huiyiId) {
        this.huiyiId = huiyiId;
    }

    public String getWenzi1() {
        return wenzi1;
    }

    public void setWenzi1(String wenzi1) {
        this.wenzi1 = wenzi1;
    }

    public int getSize1() {
        return size1;
    }

    public void setSize1(int size1) {
        this.size1 = size1;
    }

    public String getGuanggaojiMing() {
        return guanggaojiMing;
    }

    public void setGuanggaojiMing(String guanggaojiMing) {
        this.guanggaojiMing = guanggaojiMing;
    }

    public String getShiPingWeiZhi() {
        return shiPingWeiZhi;
    }

    public void setShiPingWeiZhi(String shiPingWeiZhi) {
        this.shiPingWeiZhi = shiPingWeiZhi;
    }

    public String getZhanhuiId() {
        return zhanhuiId;
    }

    public void setZhanhuiId(String zhanhuiId) {
        this.zhanhuiId = zhanhuiId;
    }

    public String getZhanhuiBianMa() {
        return zhanhuiBianMa;
    }

    public void setZhanhuiBianMa(String zhanhuiBianMa) {
        this.zhanhuiBianMa = zhanhuiBianMa;
    }
}
