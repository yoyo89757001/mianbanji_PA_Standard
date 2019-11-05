package megvii.testfacepass.pa.beans;

import java.util.List;

public class BitahFaceBean {



    private List<ResultBean> result;

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
        private String id;//增删改需要的人员id
        private String name;
        private String departmentNa;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartmentNa() {
            return departmentNa;
        }

        public void setDepartmentNa(String departmentNa) {
            this.departmentNa = departmentNa;
        }
    }
}
