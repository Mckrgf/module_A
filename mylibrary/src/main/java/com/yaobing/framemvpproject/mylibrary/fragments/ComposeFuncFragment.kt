package com.yaobing.framemvpproject.mylibrary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.composeView.MessageCard


open class ComposeFuncFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saved: Bundle?) = inflater.inflate(R.layout.fragment_compose, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val composeView = view.findViewById<ComposeView>(R.id.composeView)

        composeView.setViewTreeLifecycleOwner(viewLifecycleOwner)

        composeView.setContent {
            setComposeView()
        }
    }

    @Preview
    @Composable
    open fun setComposeView() {
        var count by remember { mutableIntStateOf(0) }
        Box(
            modifier = Modifier
                .fillMaxSize() // 关键：让 Box 占满整个屏幕（宽高都匹配父容器）
                .background(color = colorResource(id = R.color.color_a59a93))
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.white)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                // 用 Box 包一层，真正做圆角裁剪
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(10))   // 圆角裁在这里
                        .background(color = colorResource(id = R.color.color_6c9881))
                ) {
                    Row(
                        modifier = Modifier
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "count 0: $count", modifier = Modifier.padding(15.dp))
                        VerticalDivider(thickness = 1.dp, color = colorResource(id = R.color.color_dddddd), modifier = Modifier.fillMaxHeight().padding(5.dp))
                        Text(text = "count 1: $count", modifier = Modifier.padding(15.dp))   // 顺手把 typo 也修了
                        VerticalDivider(thickness = 1.dp, color = colorResource(id = R.color.color_dddddd), modifier = Modifier.fillMaxHeight().padding(5.dp))
                        Text(text = "count 2: $count", modifier = Modifier.padding(15.dp))
                    }
                }
                Text(text = "ComposeFuncFragment", modifier = Modifier.padding(16.dp))
                Text(text = "count: $count", modifier = Modifier.padding(16.dp))
                MessageCard(content = "Hello Compose", modifier = Modifier.padding(16.dp))
                Button(
                    onClick = { count++ },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Increment")
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