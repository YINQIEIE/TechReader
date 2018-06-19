package com.yq.eie.entity;

/**
 * Created by yinqi on 2017/9/11.
 */

public class Song extends SortBase {

    private int songId;//歌曲id
    private String songName;//歌曲名称
    private int artistId;//歌手 ID
    private String artist;//歌手名称
    private int duration;//时长 ms
    private int albumId;//专辑 ID
    private String album;//专辑名称
    private int size;//大小 byte
    private String path;//路径

    public Song() {
    }

    public int getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbum() {
        return album;
    }

    public int getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", artistId=" + artistId +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                ", albumId=" + albumId +
                ", album='" + album + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", firstChar=" + firstChar +
                '}';
    }
}
