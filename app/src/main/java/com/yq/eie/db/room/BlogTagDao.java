package com.yq.eie.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class BlogTagDao {

    @Insert
    public abstract void insertTag(BlogTagEntity blogTag);

    @Query("select * from blog_Tag")
    public abstract List<BlogTagEntity> findAll();
}
