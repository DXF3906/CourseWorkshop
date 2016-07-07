package com.qianfeng.courseworkshop.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 课程目录类
 * Created by asus on 2016/7/7.
 */

public class CourseAll implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private int errorType;
    private String iVersion;
    private CourseLogcat[] sortdata;


    public CourseAll() {
    }

    public CourseAll(int code, int errorType, String iVersion, CourseLogcat[] sortdata) {
        this.code = code;
        this.errorType = errorType;
        this.iVersion = iVersion;
        this.sortdata = sortdata;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getiVersion() {
        return iVersion;
    }

    public void setiVersion(String iVersion) {
        this.iVersion = iVersion;
    }

    public CourseLogcat[] getSortdata() {
        return sortdata;
    }

    public void setSortdata(CourseLogcat[] sortdata) {
        this.sortdata = sortdata;
    }

    @Override
    public String toString() {
        return "CourseAll{" +
                "code=" + code +
                ", errorType=" + errorType +
                ", iVersion='" + iVersion + '\'' +
                ", sortdata=" + Arrays.toString(sortdata) +
                '}';
    }
}
