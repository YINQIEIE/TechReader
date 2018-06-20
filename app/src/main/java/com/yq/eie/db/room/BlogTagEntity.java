package com.yq.eie.db.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "blog_tag")
public class BlogTagEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tagName;

    public BlogTagEntity() {
    }

    public BlogTagEntity(String tagName) {
        this.tagName = tagName;
    }

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
