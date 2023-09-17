package com.yaobing.framemvpproject.mylibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.contract.RepoContract
import com.yaobing.framemvpproject.mylibrary.presenter.RepoPresenter
import com.yaobing.module_apt.Presenter
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.Utils.ToastUtils
import com.yaobing.module_middleware.activity.BaseControllerActivity
import java.util.ArrayList
@Router(value = "PagingActivity")
@Presenter(value = [RepoPresenter::class])
class PagingActivity : BaseControllerActivity() ,RepoContract.View{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_paging)
    }

    override fun getAllRepoByNameSuccess(entity: ArrayList<*>?) {
        Log.d("zxcv",entity.toString())
    }

    override fun getAllRepoByNameFailed(errorMsg: String?) {
        ToastUtils.show(this@PagingActivity,errorMsg)
    }
}