package com.example.wswj.music.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/28.
 */

public class SongMenu implements Serializable{
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

    public SongMenu(String songMenuID, String title, String desc, String picUrl, String listenNum) {
        this.songMenuID = songMenuID;
        this.title = title;
        this.desc = desc;
        this.picUrl = picUrl;

        if (listenNum != null) {
            int num = Integer.parseInt(listenNum) / 10000;
            if (num > 0) {
                this.listenNum = String.format("%s万", num);
            } else {
                this.listenNum = listenNum;
            }
        }
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
}
