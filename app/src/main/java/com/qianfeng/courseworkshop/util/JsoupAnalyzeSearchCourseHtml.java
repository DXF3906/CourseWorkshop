package com.qianfeng.courseworkshop.util;


import com.qianfeng.courseworkshop.bean.SearchCourse;
import com.qianfeng.courseworkshop.bean.Tiku;

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

public class JsoupAnalyzeSearchCourseHtml {
    //解析本地缓存的文件，返回Course数据集合
    public static List<SearchCourse> analysSearchCourses(File file) {
        List<SearchCourse> tikuDatas = new LinkedList<>();
        try {
            //1.获取文档对象
            Document doc = Jsoup.parse(file, "utf-8");

            //2. 获取class="sListBtm fix"的所有元素
            Elements tikuList = doc.getElementsByClass("search-course-r");
            //3.获取每个tiku实例的每个属性，并添加到集合tikuLists中
            for (Element tikuItem : tikuList) {
                //课程实例
                SearchCourse searchCourse = new SearchCourse();



                //题目
                String title=tikuItem.select("h2").get(0).text();

                //描述
                String description=tikuItem.select("p").get(0).text();

                //标志1
                String tag1="IT";
                if (tikuItem.select("a").size()>1) {
                    tag1 = tikuItem.select("a").get(1).text();
                }
                //标志2
                String tag2="IT";
                if (tikuItem.select("a").size()>2) {
                    tag2 = tikuItem.select("a").get(2).text();
                }
                //标志3
                String tag3="IT";
                if (tikuItem.select("a").size()>3) {
                     tag3 = tikuItem.select("a").get(3).text();
                }

                //课程Url
                Elements cours = tikuItem.select("a");
                Element cour = cours.get(0);
                String courUrlStr = cour.attr("href");

                searchCourse.setTitle(title);
                searchCourse.setDescription(description);
                searchCourse.setTag1(tag1);
                searchCourse.setTag2(tag2);
                searchCourse.setTag3(tag3);
                searchCourse.setCourseUrl(courUrlStr);

                tikuDatas.add(searchCourse);


            }
            return tikuDatas;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return tikuDatas;
    }

}
