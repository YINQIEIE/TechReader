package com.yq.eie.entity;

/**
 * Created by yinqi on 2017/9/13.
 */

public class Album extends SortBase {

    private int id;// row id
    private String name;//专辑名称
    private String artist;//专辑歌手
    private String album_art;//
    private int count;//歌曲数量

    public Album() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album_art='" + album_art + '\'' +
                ", count=" + count +
                '}';
    }
}
