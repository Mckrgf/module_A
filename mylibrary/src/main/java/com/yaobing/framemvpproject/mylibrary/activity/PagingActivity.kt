package com.yaobing.framemvpproject.mylibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.module_apt.Presenter
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.Utils.ToastUtils
import com.yaobing.module_middleware.activity.BaseControllerActivity
import java.util.ArrayList
@Router(value = "PagingActivity")
class PagingActivity : BaseControllerActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_paging)
    }

}