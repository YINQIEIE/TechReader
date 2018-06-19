package com.yq.eie.entity;

/**
 * Created by yinqi on 2017/9/14.
 */

public class Folder {

    private String name;//文件夹名字
    private String path;//文件夹路径
    private int count;//文件夹中歌曲名字

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", count=" + count +
                '}';
    }
}
