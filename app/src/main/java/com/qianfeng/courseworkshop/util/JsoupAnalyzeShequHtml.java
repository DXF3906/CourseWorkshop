package com.qianfeng.courseworkshop.util;


import com.qianfeng.courseworkshop.bean.Course;
import com.qianfeng.courseworkshop.bean.Tiezi;

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

public class JsoupAnalyzeShequHtml {
    //解析本地缓存的文件，返回Course数据集合
    public static List<Tiezi> analysTiezis(File file) {
        List<Tiezi> tiezis = new LinkedList<>();
        try {
            //1.获取文档对象
            //TODO
            Document doc = Jsoup.parse(file, "utf-8");

            //2. 获取class="course_detail"的所有元素
            Elements tieziList = doc.getElementsByClass("yui3-u-1-12");
            //3.获取每个course实例的每个属性，并添加到集合courseLists中
            for (Element tieziItem : tieziList) {
                //课程实例
                Tiezi tiezi = new Tiezi();


                //图片url
                Elements imgs = tieziItem.select("img");
                Element img = imgs.get(0);
                String imgUrlStr = img.attr("src");





                tiezi.setImgUrl(imgUrlStr);


                tiezis.add(tiezi);


            }
            return tiezis;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiezis;
    }

}
