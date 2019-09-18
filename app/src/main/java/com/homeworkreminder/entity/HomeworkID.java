package com.homeworkreminder.entity;

import java.util.List;

public class HomeworkID {

    /**
     * code : 200
     * message : ok
     * data : [{"t_hid":15}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * t_hid : 15
         */

        private int t_hid;

        public int getT_hid() {
            return t_hid;
        }

        public void setT_hid(int t_hid) {
            this.t_hid = t_hid;
        }
    }
}
