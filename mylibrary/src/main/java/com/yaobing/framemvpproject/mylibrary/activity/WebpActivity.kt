package com.yaobing.framemvpproject.mylibrary.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityWebpBinding
import com.yaobing.module_apt.Router

@Router(value = "webpactivity")
class WebpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWebpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        Glide.with(this)
            .load("https://ueapp.oss-cn-hangzhou.aliyuncs.com/nativeApp/test/aaaaa.webp")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    return false
                }

            })
            .into(binding.ivA)
        Glide.with(this)
            .load("https://ueapp.oss-cn-hangzhou.aliyuncs.com/nativeApp/test/bbbbb.webp")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    return false
                }

            })
            .into(binding.ivB)
        Glide.with(this)
//            .load("https://ueapp.oss-cn-hangzhou.aliyuncs.com/nativeApp/test/ccccc.webp")
            .load("https://bbc-sit.oss-cn-hangzhou.aliyuncs.com/2024/08/13/33fd2abeeea64c60a4cabaeca2be0468")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    return false
                }

            })
            .into(binding.ivC)
    }
}