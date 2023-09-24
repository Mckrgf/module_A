package com.yaobing.framemvpproject.mylibrary.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.data.Repository
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    fun getGithubRepoData() : Flow<PagingData<RepoData>> {
        return Repository.getPagingData().cachedIn(viewModelScope)
    }
}