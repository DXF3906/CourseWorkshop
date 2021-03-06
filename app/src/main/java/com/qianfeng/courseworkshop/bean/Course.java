package com.qianfeng.courseworkshop.bean;

/**
 * Course课程实体类
 * fdb
 * Created by asus on 2016/7/6.
 */

public class Course {
    private String imgUrlStr;//图片url
    private String title;//标题名字
    private String studyNum;//学习人数
    private String money;//k币
    private String description;//简介
    private String courseUrl;//用于跳转的网站

    public Course() {
    }

    public Course(String studyNum, String imgUrlStr, String title, String money,String description,String courseUrl) {
        this.studyNum = studyNum;
        this.imgUrlStr = imgUrlStr;
        this.title = title;
        this.money = money;
        this.description=description;
        this.courseUrl=courseUrl;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getImgUrlStr() {
        return imgUrlStr;
    }

    public void setImgUrlStr(String imgUrlStr) {
        this.imgUrlStr = imgUrlStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "imgUrlStr='" + imgUrlStr + '\'' +
                ", title='" + title + '\'' +
                ", studyNum='" + studyNum + '\'' +
                ", money='" + money + '\'' +
                ", description='" + description + '\'' +
                ", courseUrl='" + courseUrl + '\'' +
                '}';
    }
}
