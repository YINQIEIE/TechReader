package com.yq.eie.entity;

/**
 * Created by yinqi on 2017/9/12.
 */

public class LocalInfo {

    private String title;//标题
    private int count;//数量
    private int iconId;//图标

    public LocalInfo(String title, int count, int iconId) {
        this.title = title;
        this.count = count;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public int getIconId() {
        return iconId;
    }

    public void setCount(int count) {
        this.count = count;
    }

}