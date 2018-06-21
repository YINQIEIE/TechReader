package com.yq.eie.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface BlogTagDao {

    @Insert
    void insertTag(BlogTagEntity blogTag);

    @Query("SELECT * FROM blog_Tag ORDER BY tagName ASC")
    List<BlogTagEntity> findAll();

    @Query("SELECT * FROM blog_tag WHERE tagName LIKE :name")
    Maybe<List<BlogTagEntity>> findTagsByName(String name);
}
