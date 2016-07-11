package com.qianfeng.courseworkshop.util;


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

public class JsoupAnalyzeShequHtml2 {
    //解析本地缓存的文件，返回Course数据集合
    public static List<Tiezi> analysTiezis(File file) {
        List<Tiezi> tiezis = new LinkedList<>();
        try {
            //1.获取文档对象
            //TODO
            Document doc = Jsoup.parse(file, "utf-8");

            //2. 获取class="course_detail"的所有元素
            Elements tieziList = doc.getElementsByClass("yui3-u-11-12");
            //3.获取每个course实例的每个属性，并添加到集合courseLists中
            for (Element tieziItem : tieziList) {
                //课程实例
                Tiezi tiezi = new Tiezi();




                //获得标题
                String title = tieziItem.select("h2").text();


                //获得学习人数
                String studyNum = tieziItem.select("span").get(0).text();
                //获得评论人数
                String pinglunNum = tieziItem.select("span").get(1).text();
                String time1="";
                String time2="";
                if (tieziItem.select("span").size()>4) {
                    //获得发表时间
                    time1 = tieziItem.select("span").get(3).text();
                    //获得回复时间
                    time2 = tieziItem.select("span").get(4).text();
                }else{
                    //获得发表时间
                    time1 = tieziItem.select("span").get(2).text();
                    //获得回复时间
                    time2 = tieziItem.select("span").get(3).text();

                }

                //课程url
                Elements cours = tieziItem.select("a");
                Element cour = cours.get(0);
                String tieziUrlStr = cour.attr("href");



                tiezi.setTitle(title);
                tiezi.setStudyNum(studyNum);
                tiezi.setPinglunNum(pinglunNum);
                tiezi.setTime1(time1);
                tiezi.setTime2(time2);
                tiezi.setTieziUrl(tieziUrlStr);

                tiezis.add(tiezi);


            }
            return tiezis;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiezis;
    }

}
