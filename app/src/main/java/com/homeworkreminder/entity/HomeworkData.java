package com.homeworkreminder.entity;

import java.util.List;

public class HomeworkData {

    /**
     * code : 200
     * message : ok
     * data : [{"id":16,"uid":5,"date":"2019-09-18 06:57:34","course":"PHP","title":"网页端测试10","content":"dsrfrs","tag":"PHP","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"li"},{"id":14,"uid":5,"date":"2019-09-18 06:17:04","course":"PHP","title":"网页端测试9","content":"grsdrrst","tag":"PHP","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"li"},{"id":12,"uid":4,"date":"2019-09-17 23:53:56","course":null,"title":"测试2343412","content":"2321343","tag":"4243","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"admin"},{"id":11,"uid":4,"date":"2019-09-16 21:36:19","course":null,"title":"网页端测试8","content":"23413rweqaewr4","tag":"软件测试","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"admin"},{"id":10,"uid":4,"date":"2019-09-16 21:35:06","course":null,"title":"网页端测试8","content":"234123342","tag":"软件测试","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"admin"},{"id":9,"uid":4,"date":"2019-09-16 21:31:11","course":null,"title":"网页端测试8","content":"fdgdsgssdf","tag":"软件测试","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"admin"},{"id":8,"uid":4,"date":"2019-09-16 21:18:10","course":null,"title":"网页端测试7","content":"43535346524364524","tag":"软件测试","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"admin"},{"id":7,"uid":1,"date":"2019-09-15 20:10:54","course":null,"title":"网页端测试6","content":"465465456","tag":"Android","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"aa"},{"id":5,"uid":3,"date":"2019-09-11 14:13:41","course":null,"title":"网页端测试3","content":"测试内容sdnnfjnkjhewer","tag":"Android","deadtime":null,"remind_date":"2019-09-20","remind_time":"21:00","username":"libuguan"},{"id":4,"uid":5,"date":"2019-09-11 14:04:50","course":"软件测试","title":"软件测试","content":"用java编写两个程序","tag":"Android","deadtime":"第四周交","remind_date":"2019-09-20","remind_time":"21:00","username":"li"},{"id":2,"uid":5,"date":"2019-09-10 21:41:13","course":"Android","title":"Android移动应用程序设计","content":"书本P20","tag":"Android","deadtime":null,"remind_date":"2019-09-10","remind_time":"21:00","username":"li"}]
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
         * id : 16
         * uid : 5
         * date : 2019-09-18 06:57:34
         * course : PHP
         * title : 网页端测试10
         * content : dsrfrs
         * tag : PHP
         * deadtime : null
         * remind_date : 2019-09-20
         * remind_time : 21:00
         * username : li
         */

        private int id;
        private int uid;
        private String date;
        private String course;
        private String title;
        private String content;
        private String tag;
        private String deadtime;
        private String remind_date;
        private String remind_time;
        private String username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
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

        public String getDeadtime() {
            return deadtime;
        }

        public void setDeadtime(String deadtime) {
            this.deadtime = deadtime;
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
