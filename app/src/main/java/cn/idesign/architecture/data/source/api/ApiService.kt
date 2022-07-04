package cn.chinaunicom.drone.data.source.api

import cn.idesign.architecture.data.dto.CommonResponse
import cn.idesign.architecture.data.dto.PageData
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): CommonResponse<PageData<List<Article>>>


    @GET("banner/json")
    suspend fun getHomeBanner(): CommonResponse<List<Banner>>
}