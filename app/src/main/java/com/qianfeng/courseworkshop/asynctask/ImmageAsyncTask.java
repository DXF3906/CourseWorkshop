package com.qianfeng.courseworkshop.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 自定义异步任务类，用来开启下载网络的图片
 * fdb
 * Created by asus on 2016/7/6.
 */

public class ImmageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private Context context;

    public ImmageAsyncTask(Context context) {
        super();
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Bitmap doInBackground(String... params) {


        // 核心逻辑（本地存储）
        File picDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//
        // 保证图片名称的唯一性
        File nowPic = new File(picDir, Base64.encodeToString(
                params[0].getBytes(), Base64.CRLF));
        byte[] data = null;
        Bitmap bp = null;
        if (nowPic.exists()) {
            // 存在,从本地获取
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(nowPic);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] b = new byte[1024 * 4];
                int len = 0;

                while ((len = fis.read(b)) != -1) {
                    baos.write(b, 0, b.length);

                }
                data=baos.toByteArray();
                bp= BitmapFactory.decodeByteArray(data, 0, data.length);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            // 保存到本地
            FileOutputStream fos = null;
            try {
                bp = Picasso.with(context).load(params[0]).get();
                fos = new FileOutputStream(nowPic);
                bp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return bp;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

    }
}
