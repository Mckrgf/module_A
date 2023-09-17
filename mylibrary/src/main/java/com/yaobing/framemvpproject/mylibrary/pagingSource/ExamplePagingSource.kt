package com.yaobing.framemvpproject.mylibrary.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState

//class ExamplePagingSource(
//    val backend: ExampleBackendService,
//    val query: String
//) : PagingSource<Int, Any>() {
//    override suspend fun load(
//        params: LoadParams<Int>
//    ): LoadResult<Int, Any> {
//        try {
//            // Start refresh at page 1 if undefined.
//            val nextPageNumber = params.key ?: 1
//            val response = backend.searchUsers(query, nextPageNumber)
//            return LoadResult.Page(
//                data = response.users,
//                prevKey = null, // Only paging forward.
//                nextKey = response.nextPageNumber
//            )
//        } catch (e: Exception) {
//            // Handle errors in this block and return LoadResult.Error for
//            // expected errors (such as a network failure).
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
//        // Try to find the page key of the closest page to anchorPosition from
//        // either the prevKey or the nextKey; you need to handle nullability
//        // here.
//        //  * prevKey == null -> anchorPage is the first page.
//        //  * nextKey == null -> anchorPage is the last page.
//        //  * both prevKey and nextKey are null -> anchorPage is the
//        //    initial page, so return null.
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}