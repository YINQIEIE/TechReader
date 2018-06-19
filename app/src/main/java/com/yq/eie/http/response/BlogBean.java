package com.yq.eie.http.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "blog", indices = {@Index(value = {"_id"}, unique = true)})
public class BlogBean extends GankBean.ResultBean {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tag;//标签
    private Date collect_time;//时间

    public BlogBean() {
    }

    public BlogBean(GankBean.ResultBean bean) {
        this._id = bean._id;
        this.name = bean.name;
        this.createdAt = bean.createdAt;
        this.desc = bean.desc;
        this.publishedAt = bean.publishedAt;
        this.type = bean.type;
        this.url = bean.url;
        this.used = bean.used;
        this.who = bean.who;
        this.images = bean.images;
        this.imageUrl = bean.imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Date collect_time) {
        this.collect_time = collect_time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultBean{");
        sb.append("id=").append(id).append('\'');
        sb.append(",name='").append(name).append('\'');
        sb.append(", _id='").append(_id).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", publishedAt='").append(publishedAt).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", used=").append(used);
        sb.append(", who='").append(who).append('\'');
        sb.append(",tag='").append(tag).append('\'');
        sb.append(",collect_time='").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(collect_time)).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
