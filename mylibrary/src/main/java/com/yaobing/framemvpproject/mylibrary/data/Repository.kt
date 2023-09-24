package com.yaobing.framemvpproject.mylibrary.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yaobing.framemvpproject.mylibrary.network.GithubRepoService
import com.yaobing.framemvpproject.mylibrary.pagingSource.ExamplePagingSource
import kotlinx.coroutines.flow.Flow

object Repository {
    private const val PAGE_SIZE = 5

    private val gitHubService = GithubRepoService.create()

    fun getPagingData(): Flow<PagingData<RepoData>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { ExamplePagingSource(gitHubService) }
        ).flow
    }
}