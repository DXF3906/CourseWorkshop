package com.qianfeng.courseworkshop.bean;

import java.io.Serializable;

/**
 * 课程目录类
 * fdb
 * Created by asus on 2016/7/7.
 */

public class CourseLogcat implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String total;
    private String img;
    private int isJob;
    private String pid;
    private String catalogId;//链接

    public CourseLogcat() {
    }

    public CourseLogcat(String catalogId, String pid, int isJob, String img, String total, String name) {
        this.catalogId = catalogId;
        this.pid = pid;
        this.isJob = isJob;
        this.img = img;
        this.total = total;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getIsJob() {
        return isJob;
    }

    public void setIsJob(int isJob) {
        this.isJob = isJob;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Override
    public String toString() {
        return "CourseLogcat{" +
                "name='" + name + '\'' +
                ", total='" + total + '\'' +
                ", img='" + img + '\'' +
                ", isJob=" + isJob +
                ", pid='" + pid + '\'' +
                ", catalogId='" + catalogId + '\'' +
                '}';
    }
}
