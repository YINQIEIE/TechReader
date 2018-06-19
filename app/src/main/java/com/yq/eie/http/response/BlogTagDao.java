package com.yq.eie.http.response;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class BlogTagDao {

    @Insert
    public abstract void insertBlog(BlogTag... blogTag);

    @Query("select * from blog_tag")
    public abstract List<BlogTag> findAll();
}
