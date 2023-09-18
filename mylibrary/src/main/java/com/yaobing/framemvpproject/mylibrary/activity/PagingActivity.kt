package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.util.Log
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.network.APIService
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.Utils.ToastUtils
import com.yaobing.module_middleware.activity.BaseControllerActivity
import com.yaobing.module_middleware.network.Api
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Router(value = "PagingActivity")
class PagingActivity : BaseControllerActivity() {
    private val aa: Flowable<ArrayList<RepoData>> by lazy {
        val name = "MCKRGF"
        val queryMap = HashMap<String, Int>()
        queryMap["page"] = 1
        queryMap["per_page"] = 5
        Api.getInstance().retrofit.create(APIService::class.java).listRx(name, queryMap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCompositeSubscription.add(aa.onErrorReturn {
            Log.d("zxcvb0", Thread.currentThread().name)
            arrayListOf<RepoData>()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Log.d("zxcvb1", Thread.currentThread().name)

            Log.d("zxcv", it.toString())
        })
    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_paging)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}