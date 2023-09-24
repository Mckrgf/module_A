package com.yaobing.framemvpproject.mylibrary.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.RepoAdapter
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityPagingBinding
import com.yaobing.framemvpproject.mylibrary.network.APIService
import com.yaobing.framemvpproject.mylibrary.viewModel.MainViewModel
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.Utils.ToastUtils
import com.yaobing.module_middleware.activity.BaseControllerActivity
import com.yaobing.module_middleware.network.Api
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Router(value = "PagingActivity")
class PagingActivity : BaseControllerActivity() {
    private val aa: Flowable<ArrayList<RepoData>> by lazy {
        val name = "MCKRGF"
        val queryMap = HashMap<String, Int>()
        queryMap["page"] = 1
        queryMap["per_page"] = 5
        Api.getInstance().retrofit.create(APIService::class.java).listRx(name, queryMap)
    }
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var repoAdapter = RepoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mCompositeSubscription.add(aa.onErrorReturn {
//            Log.d("zxcvb0", Thread.currentThread().name)
//            arrayListOf<RepoData>()
//        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
//            Log.d("zxcvb1", Thread.currentThread().name)
//
//            Log.d("zxcv", it.toString())
//        })
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
//            when (it.refresh) {
//                is LoadState.NotLoading -> {
//                    progressBar.visibility = View.INVISIBLE
//                    rv.visibility = View.VISIBLE
//                }
//                is LoadState.Loading -> {
//                    progressBar.visibility = View.VISIBLE
//                    rv.visibility = View.INVISIBLE
//                }
//                is LoadState.Error -> {
//                    val state = it.refresh as LoadState.Error
//                    progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
//        repoAdapter.addLoadStateListener {
//            when(it.refresh) {
//                is LoadState.NotLoading -> {
//                    progressBar.visibility = View.INVISIBLE
//                }
//                is LoadState.Loading -> {
//                    progressBar.visibility = View.VISIBLE
//                }
//                is LoadState.Error -> {
//                    progressBar.visibility = View.GONE
//                    ToastUtils.show(this,"加载失败")
//                }
//            }
//        }
    }

    override fun getLayoutID(): Int {
        return (R.layout.activity_paging)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}