package com.yaobing.framemvpproject.mylibrary.util

import android.content.Context
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import com.tencent.mars.xlog.Xlog
import com.yaobing.framemvpproject.mylibrary.BuildConfig

object XlogUtil {
    fun initXlog(context: Context) {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")
        val SDCARD: String =
            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).absolutePath
        val logPath = "$SDCARD/marssample/log"
        val cachePath: String = context.filesDir.path + "/xlog"
//        val xlog = Xlog()

        val config: Xlog.XLogConfig = Xlog.XLogConfig()
        //密钥
        config.pubkey = "455d048c62c53c54f4a3d0e26e95bdac9f69f29343156b2a7a7dea7ae2be928abae2560337de4d637f85a3d458c39fe7cdf52232d07216d38e7a6ed27070f70e"
        config.compressmode = Xlog.ZLIB_MODE
//        config.compresslevel = 9
        val xlog = Xlog()
        xlog.newXlogInstance(config)

        com.tencent.mars.xlog.Log.setLogImp(xlog)
        if (BuildConfig.DEBUG) {
            com.tencent.mars.xlog.Log.setConsoleLogOpen(true)
            com.tencent.mars.xlog.Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "hah", 0)
        } else {
            com.tencent.mars.xlog.Log.setConsoleLogOpen(false)
            com.tencent.mars.xlog.Log.appenderOpen(
                Xlog.LEVEL_DEBUG,
                Xlog.AppednerModeAsync,
                "",
                logPath,
                "hah",
                0
            )
        }
    }
}