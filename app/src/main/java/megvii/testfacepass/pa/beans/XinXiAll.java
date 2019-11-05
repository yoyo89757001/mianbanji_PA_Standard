package megvii.testfacepass.pa.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class XinXiAll {


    /**
     * companyId : G1001
     * pushScope : 1
     * editNews : 军总测试
     * name : 全体人员
     * employeeId : 0
     * startTime : Mon Nov 19 16:10:00 GMT+08:00 2018
     * id : 1064430823498362880
     * endTime : Thu Nov 29 16:10:00 GMT+08:00 2018
     * pushMachine : 1041879416760995840
     */

    private String companyId;
    private String pushScope;
    private String editNews;
    private String name;
    private String employeeId;
    private long startTime;
    @Id(assignable = true)
    private Long id;
    private String endTime;
    private String pushMachine;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPushScope() {
        return pushScope;
    }

    public void setPushScope(String pushScope) {
        this.pushScope = pushScope;
    }

    public String getEditNews() {
        return editNews;
    }

    public void setEditNews(String editNews) {
        this.editNews = editNews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPushMachine() {
        return pushMachine;
    }

    public void setPushMachine(String pushMachine) {
        this.pushMachine = pushMachine;
    }
}
