package com.smaboy.lib_http.interceptor

import com.smaboy.lib_http.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 类名: LoggingInterceptor

 * 类作用描述: 日志拦截器

 * 作者: liyongliang3

 * 创建时间: 2021/1/21 2:40 PM
 *
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //系统当前时间
        val n1 = System.nanoTime()
        //chain包含request和responses
        val request = chain.request()
        //打印请求时间和url
        LogUtil.d(this, String.format("发送请求: [%s] %n请求头: %n%s", request.url(), request.headers()))

        val response = chain.proceed(request)
        //收到响应的时间
        val n2 = System.nanoTime()
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        LogUtil.d(
            this, String.format(
                "接收响应: [%s] %n响应时长：%.1fms %n响应头：%n%s",
                response.request().url(),
                (n2 - n1) / 1e6,
                response.headers()
            )
        )
        LogUtil.d(this, "响应体内容如下:")
        LogUtil.json(this, response.peekBody((1024 * 1024).toLong()).string())


        return response
    }
}