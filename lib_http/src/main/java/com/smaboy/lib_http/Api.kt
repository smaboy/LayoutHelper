package com.smaboy.lib_http

import com.smaboy.lib_http.entity.responses.HomeArticleListResp
import io.reactivex.rxjava3.core.Observable
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

/**
 * 类名: Apt

 * 类作用描述: api

 * 作者: liyongliang3

 * 创建时间: 2021/1/21 1:42 PM
 *
 */
object Api {

    /**
     * 服务器地址
     */
    const val BASE_URL = "https://www.wanandroid.com"

    /**
     * 首页文章列表
     */
    const val HOME_ARTICLE_LIST = "/article/list/{page}/json"


}

interface HomeService{


    @GET(Api.HOME_ARTICLE_LIST)
    fun getHomeArticleList(@Path("page") page: Int) : Observable<HomeArticleListResp>


}