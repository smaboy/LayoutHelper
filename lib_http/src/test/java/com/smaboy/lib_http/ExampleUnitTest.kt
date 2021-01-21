package com.smaboy.lib_http

import com.smaboy.lib_http.entity.responses.HomeArticleListResp
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun testHttpClient() {
        val homeService = HttpClient.instance.client.create(HomeService::class.java)


        val homeArticleList = homeService.getHomeArticleList(0)

        homeArticleList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<HomeArticleListResp>{
                override fun onSubscribe(d: Disposable?) {
                    println("onSubscribe")

                }

                override fun onNext(t: HomeArticleListResp?) {
                    println("onNext")
                    println(t?.toString())
                }

                override fun onError(e: Throwable?) {
                    println("onError")
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

    }
}