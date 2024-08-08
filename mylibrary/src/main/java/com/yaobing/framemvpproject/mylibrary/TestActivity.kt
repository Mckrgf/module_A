package com.yaobing.framemvpproject.mylibrary

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.inewise.QRcodeUtil
import com.yaobing.framemvpproject.mylibrary.activity.IntentRouter
import com.yaobing.framemvpproject.mylibrary.activity.activity.HOmeActivity
import com.yaobing.framemvpproject.mylibrary.activity.activity.SnapshotActivity
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestBinding
import com.yaobing.framemvpproject.mylibrary.function.JavaBestSingleton
import com.yaobing.framemvpproject.mylibrary.function.SingletonKotlin
import com.yaobing.module_apt.BindByTag
import com.yaobing.module_apt.Router
import com.yaobing.module_middleware.Utils.Person
import com.yaobing.module_middleware.Utils.ScreenUtils
import com.yaobing.module_middleware.Utils.ToastUtils
import com.yaobing.module_middleware.Utils.checkComma
import com.yaobing.module_middleware.Utils.isBold
import com.yaobing.module_middleware.Utils.say
import com.yaobing.module_middleware.activity.BaseActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Field


@Router("asdf")
class TestActivity : BaseActivity() {
    private var hide = true
    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }

    private var imageFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        com.tencent.mars.xlog.Log.d("zcxv", "dfsf")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val widgetContent = intent.getStringExtra("aa")
        Log.d("zxcv","准备获取widget携带的数据$widgetContent")
        if (!TextUtils.isEmpty(widgetContent)) {
            ToastUtils.show(this,widgetContent)
        }
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ),
            0
        )
        Log.d("zxv", "onCreate")
        setContentView(binding.root)
        val aaa = packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA
        ).metaData.get("aaa")
//        ToastUtils.show(this,aaa.toString(),0)
        Log.i("zxcv", aaa.toString())
        binding.btA.setOnClickListener {
            bindTag(this, binding.root)
            IntentRouter.go(this, "MainActivity")
            var a: RoundedBorderImageView

            //单例模式创建对象
            val javaBestSingleton = JavaBestSingleton.getInstance()
            Log.d("zxcv java 静态内部类", javaBestSingleton.toString())

            //kotlin单例模式：懒汉
            val kotlinSingleton = SingletonKotlin.getSingle()
            Log.d("zxcv kotlin 懒汉", kotlinSingleton.toString())

//            saveTestFile()
//            checkFile()


            //1w次 110k左右
            //10w次 1200k左右
            //10.w次 10700k左右
            Thread {
                for (i in 0..998) {
                    com.tencent.mars.xlog.Log.d("zxcv", "我要打印了：$i")
                }
                com.tencent.mars.xlog.Log.appenderFlush()
                com.tencent.mars.xlog.Log.d("zxcv", "打印完了")
            }.start()
        }

        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

        ActivityCompat.requestPermissions(this, permissions, 1)
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
        binding.updateWidgetBroadcast.setOnClickListener {

            val intent = Intent("com.yaobing.framemvpproject.mylibrary.update")
            val appWidgetManager = AppWidgetManager.getInstance(context) // 获取 AppWidgetManager 实例
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, javaClass))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds)
            context.sendBroadcast(intent)
        }
        binding.btCamera.setOnClickListener {
            imageFile = createImageFile()
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile))
//            startActivityForResult(intent, 111)

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 请求权限
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 111);
            } else {
                // 权限已经被授予，可以进行相关操作
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                // 确保有相机活动处理该Intent
                if (takePictureIntent.resolveActivity(packageManager) != null) {

                }
                startActivityForResult(takePictureIntent, 1111)
            }
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
            } catch (e: java.lang.Exception) {
                ToastUtils.show(this, e.toString())
            }

        }

        binding.btLifeCycler.setOnClickListener {
            var intent = Intent(this, HOmeActivity::class.java)
            startActivity(intent)
        }
        binding.btAni.setOnClickListener {
            val intent = Intent(this, AniRecyclerActivity::class.java) // 替换成你的新页面 Activity 类名
            startActivity(intent)
        }
        binding.btSnapshotScrollview.setOnClickListener {
            val intent = Intent(this, SnapshotActivity::class.java) // 替换成你的新页面 Activity 类名
            startActivity(intent)
        }
        binding.btConstraint.setOnClickListener {
            val intent = Intent(this, ConstraintActivity::class.java) // 替换成你的新页面 Activity 类名
            startActivity(intent)
        }
        binding.btDecodeQr.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bb)

            //加载原图（不含圆角，做个对比）
            Glide.with(context)
                .load(resources.getDrawable(R.mipmap.bb,null))
                .into(binding.ivDfds)

            //获取圆角图片
            val bitmapRound = BitmapUtil().getRoundedCornerBitmapWithoutScaling(bitmap,120f)
            binding.ivGlide.setImageBitmap(bitmapRound)

            //获取大图（在某些情况下比如小组件可能会有bitmap大小的限制，所以要进行压缩）
            val bitmapBig = BitmapFactory.decodeResource(resources, R.mipmap.big_img)
            Log.d("zxcv","bitmap size ${bitmapBig.byteCount}")
            val bitmapCompress = BitmapUtil().compressImageByScale(bitmapBig, dp2pxInt(150f),dp2pxInt(150f))
            bitmapCompress.let {
                Log.d("zxcv","bitmapCompress size ${bitmapCompress!!.byteCount}")
                binding.circleBordImage.setImageBitmap(bitmapCompress)
            }

        }

//        Glide.with(this).load("https://www.wenjianbaike.com/wp-content/uploads/2021/04/apng_wenjan.png").set(
//            AnimationDecoderOption.DISABLE_ANIMATION_GIF_DECODER, false).into(binding.ivDfds);
        val requestListener: RequestListener<com.bumptech.glide.load.resource.gif.GifDrawable> =
            object : RequestListener<com.bumptech.glide.load.resource.gif.GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<com.bumptech.glide.load.resource.gif.GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: com.bumptech.glide.load.resource.gif.GifDrawable?,
                    model: Any?,
                    target: Target<com.bumptech.glide.load.resource.gif.GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            }
        Glide.with(context)
            .asGif()
            .load("file:///android_asset/world-cup.gif")
            .listener(requestListener).into(binding.ivDfds)

//        System.loadLibrary("c++_shared")
//        System.loadLibrary("marsxlog")
        val SDCARD: String =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).absolutePath
        val logPath = "$SDCARD/marssample/log"
//        val cachePath: String = this.filesDir + "/xlog"
//        val xlog = Xlog()
//        com.tencent.mars.xlog.Log.setLogImp(xlog)
//        if (BuildConfig.DEBUG) {
//            com.tencent.mars.xlog.Log.setConsoleLogOpen(true)
//            com.tencent.mars.xlog.Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "hah", 0)
//        } else {
//            com.tencent.mars.xlog.Log.setConsoleLogOpen(false)
//            com.tencent.mars.xlog.Log.appenderOpen(
//                Xlog.LEVEL_DEBUG,
//                Xlog.AppednerModeAsync,
//                "",
//                logPath,
//                "hah",
//                0
//            )
//        }
        measureAndSetText()
    }



    fun dp2px(dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

    fun dp2pxInt(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
            .toInt()


    fun getBitmapFromAssets(fileName: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            context.assets.use { assetManager ->
                assetManager.open(fileName!!).use { asset ->
                    asset.use { inputStream ->
                        bitmap = BitmapFactory.decodeStream(inputStream)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }
    private fun decodeFile() {
        val bitmap = getBitmapFromAssets("aaaaaa.png")
        val file = File(getExternalStorageDirectory(), "Pictures/my_image.png")
        val outputStream = FileOutputStream(file)
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
        outputStream.close();
        if (file.exists()) {
            val absolutePath = file.absolutePath
            val parentDirPath = absolutePath.split("/").dropLast(1).joinToString("/")
            println("Parent Directory Path: $parentDirPath")
            val decodeString = QRcodeUtil.simpleDecode("my_image", "png", "$parentDirPath/", 1)
            Log.d("zxcv", decodeString)
        }
    }

    private fun createImageFile(): File {
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File(storageDir, "IMG_" + System.currentTimeMillis() + ".jpg")
    }

    /**
     * 根据文字内容动态设置textview宽度
     * 乍一看无用：用warp不就行了
     * 其实不然，这个功能的用处如下：
     * 一个tablelayout内有5个item，需要这五个item根据内容长度来显示item宽度且充满屏幕
     * 但是tablelayout原生不满足需求，warp的话（也就是scrollable mode）并不会充满屏幕。
     * 而充满屏幕的mode（fixed）是五个item均分屏幕空间，且不可修改宽度，也不满足需求。
     * 所以就应该在scrollable模式下测试出各个item的宽度，计算出他们的宽度的权重，基于屏幕宽度及间隔要求重新设置item的新宽度。
     */
    private fun measureAndSetText() {
        val testContent = "我是测试文字"
        val width = binding.tvTest.paint.measureText(testContent)
        val layoutParams = binding.tvTest.layoutParams
        layoutParams.width = width.toInt()
        binding.tvTest.text = testContent
    }

    private fun checkFile() {
        val file = File(filesDir, "haha_20240407.xlog")
        Log.d("zxcv", "file大小：" + file.length())
        com.tencent.mars.xlog.Log.d("zxcv", "file大小：" + file.length())
    }

    private fun saveTestFile() {
        // 创建一个 File 对象，指定要保存的文件的路径
        val file = File(filesDir, "my_file.txt")

        // 使用 OutputStream 类将文件内容写入到 File 对象中
        val outputStream: OutputStream = FileOutputStream(file)
        outputStream.write("This is a text file.".toByteArray())
        outputStream.close()
        Log.d("zxcv", "保存了文件" + file.length())
        com.tencent.mars.xlog.Log.d("zxcv", "保存了文件" + file.length())
    }

    public fun bitmapInputStream(bm: Bitmap, quality: Int): InputStream {
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
        com.tencent.mars.xlog.Log.appenderClose();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        decodeFile()
        if (requestCode == 111 && resultCode == RESULT_OK) {
            // 照片已成功拍摄
            copyImageToExternalContentUri(imageFile!!)
        } else {
            // 处理失败
        }
    }

    private fun copyImageToExternalContentUri(imageFile: File) {
//        val resolver = contentResolver
//        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val dstUri = resolver.insert(contentUri, ContentValues())
//
//        try {
//            FileInputStream(imageFile).use { fis ->
//                resolver.openOutputStream(dstUri!!).use { os ->
//                    IOUtils.copy(fis, os)
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }
//    fun testNull() {
//        var data_a :String? = null
//        var data_b :String?  = "listOf<Any>()"
//        if (Math.random().equals(1f)) {
//            data_a = "aa"
//        }
//        try {
//            if (TextUtils.isEmpty(data_a)) {
//                Log.d("zxcv","data_a1?")
//            }else {
//                Log.d("zxcv",data_a!!)
//            }
//
//        }catch (e : java.lang.Exception) {
//            Log.d("zxcv","e")
//        }
//    }

}

