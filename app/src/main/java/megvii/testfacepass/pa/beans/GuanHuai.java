package megvii.testfacepass.pa.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class GuanHuai {


    /**
     * spareStatus : 0
     * companyId : G1002
     * projectileStatus : 1
     * machineS1 :
     * name : 欧阳
     * startTime : 2018-10-09 00:00
     * id : 1049584249319960576
     * endTime : 2018-10-09 23:59
     * markedWords : 欧阳生日快乐
     * timeRange : 2018-10-09 00:00 ~ 2018-10-09 23:59
     */

    @Id(assignable = true)
    private Long id;
    private Long employeeId;
    private int spareStatus;
    private String companyId;
    private String projectileStatus;
    private String machineS1;
    private String name;
    private String startTime;
    private String endTime;
    private String markedWords;
    private String timeRange;
    private String repeatType;
    private String newsStatus;

    public GuanHuai() {
    }

    public GuanHuai(String projectileStatus, String markedWords, String newsStatus) {
        this.projectileStatus = projectileStatus;
        this.markedWords = markedWords;
        this.newsStatus = newsStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }


    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }


    public int getSpareStatus() {
        return spareStatus;
    }

    public void setSpareStatus(int spareStatus) {
        this.spareStatus = spareStatus;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectileStatus() {
        return projectileStatus;
    }

    public void setProjectileStatus(String projectileStatus) {
        this.projectileStatus = projectileStatus;
    }

    public String getMachineS1() {
        return machineS1;
    }

    public void setMachineS1(String machineS1) {
        this.machineS1 = machineS1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMarkedWords() {
        return markedWords;
    }

    public void setMarkedWords(String markedWords) {
        this.markedWords = markedWords;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
}
