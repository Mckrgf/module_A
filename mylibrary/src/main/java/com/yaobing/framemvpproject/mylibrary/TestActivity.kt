package com.yaobing.framemvpproject.mylibrary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.yaobing.framemvpproject.mylibrary.activity.IntentRouter
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestBinding
import com.yaobing.framemvpproject.mylibrary.function.JavaBestSingleton
import com.yaobing.framemvpproject.mylibrary.function.SingletonKotlin
import com.yaobing.module_apt.*
import com.yaobing.module_middleware.Utils.*
import com.yaobing.module_middleware.activity.BaseActivity
import java.lang.reflect.Field
import java.util.*

@Router("asdf")
class TestActivity : BaseActivity() {
    @BindByTag("bt_a")
    @BindByTagA("bt_a")
    @BindByTagB("bt_a")
    @BindByTagC("bt_a")
    var btA: Button? = null
    var hide = true
    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("zxv", "onCreate")
        setContentView(binding.root)
        binding.btA.setOnClickListener {
            bindTag(this, rootView)
            IntentRouter.go(this, "MainActivity")


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

        binding.btPaging.setOnClickListener {
            IntentRouter.go(this, "PagingActivity")
        }
        binding.btTrans.setOnClickListener {
            var x = 0f
            x = if (hide) {
                -binding.btPaging.width.toFloat()
            } else {
                0f
            }
            binding.btPaging.animate().translationX(x)
            binding.btTrans.animate().translationX(x)
            hide = !hide
        }


        Log.d("zxcvaaa", stringLengthFunc("aaa"))
        stringMapper("Android", { input ->
            input.length
        })
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

    fun bindTag(target: Any, source: View) {
        val fields: List<Field> = getAllContextFields(target)
        if (fields.isNotEmpty()) {
            for (field in fields) {
                try {
                    field.isAccessible = true
                    val bindByTag: BindByTag = field.getAnnotation(BindByTag::class.java)
                    val tag: String = bindByTag.value
                    field[target] = source.findViewWithTag(tag)
                    try {
                        val button = field[this@TestActivity]
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
                fieldList.addAll(Arrays.asList(*tempClass.declaredFields))
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

}

