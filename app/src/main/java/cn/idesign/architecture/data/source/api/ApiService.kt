package cn.chinaunicom.drone.data.source.api

import cn.idesign.architecture.data.dto.CommonResponse
import cn.idesign.architecture.data.dto.PageData
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): CommonResponse<PageData<List<Article>>>

    @GET("wenda/list/{page}/json")
    suspend fun getWenDa(@Path("page") page: Int): CommonResponse<PageData<List<Article>>>

    @GET("project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): CommonResponse<PageData<List<Article>>>

    @GET("banner/json")
    suspend fun getHomeBanner(): CommonResponse<List<Banner>>
}