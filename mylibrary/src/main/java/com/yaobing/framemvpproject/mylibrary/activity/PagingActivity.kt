package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.RepoAdapter
import com.yaobing.framemvpproject.mylibrary.viewModel.MainViewModel
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.activity.BaseControllerActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


//
/**
 * 结合viewModel+liveData+paging+协程+retrofit，具体结构如下
 *
 * 1.定义接口service用来保存retrofit请求方法
 * 本来是用call/single/observable等作为返回参数，在用suspend关键字的前提下，用返回数据的类型即可
 * 再定义一个create方法用来实例化该retrofit的service
 * 2.定义一个Repository单例（通过object关键字），定义一个fun，传入Pager的构造函数（config+pagingResourceFactory），同时返回flow对象
 * 3。2缺一个pagingSource，做一个。
 * 4.
 */

@Router(value = "PagingActivity")
class PagingActivity : BaseControllerActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var repoAdapter = RepoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rv = findViewById<RecyclerView>(R.id.rv)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = repoAdapter
        lifecycleScope.launch {
            viewModel.getGithubRepoData().collect {
                repoAdapter.submitData(it)
            }
        }
        repoAdapter.addLoadStateListener {
            Log.d("zxcv","监听了it的状态" + it.append)
            when (it.append) {
                is LoadState.Loading ->{
                    progressBar.visibility = View.VISIBLE
                }
                is LoadState.NotLoading ->{
                    progressBar.visibility = View.GONE
                }
                is LoadState.Error ->{
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_paging)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}