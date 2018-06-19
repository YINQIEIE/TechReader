package com.yq.eie.entity;

import java.io.Serializable;

/**
 * Created by yinqi on 2017/9/14.
 */

public class DetaisInfo implements Serializable {

    private int type;// 1.歌手 2.专辑 3.文件夹
    private String title;//页面标题
    private String param; //对应 ID 或路径

    public DetaisInfo(int type, String title, String param) {
        this.type = type;
        this.title = title;
        this.param = param;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getParam() {
        return param;
    }
}
