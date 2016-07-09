package com.qianfeng.courseworkshop.asynctask;

import android.os.AsyncTask;

import com.qianfeng.courseworkshop.inner.GetFileNameCallBack;
import com.qianfeng.courseworkshop.util.HttpUtils;

/**
 * 自定义异步任务类，用来开启网络下载任务
 * fdb
 * Created by asus on 2016/7/6.
 */

public class CommonAsyncTask extends AsyncTask<String, Void, String> {
    private String fileName;
    private GetFileNameCallBack callBack;

    public CommonAsyncTask(GetFileNameCallBack callBack, String fileName) {
        super();
        this.callBack = callBack;
        this.fileName = fileName;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        //将资源缓存到本地，返回本地的文件名
        return HttpUtils.getData(params[0], fileName);
    }

    @Override
    protected void onPostExecute(String result) {
    //调用接口回掉，将缓存的文件名返回
        if (result != null) {
            callBack.getFileName(result);
        }
    }
}
