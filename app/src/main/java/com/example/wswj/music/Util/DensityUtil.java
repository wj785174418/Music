package com.example.wswj.music.Util;

import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2017/3/24.
 */

public class DensityUtil {

    //
    private static final DisplayMetrics displayMetrics = MyApplication.getContext().getResources().getDisplayMetrics();

    //设备宽度

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dipTopx(float dpValue) {
        return (int) (dpValue * displayMetrics.density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxTodip(float pxValue) {
        return (int) (pxValue / displayMetrics.density + 0.5f);
    }

    /**
     * 获取屏幕宽度dp
     */
    public static int getScreenWidthDp() {
        return pxTodip(displayMetrics.widthPixels);
    }

    /**
     * 获取屏幕高度dp
     */
    public static int getScreenHeightDp() {
        return pxTodip(displayMetrics.heightPixels);
    }

    /**
     * 获取屏幕宽度px
     */
    public static int getScreenWidthPx() {
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度px
     */
    public static int getScreenHeightPx() {
        return displayMetrics.heightPixels;
    }

    public static int getStatusBarHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
