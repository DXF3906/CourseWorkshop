package com.qianfeng.courseworkshop.asynctask;

import android.os.AsyncTask;

import com.qianfeng.courseworkshop.util.HttpUtils;

/**
 * 自定义异步任务类，用来开启网络下载任务
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CommonAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        //将资源缓存到本地，返回本地的文件名
        return HttpUtils.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
