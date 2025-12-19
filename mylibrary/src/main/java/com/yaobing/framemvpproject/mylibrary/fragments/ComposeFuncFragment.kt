package com.yaobing.framemvpproject.mylibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.setViewTreeLifecycleOwner
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.composeView.MessageCard
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.yaobing.framemvpproject.mylibrary.data.RepoData
import com.yaobing.framemvpproject.mylibrary.data.Repository
import androidx.paging.LoadState


open class ComposeFuncFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saved: Bundle?) = inflater.inflate(R.layout.fragment_compose, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val composeView = view.findViewById<ComposeView>(R.id.composeView)

        composeView.setViewTreeLifecycleOwner(viewLifecycleOwner)

        composeView.setContent {
            ComposeView()
        }
    }

    @Preview(widthDp = 360, heightDp = 640)
    @Composable
    open fun ComposeView() {
        var count by remember { mutableIntStateOf(0) }
        Box(
            modifier = Modifier
                .fillMaxSize() // 关键：让 Box 占满整个屏幕（宽高都匹配父容器）
                .background(color = colorResource(id = R.color.color_a59a93))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10))
                    .background(color = colorResource(id = R.color.white))
                    .verticalScroll(rememberScrollState()), // 添加的代码：实现垂直滚动
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                // 用 Box 包一层，真正做圆角裁剪
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(10))   // 圆角裁在这里
                        .background(color = colorResource(id = R.color.color_6c9881))
                ) {
                    Row(
                        modifier = Modifier
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "count 0: $count", modifier = Modifier.padding(1.dp))
                        VerticalDivider(
                            thickness = 1.dp, color = colorResource(id = R.color.color_dddddd), modifier = Modifier
                                .fillMaxHeight()
                                .padding(5.dp)
                        )
                        Text(text = "count 1: $count", modifier = Modifier.padding(1.dp))   // 顺手把 typo 也修了
                        VerticalDivider(
                            thickness = 1.dp, color = colorResource(id = R.color.color_dddddd), modifier = Modifier
                                .fillMaxHeight()
                                .padding(5.dp)
                        )
                        Text(text = "count 2: $count", modifier = Modifier.padding(1.dp))
                    }
                }
                Text(text = "ComposeFuncFragment", modifier = Modifier.padding(5.dp))
                Text(text = "count: $count", modifier = Modifier.padding(5.dp))
                MessageCard(content = "Hello Compose", modifier = Modifier.padding(5.dp))
                Button(
                    onClick = { count++ },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = "Increment")
                }
                val pagingItems: LazyPagingItems<RepoData> = Repository.getPagingData().collectAsLazyPagingItems()

                LazyColumn(
                    modifier = Modifier.height(250.dp)  // 去掉 fillMaxWidth()，让宽度自适应，由父 Column 的 horizontalAlignment 居中
                ) {
                    // 用旧版 paging-compose 的写法
                    items(pagingItems.itemCount) { index ->
                        val repo = pagingItems[index]
                        repo?.let {
                            Row(modifier = Modifier.fillMaxWidth()) {
//                                items(3) {
                                Text(text = repo.name + ": " ?: "", maxLines = 1)
                                Text(text = repo.url ?: "", modifier = Modifier.weight(1f))
//                                }
                            }

                        }
                    }

                    // 处理加载状态
                    pagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { Text("Loading...") }
                            }

                            loadState.append is LoadState.Loading -> {
                                item { Text("Loading more...") }
                            }

                            loadState.refresh is LoadState.Error -> {
                                item { Text("Error: ${(loadState.refresh as LoadState.Error).error.message}") }
                            }
                        }
                    }
                }
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FuncFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComposeFuncFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}