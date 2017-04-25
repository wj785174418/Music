package com.example.wswj.music.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/28.
 */

public class SongMenu implements Serializable{

    //轮播图传入歌单
    public static final int TYPE_ROLL_BANNER = 1;
    //推荐歌单传入
    public static final int TYPE_RECOMMEND = 2;
    //热门歌单传入
    public static final int TYPE_HOT = 3;
    //分类传入
    public static final int TYPE_CATEGORY = 4;


    //歌单ID
    private String songMenuID;
    //标题
    private String title;
    //描述
    private String desc;
    //图片Url
    private String picUrl;
    //听过的人数
    private String listenNum;
    //歌单类型
    private int type;

    public SongMenu(String songMenuID, String title, String desc, String picUrl, String listenNum) {
        setSongMenuID(songMenuID);
        setTitle(title);
        setDesc(desc);
        setPicUrl(picUrl);
        setListenNum(listenNum);
    }

    public String getDesc() {
        return desc;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getSongMenuID() {
        return songMenuID;
    }

    public String getTitle() {
        return title;
    }

    public String getListenNum() {
        return listenNum;
    }

    public int getType() {
        return type;
    }

    public void setSongMenuID(String songMenuID) {
        this.songMenuID = songMenuID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setListenNum(String listenNum) {
        if (listenNum != null) {
            int num = Integer.parseInt(listenNum) / 10000;
            if (num > 0) {
                this.listenNum = String.format("%s万", num);
            } else {
                this.listenNum = listenNum;
            }
        }
    }

    public void setType(int type) {
        this.type = type;
    }
}
