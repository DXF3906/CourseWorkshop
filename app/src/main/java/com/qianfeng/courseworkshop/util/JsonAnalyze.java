package com.qianfeng.courseworkshop.util;


import android.util.Log;

import com.qianfeng.courseworkshop.bean.CourseLogcat;

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

public class JsonAnalyze {
    //解析本地缓存的文件，返回Course数据集合
    public static List<CourseLogcat> analysCoursesLogcat(File file) {
        List<CourseLogcat> courseLogcatDatas = new LinkedList<>();
        String jsonStr = readFile(file);
        Log.i("课程", jsonStr);
        try {
            // 封装成JSONObject对象
            JSONObject obj = new JSONObject(jsonStr);

            // 解析
            JSONArray array = obj.getJSONArray("sortdata");

            for (int index = 0; index < array.length(); index++) {
                JSONObject objTmp = array.getJSONObject(index);

                String name = objTmp.getString("name");
                String total = objTmp.getString("total");
                String catalogId = objTmp.getString("catalogId");

                courseLogcatDatas.add(new CourseLogcat(catalogId, "", 0, "", total, name));

            }
            return courseLogcatDatas;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courseLogcatDatas;
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
            byte[] b = new byte[1024];
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
