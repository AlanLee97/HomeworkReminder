package com.t.testrecyclerviewinfragment.entity;

/**
 * Created by 段仕浩 on 2018/5/26
 * 个人数据对象
 */
public class HomeData {

    //属性
    private String nickname;
    private int img;
    private String date;
    private String title;
    private String content;
    private String tag;

    public HomeData(String nickname, int img, String date, String title, String content, String tag) {
        this.nickname = nickname;
        this.img = img;
        this.date = date;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
}
