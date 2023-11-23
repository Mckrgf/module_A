package com.yaobing.framemvpproject.mylibrary.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.network.GithubRepoService

class ExamplePagingSource(private val service: GithubRepoService) : PagingSource<Int, RepoData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoData> {
        return try {
            Log.d("zxcv","pagingSource开始load ${params.key}" + "size:" + "${params.loadSize}" )
            // Start refresh at page 1 if undefined.
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = service.searchRepos(page, pageSize)
            val responseItem = repoResponse.items
            val preKey = if (page > 1) page - 1 else null
            val nextKet = if (responseItem.isNotEmpty()) page + 1 else null
            Log.d("zxcv","获取到结果preKey"  + preKey + "nextKey" + nextKet  + "responseItem" + responseItem.toString())
            LoadResult.Page(responseItem, preKey, nextKet)

        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoData>): Int? = null
}