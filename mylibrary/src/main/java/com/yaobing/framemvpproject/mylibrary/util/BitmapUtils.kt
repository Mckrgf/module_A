package com.yaobing.framemvpproject.mylibrary.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.sqrt

object BitmapUtils {
    fun cropTo5x4WideBitmap(original: Bitmap?): Bitmap? {
        if (original == null) return null

        val originalWidth = original.width
        val originalHeight = original.height

        // 目标宽高比：5:4（宽/高 = 5/4）
        val targetRatio = 5f / 4f

        // 计算裁剪后的目标宽高
        val (targetWidth, targetHeight) = if (originalWidth.toFloat() / originalHeight >= targetRatio) {
            // 原图偏宽：以原图高度为基准，计算符合比例的宽度（不超过原图宽度）
            val calculatedWidth = (originalHeight * targetRatio).toInt()
            calculatedWidth to originalHeight
        } else {
            // 原图偏长：以原图宽度为基准，计算符合比例的高度（不超过原图高度）
            val calculatedHeight = (originalWidth / targetRatio).toInt()
            originalWidth to calculatedHeight
        }

        // 计算裁剪区域的起点（居中裁剪）
        val startX = (originalWidth - targetWidth) / 2
        val startY = (originalHeight - targetHeight) / 2

        // 确保裁剪区域在原图范围内（避免边界问题）
        val safeStartX = startX.coerceAtLeast(0)
        val safeStartY = startY.coerceAtLeast(0)
        val safeWidth = targetWidth.coerceAtMost(originalWidth - safeStartX)
        val safeHeight = targetHeight.coerceAtMost(originalHeight - safeStartY)

        // 执行裁剪
        return try {
            Bitmap.createBitmap(original, safeStartX, safeStartY, safeWidth, safeHeight)
        } catch (e: Exception) {
            e.printStackTrace()
            null // 裁剪失败时返回null
        }
    }

    fun cropTo5x4WideBitmapV2(original: Bitmap?, recycleOriginal: Boolean = false): Bitmap? {
        if (original == null || original.isRecycled) return null
        if (original.width == 0 || original.height == 0) return null

        return try {
            val result = doCrop(original)
            if (recycleOriginal && result != null && result != original) {
                original.recycle()
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun doCrop(original: Bitmap): Bitmap? {
        val (targetWidth, targetHeight) = calculateTargetSize(original)
        val (safeStartX, safeStartY) = calculateCropStartPoint(original, targetWidth, targetHeight)
        val (safeWidth, safeHeight) = calculateSafeDimensions(original, safeStartX, safeStartY, targetWidth, targetHeight)

        return Bitmap.createBitmap(original, safeStartX, safeStartY, safeWidth, safeHeight)
    }

    private fun calculateTargetSize(original: Bitmap): Pair<Int, Int> {
        val targetRatio = 5f / 4f
        return if (original.width.toFloat() / original.height >= targetRatio) {
            (original.height * targetRatio).toInt() to original.height
        } else {
            original.width to (original.width / targetRatio).toInt()
        }
    }

    private fun calculateCropStartPoint(original: Bitmap, targetWidth: Int, targetHeight: Int): Pair<Int, Int> {
        return ((original.width - targetWidth) / 2).coerceAtLeast(0) to
                ((original.height - targetHeight) / 2).coerceAtLeast(0)
    }

    private fun calculateSafeDimensions(
        original: Bitmap,
        startX: Int,
        startY: Int,
        targetWidth: Int,
        targetHeight: Int
    ): Pair<Int, Int> {
        return targetWidth.coerceAtMost(original.width - startX) to
                targetHeight.coerceAtMost(original.height - startY)
    }


    /**
     * 把传进来的bitmap压缩成png格式的bitmap
     */
    fun getThumbBitmap(resource: Bitmap): Bitmap {
        val bitmap = resource.copy(Bitmap.Config.ARGB_8888, true)
        val thumbByteData: ByteArray = bitmapToByteArray(bitmap)

        //png格式的byteArray的大小
        val thumbByteSize = thumbByteData.size * 1.0f / 1024

        //内存占用大小
        val bitmapSize = bitmap.allocationByteCount * 1.0f / 1024
        Log.i("Share123--->", "thumbByteSize:" + thumbByteSize + "k")
        Log.i("Share123--->", "bitmapSize:" + bitmapSize + "k")
        return if (thumbByteSize > 128) {
            val multiple = sqrt((128 / thumbByteSize).toDouble())
            Log.i("Share123--->", "multiple.size:" + thumbByteSize + "k")
            val zoom = zoomImage(
                bitmap,
                multiple
            )
            getThumbBitmap(zoom)
        } else {
            bitmap
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        stream.close()
        return byteArray
    }

    /**
     * 尺寸压缩
     *
     * @param bgimage
     * @return
     */
    fun zoomImage(bgimage: Bitmap, resource: Double): Bitmap {
        // 获取这个图片的宽和高
        val width = bgimage.width.toFloat()
        val height = bgimage.height.toFloat()
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        var ratio = resource
        if (resource > 0.9) {
            ratio = 0.9
        }
        // 缩放图片动作
        matrix.postScale(ratio.toFloat(), ratio.toFloat())
        return Bitmap.createBitmap(bgimage, 0, 0, width.toInt(), height.toInt(), matrix, true)
    }

    fun imageToBase64(path: String?, base64Flag: Int): String? {
        if (TextUtils.isEmpty(path)) {
            return null
        } else {
            var `is`: InputStream? = null
            var data: ByteArray? = null
            var result: String? = null

            try {
                `is` = Files.newInputStream(Paths.get(path))
                data = ByteArray(`is`.available())
                `is`.read(data)
                result = Base64.encodeToString(data, base64Flag)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                if (null != `is`) {
                    try {
                        `is`.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            return result
        }
    }

    /**
     * New: Read content from a content Uri and return Base64 string.
     * Uses stream-based encoding to avoid loading the whole file into memory.
     * @param context required to open the Uri via ContentResolver
     * @param uri content Uri (content://...) or file Uri
     * @param base64Flag flags from android.util.Base64 (e.g. NO_WRAP)
     */
    fun imageToBase64ByUri(context: Context, uri: Uri, base64Flag: Int = Base64.NO_WRAP): String? {
        try {
            // Use ContentResolver for content:// and file:// alike
            val input: InputStream? = try {
                context.contentResolver.openInputStream(uri)
            } catch (e: Exception) {
                Log.w("BitmapUtils", "openInputStream failed for uri=$uri, falling back to file path: ${e.message}")
                null
            }

            if (input == null) {
                Log.w("BitmapUtils", "Cannot open input stream for uri=$uri")
                return null
            }

            input.use { ins ->
                val baos = ByteArrayOutputStream()
                // Base64OutputStream will write base64 bytes into baos
                Base64OutputStream(baos, base64Flag).use { b64Out ->
                    val buffer = ByteArray(8 * 1024)
                    while (true) {
                        val read = ins.read(buffer)
                        if (read <= 0) break
                        b64Out.write(buffer, 0, read)
                    }
                    b64Out.flush()
                }
                // Base64OutputStream wrote US-ASCII bytes; convert to String
                return try {
                    baos.toString(Charsets.US_ASCII.name())
                } catch (e: Exception) {
                    // Fallback
                    String(baos.toByteArray(), Charsets.US_ASCII)
                }
            }
        } catch (e: Exception) {
            Log.e("BitmapUtils", "imageToBase64ByUri failed for uri=$uri", e)
            return null
        }
    }

    // Convenience overload: accept Uri as String by parsing it
    fun imageToBase64ByUri(context: Context, uriString: String?, base64Flag: Int = Base64.NO_WRAP): String? {
        if (uriString == null) return null
        return try {
            val uri = Uri.parse(uriString)
            imageToBase64ByUri(context, uri, base64Flag)
        } catch (e: Exception) {
            Log.w("BitmapUtils", "invalid uri string: $uriString", e)
            null
        }
    }
}