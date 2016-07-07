package com.qianfeng.courseworkshop.util;


import com.qianfeng.courseworkshop.bean.Course;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 解析html格式数据的工具类
 * fdb
 * Created by asus on 2016/7/6.
 */

public class JsoupAnalyzeHtml {
    //解析本地缓存的文件，返回Course数据集合
    public static List<Course> analysCourses(File file) {
        List<Course> courseDatas = new LinkedList<>();
        try {
            //1.获取文档对象
            Document doc = Jsoup.parse(file, "utf-8", "view-source:http://www.kgc.cn/list.shtml");

            //2. 获取class="course_detail"的所有元素
            Elements courseList = doc.getElementsByClass("course_detail");
            //3.获取每个course实例的每个属性，并添加到集合courseLists中
            for (Element courseItem : courseList) {
                //课程实例
                Course course = new Course();

                //获得标题
                String title = courseItem.select("h2").text();

                //图片url
                Elements imgs = courseItem.select("img");
                Element img = imgs.get(0);
                String imgUrlStr = img.attr("src");

                //获得学习人数
                String studyNum = courseItem.select("span").get(1).text();

                //获得K币
                String money = courseItem.select("span").get(2).text();
                course.setTitle(title);
                course.setImgUrlStr(imgUrlStr);
                course.setStudyNum(studyNum);
                course.setMoney(money);
                courseDatas.add(course);

            }
            return courseDatas;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseDatas;
    }

}
