package com.qianfeng.courseworkshop.util;


import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianfeng.courseworkshop.MainActivity;
import com.qianfeng.courseworkshop.bean.CourseLogcat;
import com.qianfeng.courseworkshop.bean.MainBeen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * 解析json格式数据的工具类
 * fdb
 * Created by asus on 2016/7/7.
 */

public class JsonMain {

    //解析本地缓存的文件，返回Course数据集合
    public static List<MainBeen> mainBeen(File file) {
        List<MainBeen> mainBeens = new LinkedList<>();

        String jsonStr = readFile(file);
        Log.i("课程", jsonStr);
        Gson gson = new Gson();
        MainBeen status = gson.fromJson("{\"code\":1,\"errorType\":0,\"categoryImg\":[{\"categoryId\":\"231\",\"categoryName\":\"\\u7814\\u53d1\\u00b7\\u7f16\\u7a0b\",\"categoryImg\":\"http:\\/\\/assets.kgc.cn\\/upload\\/image\\/20160612\\/1465700659128482.png\"},{\"categoryId\":\"298\",\"categoryName\":\"\\u8fd0\\u8425\\u00b7\\u63a8\\u5e7f\",\"categoryImg\":\"http:\\/\\/assets.kgc.cn\\/upload\\/image\\/20160612\\/1465700707511121.png\"},{\"categoryId\":\"240\",\"categoryName\":\"\\u89c6\\u89c9\\u00b7\\u521b\\u610f\",\"categoryImg\":\"http:\\/\\/assets.kgc.cn\\/upload\\/image\\/20160612\\/1465700683311545.png\"},{\"categoryId\":\"246\",\"categoryName\":\"\\u7f51\\u7edc\\u00b7\\u5b89\\u5168\",\"categoryImg\":\"http:\\/\\/assets.kgc.cn\\/upload\\/image\\/20160612\\/1465700695213455.png\"},{\"categoryId\":\"243\",\"categoryName\":\"\\u804c\\u573a\\u00b7\\u5fc3\\u7406\",\"categoryImg\":\"\"},{\"categoryId\":\"414\",\"categoryName\":\"\\u4ea7\\u54c1\\u00b7\\u7b56\\u5212\",\"categoryImg\":\"\"},{\"categoryId\":\"420\",\"categoryName\":\"\\u8003\\u7ea7\\u00b7\\u8ba4\\u8bc1\",\"categoryImg\":\"http:\\/\\/assets.kgc.cn\\/upload\\/image\\/20160314\\/1457927781948598.png\"}],\"info\":{\"ad\":[{\"img\":\"http:\\/\\/assets.kgc.cn\\/upload\\/ad\\/20160517\\/1463450580414471.jpg\",\"title\":\"\\u8611\\u83c7\\u8857\\u9879\\u76ee-\\u7535\\u5546\\u7c7bAndroid\\u624b\\u673aApp\\u8bbe\\u8ba1\\uff081\\uff09\",\"course\":{\"id\":\"17801\",\"title\":\"\\u8611\\u83c7\\u8857\\u9879\\u76ee-\\u7535\\u5546\\u7c7bAndroid\\u624b\\u673aApp\\u8bbe\\u8ba1\\uff081\\uff09\",\"abstract\":\"\\u8bbe\\u8ba1\\u6ce8\\u610f\\u4e8b\\u9879\\u53ca\\u5b89\\u5353\\u624b\\u673aApp\\u5e38\\u89c1\\u89c4\\u8303\",\"stuNums\":7277,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.bdqn.cn\\/upload\\/open\\/1445916930456625.jpg\",\"catalogId\":\"241\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},\"clickType\":0},{\"img\":\"http:\\/\\/assets.kgc.cn\\/upload\\/ad\\/20160623\\/1466665711913388.jpg\",\"title\":\"\\u521d\\u8bc6java\",\"course\":{\"id\":\"16028\",\"title\":\"\\u7b2c1\\u8bfe\\uff1a\\u521d\\u8bc6Java\\u8bed\\u8a00\",\"abstract\":\"\\u91d1\\u724c\\u7f8e\\u5973\\u8bb2\\u5e08\\u5e26\\u6765\\u4e0d\\u4e00\\u6837\\u7684Java\\u4f53\\u9a8c\",\"stuNums\":37640,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.bdqn.cn\\/upload\\/open\\/1444474455563536.jpg\",\"catalogId\":\"232\",\"isJob\":0,\"download\":1,\"buyStatus\":{\"status\":0}},\"clickType\":0},{\"img\":\"http:\\/\\/assets.kgc.cn\\/upload\\/ad\\/20160606\\/1465195545571261.jpg\",\"title\":\"\\u5b9a\\u4f4d\",\"course\":{\"id\":\"17802\",\"title\":\"\\u5b9a\\u4f4d\",\"abstract\":\"\\u4f7f\\u7528position\\u5b9e\\u73b0\\u7f51\\u9875\\u5143\\u7d20\\u7684\\u5b9a\\u4f4d\",\"stuNums\":7535,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160401\\/1459502798330229.jpg\",\"catalogId\":\"301\",\"isJob\":0,\"download\":1,\"buyStatus\":{\"status\":0}},\"clickType\":0}],\"my\":[],\"hot\":[{\"id\":\"16028\",\"title\":\"\\u7b2c1\\u8bfe\\uff1a\\u521d\\u8bc6Java\\u8bed\\u8a00\",\"abstract\":\"\\u91d1\\u724c\\u7f8e\\u5973\\u8bb2\\u5e08\\u5e26\\u6765\\u4e0d\\u4e00\\u6837\\u7684Java\\u4f53\\u9a8c\",\"stuNums\":37640,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.bdqn.cn\\/upload\\/open\\/1444474455563536.jpg\",\"catalogId\":\"232\",\"isJob\":0,\"download\":1,\"buyStatus\":{\"status\":0}},{\"id\":\"17322\",\"title\":\"\\u521d\\u8bc6Android Studio\",\"abstract\":\"android\\u5f00\\u53d1\\u542f\\u822a\",\"stuNums\":17695,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160428\\/1461826610237410.jpg\",\"catalogId\":\"234\",\"isJob\":0,\"download\":1,\"buyStatus\":{\"status\":0}},{\"id\":\"15688\",\"title\":\"PHP\\u8bed\\u8a00\\u6982\\u5ff5\\u548c\\u5f00\\u53d1\\u57fa\\u7840\",\"abstract\":\"PHP\\uff0c\\u4ece\\u8fd9\\u91cc\\u5f00\\u59cb~\",\"stuNums\":16055,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160422\\/1461303656203984.jpg\",\"catalogId\":\"286\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"17087\",\"title\":\"\\u6b22\\u8fce\\u6765\\u5230\\u8bfe\\u5de5\\u573a\",\"abstract\":\"\\u8bfe\\u5de5\\u573a\\u4fee\\u70bc\\u6307\\u5357\",\"stuNums\":16008,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.bdqn.cn\\/upload\\/open\\/1437528517152510.jpg\",\"catalogId\":\"382\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}}],\"new\":[{\"id\":\"21271\",\"title\":\"GD\\u5e93-\\u7f29\\u7565\\u56fe\\u6c34\\u5370\",\"abstract\":\"\\u901a\\u8fc7GD\\u5e93\\u5b9e\\u73b0\\u7f29\\u7565\\u56fe\\u548c\\u6c34\\u5370\\u529f\\u80fd~\",\"stuNums\":0,\"beans\":\"25\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"3\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160707\\/1467882085553784.jpg\",\"catalogId\":\"286\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"21270\",\"title\":\"GD\\u5e93-\\u56fe\\u7247\\u9a8c\\u8bc1\\u7801\",\"abstract\":\"\\u901a\\u8fc7GD\\u5e93\\u5b9e\\u73b0\\u9a8c\\u8bc1\\u7801\\u529f\\u80fd\",\"stuNums\":0,\"beans\":\"25\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"3\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160707\\/1467881984846519.jpg\",\"catalogId\":\"286\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"21269\",\"title\":\"\\u9759\\u6001\\u8d44\\u6e90\\u5904\\u7406\",\"abstract\":\"Hold\\u4f4f\\u52a8\\u6001\\u8d44\\u6e90\\u5904\\u7406\",\"stuNums\":2,\"beans\":\"5\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"3\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160707\\/1467881813601158.jpg\",\"catalogId\":\"232\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"21268\",\"title\":\"Mytime-\\u5fae\\u8bc4\\u8bba\",\"abstract\":\"\\u6709\\u56fe\\u6ca1\\u56fe\\uff0c\\u53ea\\u7ed9\\u5dee\\u8bc4\\uff1f\",\"stuNums\":0,\"beans\":\"25\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"3\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160707\\/1467881484247407.jpg\",\"catalogId\":\"286\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}}],\"free\":[{\"id\":\"21264\",\"title\":\"Mytime-\\u9879\\u76ee\\u5206\\u6790\",\"abstract\":\"DIY\\u5c5e\\u4e8e\\u81ea\\u5df1\\u7684\\u65f6\\u5149\\u7f51\\uff01\",\"stuNums\":9,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160707\\/1467879877153362.jpg\",\"catalogId\":\"286\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"21164\",\"title\":\"\\u6e38\\u620f\\u6750\\u8d28\\u8fd9\\u4e48\\u753b\\uff081\\uff09\",\"abstract\":\"\\u4e86\\u89e3\\u6750\\u8d28\\u7684\\u57fa\\u672c\\u753b\\u6cd5\",\"stuNums\":2840,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160526\\/1464255920481986.jpg\",\"catalogId\":\"306\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"21163\",\"title\":\"\\u706b\\u5f71\\u5fcd\\u8005\\u56fe\\u6807\\u8bbe\\u8ba1\",\"abstract\":\"\\u706b\\u5f71\\u5fcd\\u8005\\u56fe\\u6807\\u8bbe\\u8ba1~\",\"stuNums\":3154,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160526\\/1464255452850568.jpg\",\"catalogId\":\"241\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}},{\"id\":\"21157\",\"title\":\"\\u521d\\u8bc6Django\",\"abstract\":\"\\u5b9d\\u5200Django\\uff0c\\u8c01\\u4e0e\\u4e89\\u950b\\uff1f\",\"stuNums\":3030,\"beans\":\"0\",\"unit\":\"K\\u5e01\",\"renewalprice\":\"0\",\"series\":\"0\",\"pic\":\"http:\\/\\/assets.kgc.cn\\/upload\\/open\\/20160524\\/1464067144135846.jpg\",\"catalogId\":\"372\",\"isJob\":0,\"download\":0,\"buyStatus\":{\"status\":0}}]},\"iVersion\":\"3.6\"}", MainBeen.class);

//        MainBeen status = gson.fromJson(jsonStr,MainBeen.class);

//  MainBeen status = gson.fromJson(jsonStr,MainBeen.class);
        System.out.println("status=" + status);
        mainBeens.add(status);

        return mainBeens;
    }
    /**
     * 获得json格式的字符串数据
     * @param file
     * @return
     */
    public static String readFile(File file) {
        String jsonStr = new String();
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[2048];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (fis.read(b) != -1) {
                baos.write(b, 0, b.length);
            }
            baos.close();
            fis.close();
            jsonStr = baos.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


}
