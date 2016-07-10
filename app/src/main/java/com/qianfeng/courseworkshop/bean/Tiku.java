package com.qianfeng.courseworkshop.bean;

/**
 * Course课程实体类
 * fdb
 * Created by asus on 2016/7/6.
 */

public class Tiku {
    private String chooseType;//试题类型
    private String description;//题目描述
    private String jibie;//二级c
    private String defaultTi;//试题难度
    private String studyNum;//学习人数
    private String pinlunNum;//评论人数
    private String tikuUrl;//试题网址

    public Tiku() {
    }

    public Tiku(String chooseType, String tikuUrl, String pinlunNum, String studyNum, String defaultTi, String jibie, String description) {
        this.chooseType = chooseType;
        this.tikuUrl = tikuUrl;
        this.pinlunNum = pinlunNum;
        this.studyNum = studyNum;
        this.defaultTi = defaultTi;
        jibie = jibie;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChooseType() {
        return chooseType;
    }

    public void setChooseType(String chooseType) {
        this.chooseType = chooseType;
    }

    public String getJibie() {
        return jibie;
    }

    public void setJibie(String jibie) {
        jibie = jibie;
    }

    public String getDefaultTi() {
        return defaultTi;
    }

    public void setDefaultTi(String defaultTi) {
        this.defaultTi = defaultTi;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

    public String getPinlunNum() {
        return pinlunNum;
    }

    public void setPinlunNum(String pinlunNum) {
        this.pinlunNum = pinlunNum;
    }

    public String getTikuUrl() {
        return tikuUrl;
    }

    public void setTikuUrl(String tikuUrl) {
        this.tikuUrl = tikuUrl;
    }

    @Override
    public String toString() {
        return "Tiku{" +
                "chooseType='" + chooseType + '\'' +
                ", description='" + description + '\'' +
                ", jibie='" + jibie + '\'' +
                ", defaultTi='" + defaultTi + '\'' +
                ", studyNum='" + studyNum + '\'' +
                ", pinlunNum='" + pinlunNum + '\'' +
                ", tikuUrl='" + tikuUrl + '\'' +
                '}';
    }
}
