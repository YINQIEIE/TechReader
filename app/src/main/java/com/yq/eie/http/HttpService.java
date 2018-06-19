package com.yq.eie.http;

import com.yq.eie.entity.BookInfo;
import com.yq.eie.http.response.BannerBean;
import com.yq.eie.http.response.GankBean;
import com.yq.eie.http.response.MovieBean;
import com.yq.eie.http.response.MovieDetailBean;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yinqi on 2017/11/2.
 */

public interface HttpService {

    //    @Headers("User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64)")
    @GET("ting?from=android&version=2.4.0&method=baidu.ting.plaza.getFocusPic&format=json&limit=111")
//    @GET("ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Call<BannerBean> getBannerUrls();

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @Headers("User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64)")
    @GET("day/{year}/{month}/{day}")
    Call<GankBean<Map<String, List<GankBean.ResultBean>>>> getGankIoDay(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{per_page}/{page}")
    Call<GankBean<List<GankBean.ResultBean>>> getGankInfo(@Path("type") String type, @Path("page") int page, @Path("per_page") int per_page);

    @GET("v2/movie/in_theaters")
    Call<MovieBean> getHotMovies();

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Call<MovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Call<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */

    @GET("v2/book/search")
    Call<BookInfo> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("v2/book/{id}")
    Call getBookDetail(@Path("id") String id);

}
