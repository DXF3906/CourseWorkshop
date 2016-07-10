package com.qianfeng.courseworkshop.util;


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

public class JsoupAnalyzeTikuHtml {
    //解析本地缓存的文件，返回Course数据集合
    public static List<Tiku> analysTikus(File file) {
        List<Tiku> tikuDatas = new LinkedList<>();
        try {
            //1.获取文档对象
            Document doc = Jsoup.parse(file, "utf-8");

            //2. 获取class="sInfo"的所有元素
            Elements tikuList = doc.getElementsByClass("sInfo");
            //3.获取每个tiku实例的每个属性，并添加到集合tikuLists中
            for (Element tikuItem : tikuList) {
                //课程实例
                Tiku tiku = new Tiku();

                //试题类型
                String chooseType = tikuItem.select("span").get(0).text();

                //题目描述
                String description=tikuItem.select("a").get(0).text();

                //试题网址
                Elements tks = tikuItem.select("a");
                Element tk = tks.get(0);
                String tikuUrl = tk.attr("href");


                tiku.setChooseType(chooseType);
                tiku.setDescription(description);
                tiku.setTikuUrl(tikuUrl);


                tikuDatas.add(tiku);


            }
            return tikuDatas;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return tikuDatas;
    }

}
