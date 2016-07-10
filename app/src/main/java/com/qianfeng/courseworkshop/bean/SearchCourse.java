package com.qianfeng.courseworkshop.bean;

/**
 * 搜索的课程类
 * Created by asus on 2016/7/10.
 */

public class SearchCourse {
    private String imgUrl;
    private String title;
    private String description;
    private String tag1;
    private String tag2;
    private String tag3;
    private String courseUrl;

    public SearchCourse() {
    }

    public SearchCourse(String imgUrl, String title, String description, String tag1, String tag2, String tag3, String courseUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.courseUrl = courseUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    @Override
    public String toString() {
        return "SearchCourse{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", tag3='" + tag3 + '\'' +
                ", courseUrl='" + courseUrl + '\'' +
                '}';
    }
}
