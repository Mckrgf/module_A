package com.yaobing.framemvpproject.mylibrary.activity

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yaobing.framemvpproject.mylibrary.MaskView
import com.yaobing.framemvpproject.mylibrary.R
import com.yaobing.framemvpproject.mylibrary.util.BitmapUtils.imageToBase64
import com.yaobing.framemvpproject.mylibrary.util.BitmapUtils.imageToBase64ByUri
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TakeCardPictureActivity : AppCompatActivity() {

    companion object {
        const val TYPE_KEY: String = "TAKE_PIC_TYPE"
        const val PIC_BASE_64: String = "TAKE_PIC_TYPE"
        const val CONTENT_HEAD: String = "请拍摄"
        const val CONTENT_ID_FRONT: String = "身份证人面像"
        const val CONTENT_ID_BACK: String = "身份证国徽面"
        const val CONTENT_DRIVE: String = "行驶证印章页"
        const val CONTENT_PASSPORT: String = "护照人面像"
        const val CONTENT_HONGKONG: String = "港澳居民来往内地通行证人面像"
        const val CONTENT_TAIWAN: String = "台湾居民来往大陆通行证人面像"
        const val CONTENT_CAR_REGISTER: String = "机动车登记证书"
        const val CONTENT_BUSINESS_LICENSE: String = "营业执照"
        const val RESULT_BASE_64_OK: Int = 100001
    }

    private lateinit var previewView: PreviewView
    private lateinit var captureButton: View
    private lateinit var ivCardFront: ImageView
    private lateinit var tvTips: TextView
    private var imageCapture: ImageCapture? = null

    private val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    private val REQUEST_CODE_PERMISSIONS = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_card_picture)
        previewView = findViewById(R.id.previewView)
        captureButton = findViewById(R.id.iv_click_front_take_picture)
        ivCardFront = findViewById(R.id.iv_card_front)

        // 监听iv_card_front布局完成，动态设置MaskView的洞
        ivCardFront.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ivCardFront.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val rect = Rect()
                ivCardFront.getGlobalVisibleRect(rect)
                findViewById<MaskView>(R.id.v_mask).setHoleRect(rect)
            }
        })

        ivCardFront.setImageResource(
            when (intent.getIntExtra(TYPE_KEY, 0)) {
                1 -> {
                    R.mipmap.id_card_front_3
                }

                2 -> {
                    R.mipmap.id_card_back
                }

                3 -> {
                    R.mipmap.drive_3
                }

                4 -> {
                    R.mipmap.passport_3
                }

                5 -> {
                    R.mipmap.hoko_3
                }

                6 -> {
                    R.mipmap.taiwan_3
                }

                7 -> {
                    R.mipmap.lisense
                }

                8 -> {
                    R.mipmap.lisense
                }

                else -> {
                    R.mipmap.lisense
                }
            }
        )
        tvTips = findViewById(R.id.tv_tips)
        tvTips.text = when (intent.getIntExtra(TYPE_KEY, 0)) {
            1 -> {
                CONTENT_HEAD + CONTENT_ID_FRONT
            }

            2 -> {
                CONTENT_HEAD + CONTENT_ID_BACK
            }

            3 -> {
                CONTENT_HEAD + CONTENT_DRIVE
            }

            4 -> {
                CONTENT_HEAD + CONTENT_PASSPORT
            }

            5 -> {
                CONTENT_HEAD + CONTENT_HONGKONG
            }

            6 -> {
                CONTENT_HEAD + CONTENT_TAIWAN
            }

            7 -> {
                CONTENT_HEAD + CONTENT_CAR_REGISTER
            }

            8 -> {
                CONTENT_HEAD + CONTENT_BUSINESS_LICENSE
            }

            else -> {
                ""
            }
        }

        if (ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION), REQUEST_CODE_PERMISSIONS)
        }

        captureButton.setOnClickListener { takePhoto() }
        findViewById<View>(R.id.iv_close).setOnClickListener { finish() }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(previewView.surfaceProvider) }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                // 可根据需要 setTargetResolution / setTargetAspectRatio
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e("CameraXApp", "绑定相机失败", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Capture preview frame bitmap (not full-resolution, but enough for cropping UI overlay region)
        val bitmap: Bitmap? = previewView.bitmap
        if (bitmap == null) {
            Toast.makeText(this, "预览帧不可用，稍后重试", Toast.LENGTH_SHORT).show()
            Log.w("CameraXApp", "PreviewView bitmap is null")
            return
        }
        // Map iv_card_front rect to previewView coordinates
        val previewRect = Rect()
        val overlayRect = Rect()
        previewView.getGlobalVisibleRect(previewRect)
        ivCardFront.getGlobalVisibleRect(overlayRect)
        overlayRect.offset(-previewRect.left, -previewRect.top)
        // Scale view rect to bitmap space
        val scaleX = bitmap.width.toFloat() / previewView.width
        val scaleY = bitmap.height.toFloat() / previewView.height
        var left = (overlayRect.left * scaleX).toInt()
        var top = (overlayRect.top * scaleY).toInt()
        var right = (overlayRect.right * scaleX).toInt()
        var bottom = (overlayRect.bottom * scaleY).toInt()
        // Clamp
        left = left.coerceIn(0, bitmap.width)
        top = top.coerceIn(0, bitmap.height)
        right = right.coerceIn(left, bitmap.width)
        bottom = bottom.coerceIn(top, bitmap.height)
        val add = ((bottom-top)*0.2).toInt()
        //扩大20%
        top -= add
        bottom += add
        val cropW = right - left
        val cropH = bottom - top
        if (cropW <= 0 || cropH <= 0) {
            Toast.makeText(this, "裁剪区域无效", Toast.LENGTH_SHORT).show()
            Log.w("CameraXApp", "Invalid crop rect: $left,$top,$right,$bottom bitmap=${bitmap.width}x${bitmap.height}")
            return
        }
        val cropped = try {
            Bitmap.createBitmap(bitmap, left, top, cropW, cropH)
        } catch (e: Exception) {
            Log.e("CameraXApp", "Create cropped bitmap failed", e)
            Toast.makeText(this, "裁剪失败", Toast.LENGTH_SHORT).show()
            return
        }
        // Save cropped bitmap
        val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$name.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
            }
        }
        val resolver = contentResolver
        var savedUri: Uri? = null
        try {
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri == null) {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
                return
            }
            savedUri = uri
            resolver.openOutputStream(uri)?.use { os: OutputStream ->
                val ok = cropped.compress(Bitmap.CompressFormat.JPEG, 90, os)
                if (!ok) throw RuntimeException("Compress failed")
            }
            // Compute size via OpenableColumns.SIZE; fallback by reading stream length
            var size: Long? = resolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { c ->
                val idx = c.getColumnIndex(OpenableColumns.SIZE)
                if (idx >= 0 && c.moveToFirst()) c.getLong(idx) else null
            }
            if (size == null || size == 0L) {
                resolver.openInputStream(uri)?.use { ins ->
                    var total = 0L
                    val buf = ByteArray(8 * 1024)
                    while (true) {
                        val n = ins.read(buf)
                        if (n <= 0) break
                        total += n
                    }
                    size = total
                }
            }
            // Build Base64 from content resolver stream (do not use uri.path)
            val base64: String = resolver.openInputStream(uri)?.use { ins ->
                val bytes = ins.readBytes()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            } ?: ""

            Toast.makeText(this, "已保存裁剪图片", Toast.LENGTH_SHORT).show()
            val aa = uri.toString()
            val intent = Intent()
            intent.putExtra("data",uri.toString())
            setResult(RESULT_BASE_64_OK,intent)
        } catch (e: Exception) {
            Log.e("CameraXApp", "保存裁剪图片失败", e)
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
            savedUri?.let { resolver.delete(it, null, null) }
        } finally {
            if (bitmap != cropped) bitmap.recycle()
            // cropped 可根据需求显示再回收
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(this, "需要相机权限才能拍照", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}