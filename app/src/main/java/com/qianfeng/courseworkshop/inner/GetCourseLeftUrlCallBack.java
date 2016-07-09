package com.qianfeng.courseworkshop.inner;


/**
 * 接口回调,课程侧滑ListView点击回调对应的网站
 * Created by asus on 2016/7/9.
 */

public interface GetCourseLeftUrlCallBack {
    //回调数据为通过异步任务下载的文件名
    void gettCourseLeftUrl(String  courseUrl);
}
