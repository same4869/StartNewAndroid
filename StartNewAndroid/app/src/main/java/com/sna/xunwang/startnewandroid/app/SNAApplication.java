package com.sna.xunwang.startnewandroid.app;

import android.app.Application;

import com.sna.xunwang.startnewandroid.config.Constants;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by xunwang on 16/11/9.
 */

public class SNAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBomb();
    }

    private void initBomb() {
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(Constants.BOMB_APP_ID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }
}
