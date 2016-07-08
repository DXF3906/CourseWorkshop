package com.qianfeng.courseworkshop.inner;

import android.graphics.Bitmap;

/**
 * 接口回调
 * Created by asus on 2016/7/7.
 */

public interface GetFileBitmapCallBack {
    //回调数据为通过异步任务下载的文件名
    void getBitmap(Bitmap bitmap);
}
