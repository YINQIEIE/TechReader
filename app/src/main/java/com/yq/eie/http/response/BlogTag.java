package com.yq.eie.http.response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "blog_tag")
public class BlogTag implements Serializable {

    private static final long serialVersionUID = 3447392702792292134L;

    @PrimaryKey
    private int id;
    private String tagName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
