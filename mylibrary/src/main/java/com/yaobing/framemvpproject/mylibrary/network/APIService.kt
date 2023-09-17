package com.yaobing.framemvpproject.mylibrary.network

import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.module_apt.ApiFactory
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author : yaobing
 * @date   : 2020/10/30 15:43
 * @desc   :
 */
@ApiFactory(name = "APIServiceHttpClient")
interface APIService {
    //该用户的所有repo,rxjava
    @GET("users/{user}/repos")
    fun listReposRx(@Path("user") user: String?): Flowable<ArrayList<RepoData>>

    //分页显示repo,rxjava
    @GET("users/{user}/repos")
    fun listRx(@Path("user") user: String?, @QueryMap(encoded = true) page: HashMap<String,Int>): Flowable<ArrayList<RepoData>>
}