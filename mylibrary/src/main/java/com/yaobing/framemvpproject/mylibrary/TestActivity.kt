package com.yaobing.framemvpproject.mylibrary

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import com.yaobing.framemvpproject.mylibrary.function.JavaBestSingleton
import com.yaobing.module_apt.*
import com.yaobing.module_middleware.Utils.ToastUtils
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("zxcv","测试btA是否已经获取了")
        val a = rootView.findViewWithTag<View>("bt_a")

        findViewById<Button>(R.id.bt_a).setOnClickListener {
            bindTag(this,rootView)
            IntentRouter.go(this, "MainActivity")

            //单例模式创建对象
            val javaBestSingleton = JavaBestSingleton.getInstance()
            Log.d("zxcv",javaBestSingleton.toString())
        }
//        bindTag(this,rootView)
    }

    override fun initListener() {
        super.initListener()
        findViewById<Button>(R.id.et_b).addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                ToastUtils.show(this,it.toString().checkComma())
            }
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        super.initView()
        Log.d("zxcv","测试btA是否已经获取了")
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

}

fun String.checkComma() :String {
    return if (this.contains(",")) {
        "有逗号"
    }else {
        "没有逗号"
    }
}