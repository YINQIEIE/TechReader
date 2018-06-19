package com.yq.eie.http.response;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class BlogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertSingleBlog(BlogBean bean);

    /**
     * 编译报错；
     * Methods annotated with @Insert can return either void, long, Long, long[], Long[] or List<Long>
     * 原因不详
     *
     * @param
     * @return 行 id
     * <p>
     * Flowable<Long> insertBlog(BlogBean blog);
     */

    @Query("SELECT * FROM blog")
    public abstract Flowable<List<BlogBean>> findAll();

    @Query("SELECT * FROM blog")
    public abstract List<BlogBean> getAllBlogs();

    /**
     * Single 在数据库中有用户时触发 onSuccess，没有触发 onError
     *
     * @param id 文章 id
     * @return 已收藏博客信息
     */
    @Query("SELECT * FROM blog WHERE _id = :id")
    public abstract Single<BlogBean> queryBlogById(String id);

    @Query("delete FROM blog WHERE _id = :id")
    public abstract int deleteBlogById(String id);

}
