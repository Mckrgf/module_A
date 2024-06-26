package com.yaobing.framemvpproject.mylibrary

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yaobing.framemvpproject.mylibrary.viewModel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AniRecyclerActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ani_recycler)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = repoAdapter
        lifecycleScope.launch {
            viewModel.getGithubRepoData().collect {
                repoAdapter.submitData(it)
            }
        }
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

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
}