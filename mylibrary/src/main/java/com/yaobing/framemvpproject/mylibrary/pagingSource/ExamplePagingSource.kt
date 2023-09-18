//package com.yaobing.framemvpproject.mylibrary.pagingSource
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.yaobing.framemvpproject.mylibrary.network.APIService
////import com.yaobing.framemvpproject.mylibrary.presenter.RepoPresenter
//import com.yaobing.module_middleware.network.Api
//import java.util.HashMap
//
//class ExamplePagingSource(
////    val backend: RepoPresenter,
//    val query: String
//) : PagingSource<Int, Any>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
//        try {
//            // Start refresh at page 1 if undefined.
//            val currentPage = 1
//            val perPage = 2
//            val name = "MCKRGF"
//            val queryMap: HashMap<String, Int> = HashMap()
//            queryMap["page"] = currentPage
//            queryMap["per_page"] = perPage
//
//            val nextPageNumber = params.key ?: 1
//            val response = Api.getInstance().retrofit.create(APIService::class.java).listRx(name,queryMap)
//            return LoadResult.Page(
//                data = response,
//                prevKey = null, // Only paging forward.
//                nextKey = 2
//            )
//        } catch (e: Exception) {
//            // Handle errors in this block and return LoadResult.Error for
//            // expected errors (such as a network failure).
//            return LoadResult.Page(
//                data = arrayListOf(),
//                prevKey = null, // Only paging forward.
//                nextKey = 2
//            )
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