package com.yaobing.framemvpproject.mylibrary

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.penfeizhou.animation.apng.APNGDrawable
import com.github.penfeizhou.animation.gif.GifDrawable
import com.yaobing.framemvpproject.mylibrary.activity.IntentRouter
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestBinding
import com.yaobing.framemvpproject.mylibrary.function.JavaBestSingleton
import com.yaobing.framemvpproject.mylibrary.function.SingletonKotlin
import com.yaobing.module_apt.*
import com.yaobing.module_middleware.Utils.*
import com.yaobing.module_middleware.activity.BaseActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.reflect.Field
import java.util.*

@Router("asdf")
class TestActivity : BaseActivity() {
    private var hide = true
    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("zxv", "onCreate")
        setContentView(binding.root)
        val aaa = packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA).metaData.get("aaa")
        ToastUtils.show(this,aaa.toString(),0)
        Log.i("zxcv",aaa.toString())
        binding.btA.setOnClickListener {
            bindTag(this, binding.root)
            IntentRouter.go(this, "MainActivity")
            var a :RoundedBorderImageView

            //单例模式创建对象
            val javaBestSingleton = JavaBestSingleton.getInstance()
            Log.d("zxcv java 静态内部类", javaBestSingleton.toString())

            //kotlin单例模式：懒汉
            val kotlinSingleton = SingletonKotlin.getSingle()
            Log.d("zxcv kotlin 懒汉", kotlinSingleton.toString())
        }


        binding.btB.setOnClickListener {
            val person = Person(30, "敲代码")
            person.work = "敲代码"
            person.say("我用扩展方法说话了：" + person.work + ";且我跳转到moduleB页面了")

            IntentRouter.go(this, "TestCActivity")
        }

        binding.btC.setOnClickListener {
            IntentRouter.go(this, "testd")
        }

        binding.btPaging.setOnClickListener {
            IntentRouter.go(this, "PagingActivity")
        }
        binding.btCeilingA.setOnClickListener {
            IntentRouter.go(this, "CeilingAlphaActivity")
        }
        binding.btTrans.setOnClickListener {
            val x: Float = if (hide) {
                -binding.btPaging.width.toFloat()
            } else {
                0f
            }
            binding.btPaging.animate().translationX(x)
            binding.btTrans.animate().translationX(x).startDelay = 100L
            hide = !hide
        }

        stringMapper("Android") { input ->
            input.length
        }

        binding.etCircle.addTextChangedListener {
            try {
                if (it.toString().isNotEmpty() && it.toString().toFloat() in 0f..1f) {
                    binding.circleImage.roundPercent = it.toString().toFloat()
                }
            }catch (e: java.lang.Exception) {
                ToastUtils.show(this,e.toString())
            }
        }
        Glide.with(context)
//            .asGif()
            .load("file:///android_asset/test5.png")
            .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                if (resource is APNGDrawable) {
                    (resource as APNGDrawable).setLoopLimit(10)
                }
                return false
            }
        })
            .into(binding.ivDfds)

    }
    public fun bitmapInputStream(bm: Bitmap, quality:Int) : InputStream {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos)
        return ByteArrayInputStream(baos.toByteArray())
    }


    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap = Bitmap
            .createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        val canvas = Canvas(bitmap)

// canvas.setBitmap(bitmap);
        drawable.setBounds(
            0, 0, drawable.intrinsicWidth,
            drawable.intrinsicHeight
        )
        drawable.draw(canvas)
        return bitmap
    }
    //kotlin匿名函数
    val stringLengthFunc: (String) -> String = { input ->
        "输入的字符串$input 的长度为" + input.length
    }

    //kt高阶函数
    private fun stringMapper(str: String, mapper1: (String) -> Int): Int {
        // Invoke function
        return mapper1(str)
    }

    override fun initListener() {
        var isBold = true
        super.initListener()
        binding.etB.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                //给Edittext设置扩展属性
                isBold = !isBold
                binding.etB.isBold = !isBold
                ToastUtils.show(this, it.toString().checkComma())
                //hahahaha
                //haha

            }
        }
    }

    override fun getLayoutID(): Int {
        return NO_VIEW
    }

    override fun initView() {
        super.initView()
        Log.d("zxcv", "测试btA是否已经获取了")
    }

    private fun bindTag(target: Any, source: View) {
        val fields: List<Field> = getAllContextFields(target)
        if (fields.isNotEmpty()) {
            for (field in fields) {
                try {
                    field.isAccessible = true
                    val bindByTag: BindByTag =
                        field.getAnnotation(BindByTag::class.java) as BindByTag
                    val tag: String = bindByTag.value
                    field[target] = source.findViewWithTag(tag)
                    try {
                        field[this@TestActivity]
                        Log.d("zxcv", "墙砖成功")
                    } catch (e: Exception) {
                        Log.d("zxcv", "墙砖失败")
                    }
                    continue

//                    String fieldName = field.getName();
//                    field.set(target, source.findViewWithTag(fieldName));
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getAllContextFields(target: Any): List<Field> {
        val fieldList: MutableList<Field> = ArrayList()
        var tempClass: Class<*>? = target.javaClass
        while (tempClass != null) { //当父类为null的时候说明到达了最上层的父类(Object类).
            tempClass = if (tempClass.name.contains("com.yaobing.framemvpproject")) {
                null
            } else {
                fieldList.addAll(listOf(*tempClass.declaredFields))
                tempClass.superclass //得到父类,然后赋给自己
            }
        }
        return fieldList
    }


    init {
        Log.d("zxv", "init了")
    }

    override fun onStart() {
        super.onStart()
        Log.d("zxv", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("zxv", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("zxv", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("zxv", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("zxv", "onDestroy")
    }

    fun testNull() {
        var data_a :String? = null
        var data_b :String?  = "listOf<Any>()"
        if (Math.random().equals(1f)) {
            data_a = "aa"
        }
        try {
            if (TextUtils.isEmpty(data_a)) {
                Log.d("zxcv","data_a1?")
            }else {
                Log.d("zxcv",data_a!!)
            }

        }catch (e : java.lang.Exception) {
            Log.d("zxcv","e")
        }
    }

}

