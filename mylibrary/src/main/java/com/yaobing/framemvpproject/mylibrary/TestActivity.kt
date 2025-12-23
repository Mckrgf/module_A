package com.yaobing.framemvpproject.mylibrary

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.inewise.QRcodeUtil
import com.yaobing.framemvpproject.mylibrary.activity.IntentRouter
import com.yaobing.framemvpproject.mylibrary.activity.WebpActivity
import com.yaobing.framemvpproject.mylibrary.activity.activity.HOmeActivity
import com.yaobing.framemvpproject.mylibrary.activity.activity.SnapshotActivity
import com.yaobing.framemvpproject.mylibrary.databinding.ActivityTestBinding
import com.yaobing.framemvpproject.mylibrary.function.JavaBestSingleton
import com.yaobing.framemvpproject.mylibrary.function.SingletonKotlin
import com.yaobing.framemvpproject.mylibrary.util.XlogUtil
import com.yaobing.module_apt.BindByTag
import com.yaobing.module_apt.Router
import com.yaobing.module_common_view.views.PageDragView
import com.yaobing.module_middleware.Utils.Person
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
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.core.graphics.createBitmap
import com.blankj.utilcode.util.DeviceUtils
import com.yaobing.framemvpproject.mylibrary.util.XlogUtil.generateRandomStrings


@Router("asdf")
class TestActivity : BaseActivity() {
    private var hide = true
    private val binding by lazy {
        ActivityTestBinding.inflate(layoutInflater)
    }
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 处理返回的结果
            Log.d("zxcv","get data : "+result.data?.getStringExtra("cccc").toString())
        }
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

    @RequiresApi(Build.VERSION_CODES.R)
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
        setContentView(binding.root)
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

        ActivityCompat.requestPermissions(this, permissions, 1)

        stringMapper("Android") { input ->
            input.length
        }


        initTextViewImage()

        initDragView()

        initImageViewLeftCorner()

        //该方法必须在真机上调用
        if (!DeviceUtils.isEmulator()) {
//            XlogUtil.initXlog(this)
            XlogUtil.initXlogA(this)
        }
        measureAndSetText()
    }

    private fun initMultiLineText(text1: String) {
        val combinedText = findViewById<TextView>(R.id.tv_multi_line)

        // 第二个文本内容
        val text2 = "[附加信息]"
        // 初始化画笔，用于测量文本宽度
        val paint = Paint().apply {
            textSize = combinedText.textSize // 使用容器的默认文字大小
        }

        // 测量第一个文本最后一行的可用宽度
        val availableWidth = calculateAvailableWidth(combinedText, text1, paint)

        // 测量第二个文本需要的宽度
        val text2Width = paint.measureText(text2)

        // 组合最终文本（根据空间判断是否换行）
        val finalText = if (text2Width <= availableWidth) {
            // 第一行空间足够，直接拼接
            text1 + text2
        } else {
            // 第一行空间不足，换行显示第二个文本
            text1 + "\n" + text2
        }

        // 创建SpannableString组合两个文本
        val spannable = SpannableString(finalText)

        // 可以给第二个文本设置不同样式（可选）
        val text2Color = ForegroundColorSpan("#FF5722".toColorInt())
        spannable.setSpan(
            text2Color,
            text1.length,  // 起始位置（第一个文本结束处）
            text1.length + text2.length + if (finalText.contains("\n")) 1 else 0,  // 结束位置
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // 2. 给第二个文本设置不同字体大小（单位：像素）
        // 注意：如果需要dp单位，可通过TypedValue.convertDpToPx()转换
        val text2Size = AbsoluteSizeSpan(10, true) // 第二个参数true表示单位为sp
        spannable.setSpan(
            text2Size,
            text1.length,
            text1.length + text2.length + if (finalText.contains("\n")) 1 else 0,  // 结束位置,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // 设置组合后的文本
        combinedText.text = spannable
    }
    /**
     * 计算第一个文本最后一行的可用宽度
     */
    private fun calculateAvailableWidth(container: TextView, text: String, paint: Paint): Float {
        // 容器可用宽度（减去内边距）
        val containerWidth = container.width - container.paddingLeft - container.paddingRight

        if (containerWidth <= 0) {
            // 如果还没测量完成，使用默认宽度估算
            return 500f // 可根据实际需求调整
        }

        // 测量文本总宽度
        val textWidth = paint.measureText(text)

        // 如果文本总宽度小于容器宽度，第一行剩余空间就是容器宽度 - 文本宽度
        if (textWidth <= containerWidth) {
            return containerWidth - textWidth
        }

        // 文本超过一行，计算最后一行的可用宽度
        // 这里简化处理，实际复杂情况可能需要更精确的测量
        return containerWidth.toFloat()
    }
    private fun initImageViewLeftCorner() {
        //加载原图（不含圆角，做个对比）
        Glide.with(this)
            .load("https://ueapp.oss-cn-hangzhou.aliyuncs.com/nativeApp/test/14465cc46b69453d8c8f367eeafd5eb3")
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.ivSsl)

        val cornerTransform = CornerTransform(this, 10f);
        cornerTransform.setNeedCorner(true, false, true, false);
        val centerCropOptions = RequestOptions().transform(CenterCrop(), cornerTransform);
        //加载图片，只在左侧做圆角处理
        Glide.with(this)
            .load("https://ueapp.oss-cn-hangzhou.aliyuncs.com/nativeApp/test/14465cc46b69453d8c8f367eeafd5eb3")
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .apply(centerCropOptions)
            .into(binding.ivRoundSpec)
    }

    private fun initTextViewImage() {
        //用TextView的drawableTop设置图片，有隐患：获取到的图片如果不裁剪那就很大，裁剪了指定大小又是根据左上的图片内容。
        //所以最后还是采取textview+iamgeview的方式来实现，这样能完美展示缩放图片
        val rul = "https://ueapp-oss-static.leapmotor.com/img/app/club/20230925/4d2c9bdab1d74515844c100519a22b35.png"
        Glide.with(context).asBitmap().load(rul).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap?>?
            ) {
                val drawables = binding.sharelibIvClubChat.compoundDrawablesRelative // 推荐使用 Relative (Start/End)
                val originalTopDrawable = drawables[1]
                if (originalTopDrawable != null) {
                    val originalBounds = originalTopDrawable.bounds
                    val width = if (originalBounds.isEmpty) {
                        originalTopDrawable.intrinsicWidth.takeIf { it > 0 } ?: 0
                    } else {
                        originalBounds.width()
                    }
                    val height = if (originalBounds.isEmpty) {
                        originalTopDrawable.intrinsicHeight.takeIf { it > 0 } ?: 0
                    } else {
                        originalBounds.height()
                    }
                    val newResource = resource.scale(133, 133)
                    val newDrawable = newResource.toDrawable(context.resources)
                    newDrawable.setBounds(0, 0, width, height)
                    binding.sharelibIvClubChat.setCompoundDrawablesWithIntrinsicBounds(null, newDrawable, null, null)
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initDragView() {
        val dragView = PageDragView(this)
        //加载可拖动的图片
        val img: ImageView = dragView.findViewById<ImageView>(com.yaobing.module_common_view.R.id.iv_img)
        Glide.with(context)
            .load(resources.getDrawable(R.mipmap.bb, null))
            .into(img)
        dragView.initDrag(this)
    }

    // 工具方法：dp转px
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
    private fun animateSeekBar(seekBar: SeekBar, progress: Int) {
        val scale = 1 + progress.toFloat() / seekBar.max // 计算变大的比例
        seekBar.scaleX = scale // 设置X轴方向的缩放比例
        seekBar.scaleY = scale // 设置Y轴方向的缩放比例
    }


    @SuppressLint("Range")
    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(Images.Media.EXTERNAL_CONTENT_URI, arrayOf("_id"), "_data=? ", arrayOf(filePath), null as String?)
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("_id"))
            val baseUri = "content://media/external/images/media".toUri()
            cursor.close()
            Uri.withAppendedPath(baseUri, id.toString())
        } else if (imageFile.exists()) {
            val values = ContentValues()
            values.put("_data", filePath)
            context.contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            null
        }
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


    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = createBitmap(
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

    //kt高阶函数,传入一个字符串，返回一个Int类型的长度
    private fun stringMapper(str: String, mapper1: (String) -> Int): Int {
        return mapper1(str)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "QueryPermissionsNeeded")
    override fun initListener() {
        var isBold = true
        super.initListener()
        binding.etB.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                //给Edittext设置扩展属性
                isBold = !isBold
                binding.etB.isBold = !isBold
                ToastUtils.show(this, it.toString().checkComma())
                initMultiLineText(it.toString())

            }
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
                Log.d("zxcv","bitmapCompress size ${bitmapCompress.byteCount}")
                binding.circleBordImage.setImageBitmap(bitmapCompress)
            }
        }
        binding.btLifeCycler.setOnClickListener {
            val intent = Intent(this, HOmeActivity::class.java)
            startActivity(intent)
        }
        binding.btRsa.setOnClickListener {
            val token = "eyJpc3MiOiJKb2huIeyJpc3MiOiJKb2heyJpc3MiOiJKb2huIeyJpc3MiOiJKb2h"
            Log.d("zxcv", "token: $token")
            val tokenEncode = AESUtils.encrypt(token)
            Log.d("zxcv", "tokenEncode: $tokenEncode")
            val token1Encode = AESUtils.decrypt(tokenEncode)
            Log.d("zxcv", "token1Encode: $token1Encode")
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
        binding.btB.setOnClickListener {
            val person = Person(30, "敲代码")
            person.work = "敲代码"
            person.say("我用扩展方法说话了：" + person.work + ";且我跳转到moduleB页面了")

            IntentRouter.go(this, "TestCActivity")
        }

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progressDrawable = getDrawable(R.drawable.custom_seekbar_track)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progressDrawable = getDrawable(R.drawable.custom_seekbar_track_low)
            }

        })

        binding.btC.setOnClickListener {
            IntentRouter.go(this, "testd")
        }
        binding.btRecyclerView.setOnClickListener {
            IntentRouter.go(this, "recyclerviewactivity")
        }
        binding.btDoubleRecyclerView.setOnClickListener {
            IntentRouter.go(this, "doubleRecyclerviewactivity")
        }
        binding.btTimeLineRecyclerView.setOnClickListener {
            IntentRouter.go(this, "timeLineRecyclerviewactivity")
        }
        binding.btPathUri.setOnClickListener {
            val path = "/storage/emulated/0/Android/data/com.dahua.leapmotor/cache/Leapmotor/smallVideo/tempPic_1729161414328.jpg"

            val uri = Uri.fromFile(File(path))
            val newPath = uri.path
            val fileExist = File(newPath).exists()

            val uriB = getImageContentUri(this,File(path))
            val newPathB = uriB?.path
            val fileBExist = newPathB?.let { it1 -> File(it1).exists() }
            val bitmap = Images.Media.getBitmap(this.contentResolver, uri)//这里获取不到，转换为uri之后无法获取文件了很奇怪
//            val fileBExist1 = File(URI.create(newPathB))
        }

        binding.btPaging.setOnClickListener {
            IntentRouter.go(this, "PagingActivity")
        }
        binding.btLarge.setOnClickListener {
            IntentRouter.go(this, "teste")
        }
        binding.btCeilingA.setOnClickListener {
            IntentRouter.go(this, "CeilingAlphaActivity")
        }
        binding.btWebp.setOnClickListener {
            val intent = Intent(this, WebpActivity::class.java)
            launcher.launch(intent)
        }
        binding.customView.setOnClickListener {
            IntentRouter.go(this, "customviewactivity")
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
        binding.btFuncShow.setOnClickListener {
            IntentRouter.go(this, "funcShowActivity")
        }
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
                for (i in 0..59999) {
                    com.tencent.mars.xlog.Log.d("zxcv", "我要打印了：$i" + generateRandomStrings(10))
                }
                com.tencent.mars.xlog.Log.appenderFlush()
                com.tencent.mars.xlog.Log.d("zxcv", "打印完了")
            }.start()
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

    override fun onDestroy() {
        super.onDestroy()
        com.tencent.mars.xlog.Log.appenderClose();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            return
        }
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
}