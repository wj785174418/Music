package com.example.wswj.music.Model;


/**
 * Created by Administrator on 2017/3/23.
 */

public class RollPicture {

    //专辑ID
    private String albumID;
    //图片url
    private String picUrl;

    public RollPicture(String albumID, String picUrl) {
        this.albumID = albumID;
        this.picUrl = picUrl;
    }

    public String getAlbumID() {
        return albumID;
    }

    public String getPicUrl() {
        return picUrl;
    }

    @Override
    public String toString() {
        return String.format("RollPic:{albumID:%s,picUrl:%s}",this.getAlbumID(), this.getPicUrl());
    }
}
