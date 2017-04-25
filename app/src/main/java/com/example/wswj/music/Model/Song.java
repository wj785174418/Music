package com.example.wswj.music.Model;

/**
 * Created by Administrator on 2017/4/24.
 */

public class Song {
    //歌曲名
    String title;
    //歌曲ID
    String songID;
    //作者
    String author;
    //专辑ID
    String albumID;
    //专辑名
    String albumTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
}
