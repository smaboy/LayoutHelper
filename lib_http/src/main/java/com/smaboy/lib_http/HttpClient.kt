package com.smaboy.lib_http

import com.smaboy.lib_http.constant.APIConstant
import com.smaboy.lib_http.gson.CustomGson
import com.smaboy.lib_http.interceptor.BaseUrlInterceptor
import com.smaboy.lib_http.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 类名: HttpClient

 * 类作用描述: http客户端获取类

 * 作者: liyongliang3

 * 创建时间: 2021/1/21 11:08 AM
 *
 */
class HttpClient private constructor(){

    var  client : Retrofit

    companion object {
        val instance: HttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpClient() }
    }

    init {
        println("Retrofit 开始初始化了")
//            Log.d(HttpClient::javaClass.name, "Retrofit 开始初始化了")

        client =  Retrofit.Builder().apply {
            //设置服务器(这里采用玩安卓的开发api)
            baseUrl(APIConstant.BaseUrlType.DEF_BASE_URL.url())
            //设置http客户端
            client(okHttpClient)
            //添加转化工厂
            addConverterFactory(GsonConverterFactory.create(CustomGson.build()))
            //添加转化适配器
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())

        }.build()
    }


    /**
     * OKHttp客户端实例
     */
    private val okHttpClient: OkHttpClient
        get() {
            println("OkHttpClient 开始初始化了")
//            Log.d(HttpClient::javaClass.name, "OkHttpClient 开始初始化了")

            return OkHttpClient.Builder().apply {
                //添加拦截器
                addInterceptor(BaseUrlInterceptor())
                addInterceptor(LoggingInterceptor())
                //设置回调时间和连接时间
                callTimeout(300, TimeUnit.SECONDS)
                connectTimeout(300, TimeUnit.SECONDS)
                //设置cookie
//                cookieJar()

            }.build()
        }

}