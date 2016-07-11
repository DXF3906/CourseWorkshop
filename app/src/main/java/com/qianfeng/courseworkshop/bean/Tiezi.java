package com.qianfeng.courseworkshop.bean;

/**
 * Course课程实体类
 * fdb
 * Created by asus on 2016/7/6.
 */

public class Tiezi {
    private String imgUrl;//图片url
    private String title;//标题名字
    private String time1;//简介
    private String time2;//用于跳转的网站
    private String studyNum;//学习人数
    private String pinglunNum;//评论人数
    private String tieziUrl;

    public Tiezi() {
    }

    public Tiezi(String imgUrl, String title, String time1, String time2, String studyNum, String pinglunNum, String tieziUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.time1 = time1;
        this.time2 = time2;
        this.studyNum = studyNum;
        this.pinglunNum = pinglunNum;
        this.tieziUrl = tieziUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTieziUrl() {
        return tieziUrl;
    }

    public void setTieziUrl(String tieziUrl) {
        this.tieziUrl = tieziUrl;
    }

    public String getPinglunNum() {
        return pinglunNum;
    }

    public void setPinglunNum(String pinglunNum) {
        this.pinglunNum = pinglunNum;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Tiezi{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", studyNum='" + studyNum + '\'' +
                ", pinglunNum='" + pinglunNum + '\'' +
                ", tieziUrl='" + tieziUrl + '\'' +
                '}';
    }
}
