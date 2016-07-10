package com.qianfeng.courseworkshop.util;


import com.qianfeng.courseworkshop.bean.SearchCourse;

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

public class JsoupAnalyzeSearchCourseHtmlImgUrl {
    //解析本地缓存的文件，返回Course数据集合
    public static List<SearchCourse> analysSearchCourses(File file) {
        List<SearchCourse> tikuDatas = new LinkedList<>();
        try {
            //1.获取文档对象
            Document doc = Jsoup.parse(file, "utf-8");

            //2. 获取class="sListBtm fix"的所有元素
            Elements tikuList = doc.getElementsByClass("left");
            //3.获取每个tiku实例的每个属性，并添加到集合tikuLists中
            for (Element tikuItem : tikuList) {
                //课程实例
                SearchCourse searchCourse = new SearchCourse();

                //图片网址
                Elements tks = tikuItem.select("img");
                String imgUrl="";
                if (tks.size()<=0) {
                    continue;
                }
                    Element tk = tks.get(0);
                    imgUrl = tk.attr("src");

                if (imgUrl.length()<=0) {
                    imgUrl = "http://assets.kgc.cn/upload/open/20160613/1465810210254595.jpg";
                }




                searchCourse.setImgUrl(imgUrl);


                tikuDatas.add(searchCourse);


            }
            return tikuDatas;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return tikuDatas;
    }

}
