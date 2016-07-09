package com.qianfeng.courseworkshop.util;


import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络资源访问工具类
 * fdb
 * Created by asus on 2016/7/6.
 */

public class MyHttpUtils {
    //返回缓存的文件名
    public static String getData(String urlStr,String fileName){
        //如果传过来的有文件名，说明是目录文件（json格式的数据），否则，是html数据重新给定文件名
        if (fileName.length()<=0){
            fileName=urlStr.substring(urlStr.lastIndexOf("/"));
            Log.i("tag","根据url修改的文件名"+fileName);
        }else{
            Log.i("tag","传过来的文件名"+fileName);

        }
        File file=new File(Environment.getExternalStorageDirectory(),fileName+".html");
        FileOutputStream os=null;
        InputStream is=null;
        HttpURLConnection coon=null;
        try {
            os=new FileOutputStream(file);
            //1.声明网络访问的路径，url 网络资源
            URL url=new URL(urlStr);

            //2.通过路径得到一个连接 http的连接
            coon= (HttpURLConnection) url.openConnection();
            coon.setConnectTimeout(6* 1000);

            // 3.判断服务器给我们返回的状态信息
            int code=coon.getResponseCode();
            if (code==HttpURLConnection.HTTP_OK){
                // 4.利用链接成功的 conn 得到输入流
                is=coon.getInputStream();

                //缓存到本地SD卡上
                byte [] bytes=new byte[1024];
                int len=-1;
                while ((len=is.read(bytes))!=-1){
                    os.write(bytes,0,len);
                   // os.flush();
                }
            }
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
