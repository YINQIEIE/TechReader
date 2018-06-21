package com.yq.eie.db.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "blog_tag", indices = {@Index(value = "tagName", unique = true)})
public class BlogTagEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String tagName;

    public BlogTagEntity() {
    }

    @Ignore
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BlogTagEntity{");
        sb.append("id=").append(id);
        sb.append(", tagName='").append(tagName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
