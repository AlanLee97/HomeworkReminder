package com.homeworkreminder.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfo {
    /**
     * code : 200
     * message : ok
     * data : [{"id":5,"username":"li","password":"123456","nickname":"AlanLee","school":"zdxh","major":"软件工程","class":"A班","create_time":"2019-09-15 12:03:16"}]
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
         * id : 5
         * username : li
         * password : 123456
         * nickname : AlanLee
         * school : zdxh
         * major : 软件工程
         * class : A班
         * create_time : 2019-09-15 12:03:16
         */

        private int id;
        private String username;
        private String password;
        private String nickname;
        private String school;
        private String major;
        @SerializedName("class")
        private String classX;
        private String create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", school='" + school + '\'' +
                    ", major='" + major + '\'' +
                    ", classX='" + classX + '\'' +
                    ", create_time='" + create_time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
