package com.example.wswj.music.Util;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    /**
    * 获取全局的Context
    * */
    public static Context getContext() {
        return context;
    }
}
