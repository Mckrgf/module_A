package com.yaobing.framemvpproject.mylibrary.network

import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.data.RepoResponse
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GithubRepoService {
    //分页显示repo,rxjava(自己库的数据)
    @GET("users/{user}/repos")
    suspend fun listRx(@Path("user") user: String?, @QueryMap(encoded = true) page: HashMap<String, Int>): Flowable<ArrayList<RepoData>>

    //分页显示repo,rxjava（所有android库的数据）
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepos(@Query("page") page: Int, @Query("per_page") perPage: Int): RepoResponse

    //分页显示repo,rxjava（所有android库的数据）
    @GET("search/repositories?sort=stars&q=Android")
    fun searchReposa(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<RepoResponse>

    companion object {
        private const val baseUrl = "https://api.github.com/"

        fun create(): GithubRepoService {
            return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                ).build().create(GithubRepoService::class.java)
        }
    }
}
