package com.yaobing.framemvpproject.mylibrary.util

import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import com.yaobing.framemvpproject.mylibrary.BuildConfig

object XlogUtil {
    var logPath = ""
    var cachePath = ""
    fun commonXLogInit(context: Context) : Xlog.XLogConfig {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")
        val SDCARD: String =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).absolutePath
        logPath = "$SDCARD/marssample/log"
        cachePath = context.filesDir.path + "/xlog"
        val config: Xlog.XLogConfig = Xlog.XLogConfig()
        //密钥
        config.pubkey = "455d048c62c53c54f4a3d0e26e95bdac9f69f29343156b2a7a7dea7ae2be928abae2560337de4d637f85a3d458c39fe7cdf52232d07216d38e7a6ed27070f70e"
        return config
    }

    /**
     * 新方法，解压特别慢
     */
    fun initXlogA(context: Context) {
        val config = commonXLogInit(context)
        val xlog = Xlog()
        xlog.newXlogInstance(config)
        Log.setLogImp(xlog)

        Log.setConsoleLogOpen(true)

        //解压慢的方法（目前在用，慢是因为加了pubkey）
        Xlog.open(true, Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "leap_log_have_key", config.pubkey)

        //解压快的方法
//        Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "leap_log_no_key", 10)

        xlog.setMaxFileSize(0, 1024L * 1024 * 100)
        xlog.setMaxAliveTime(0, 3600 * 24 * 100)
    }


    /**
     * 老方法，解压很快
     */
    fun initXlog(context: Context) {
        val config = commonXLogInit(context)
        config.compressmode = Xlog.ZSTD_MODE
        config.compressmode = 9
        val xlog = Xlog()
        xlog.newXlogInstance(config)
        Log.setLogImp(xlog)
        if (BuildConfig.DEBUG) {

            Log.setConsoleLogOpen(true)//用这个open的话，设置的compressMode会不生效
            Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "hah", 0)
        } else {
            Log.setConsoleLogOpen(false)
            Log.appenderOpen(
                Xlog.LEVEL_DEBUG,
                Xlog.AppednerModeAsync,
                "",
                logPath,
                "hah",
                0
            )
        }
    }

    /**
     * 随机生成n个指定长度的字符串
     * @param n 生成的字符串数量
     * @param length 每个字符串的长度，默认8
     * @return List<String>
     */
    fun generateRandomStrings(n: Int, length: Int = 8): List<String> {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = java.util.Random()
        return List(n) {
            (1..length)
                .map { chars[random.nextInt(chars.length)] }
                .joinToString("")
        }
    }
}
