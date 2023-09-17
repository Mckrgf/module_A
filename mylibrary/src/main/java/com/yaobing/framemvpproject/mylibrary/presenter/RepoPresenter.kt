package com.yaobing.framemvpproject.mylibrary.presenter

import android.util.Log
import com.yaobing.framemvpproject.mylibrary.contract.RepoContract
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.network.APIService
import com.yaobing.module_middleware.network.Api
import com.yaobing.module_middleware.network.RxSchedulers
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * @author : yaobing
 * @date   : 2020/10/30 15:50
 * @desc   :
 */
class RepoPresenter : RepoContract.Presenter() {
    var aa: ArrayList<RepoData> = ArrayList<RepoData>()
    override fun getAllRepoByName(name: String?) {
        val aaa = Api.getInstance().retrofit.create(APIService::class.java)
        val flowable = aaa.listReposRx(name).compose(RxSchedulers.io_main())


        mCompositeSubscription.add(flowable.onErrorReturn {
            Log.d("zxcvb0", Thread.currentThread().name)
            return@onErrorReturn aa
        }.subscribe {
            Log.d("zxcvb1", Thread.currentThread().name)

            if (it.size == 0) {
                view.getAllRepoByNameFailed("失败")
            } else {
                view.getAllRepoByNameSuccess(it)
            }
        })
    }
    override fun getGithubReposByPages(name: String?,page_data:HashMap<String,Int>) {
        val aaa = Api.getInstance().retrofit.create(APIService::class.java)
        val flowable = aaa.listReposRx(name).compose(RxSchedulers.io_main())
        var repoData = mutableListOf<RepoData>()

        mCompositeSubscription.add(flowable.onErrorReturn {
            Log.d("zxcvb0", Thread.currentThread().name)
            return@onErrorReturn aa
        }.subscribe {
            Log.d("zxcvb1", Thread.currentThread().name)

            if (it.size == 0) {
                view.getAllRepoByNameFailed("失败")
            } else {
                view.getAllRepoByNameSuccess(it)
                repoData = it
            }

        })
    }


    fun getGithubReposByPage(name: String?,page_data:HashMap<String,Int>) : ArrayList<RepoData>{
        val aaa = Api.getInstance().retrofit.create(APIService::class.java)
        val flowable = aaa.listReposRx(name).compose(RxSchedulers.io_main())
        val repoData1 = arrayListOf<RepoData>()

        mCompositeSubscription.add(flowable.onErrorReturn {
            Log.d("zxcvb0", Thread.currentThread().name)
            return@onErrorReturn aa
        }.subscribe {
            Log.d("zxcvb1", Thread.currentThread().name)

            if (it.size == 0) {
                view.getAllRepoByNameFailed("失败")
            } else {
                view.getAllRepoByNameSuccess(it)
                repoData1.addAll(it)
            }

        })
        return repoData1
    }

}