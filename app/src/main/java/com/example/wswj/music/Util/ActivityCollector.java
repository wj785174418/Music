package com.example.wswj.music.Util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    //加入新创建的activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移除已销毁的activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁所有activity,关闭应用程序
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}