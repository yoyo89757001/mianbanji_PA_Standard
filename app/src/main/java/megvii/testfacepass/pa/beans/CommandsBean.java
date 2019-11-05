package megvii.testfacepass.pa.beans;

import java.util.List;

public class CommandsBean {


    /**
     * code : 2000
     * desc : Success
     * "result" : [{"type":0,"name":"交友"},{"type":1,"name":"户外"},{"type":2,"name":"校园"},{"type":3,"name":"音乐"}]
     * total : 0
     */

    private int code;
    private String desc;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


    public static class ResultBean {
        /**
         * type : 0
         * name : 交友
         */
        private int command;//指令要做什么 1001.新增。1002。修改 1003。删除单个 1004。删除所有 。1005人员同步
        private String id;//增删改需要的人员id
        private String methodName;

        public int getCommand() {
            return command;
        }

        public void setCommand(int command) {
            this.command = command;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }
}
