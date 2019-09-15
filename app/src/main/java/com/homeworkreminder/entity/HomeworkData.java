package com.homeworkreminder.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeworkData {
    /**
     * code : 200
     * message : ok
     * data : [{"id":1,"date":"2019-09-10 21:40:06","title":"JavaEE作业","content":"书本P29","tag":"JavaEE","uid":null,"remind_date":"2019-09-10","remind_time":"21:00"},{"id":2,"date":"2019-09-10 21:41:13","title":"Android移动应用程序设计","content":"书本P20","tag":"Android","uid":null,"remind_date":"2019-09-10","remind_time":"21:00"}]
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
         * id : 1
         * date : 2019-09-10 21:40:06
         * title : JavaEE作业
         * content : 书本P29
         * tag : JavaEE
         * remind_date : 2019-09-10
         * remind_time : 21:00
         * username : aaaaaa
         */

        private int id;
        private String date;
        private String title;
        private String content;
        private String tag;
        private String remind_date;
        private String remind_time;
        private String username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getRemind_date() {
            return remind_date;
        }

        public void setRemind_date(String remind_date) {
            this.remind_date = remind_date;
        }

        public String getRemind_time() {
            return remind_time;
        }

        public void setRemind_time(String remind_time) {
            this.remind_time = remind_time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
