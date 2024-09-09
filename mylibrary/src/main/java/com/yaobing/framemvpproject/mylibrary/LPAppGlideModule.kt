package com.yaobing.framemvpproject.mylibrary

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.yaobing.framemvpproject.mylibrary.glide.SSHUtil
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@GlideModule
class LPAppGlideModule  : AppGlideModule(){


    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {


//        val builder = OkHttpClient.Builder()
//        builder.sslSocketFactory(sSLSocketFactory, trustManager)
//        val okHttpClient = builder.build()
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))

        val okhttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true) // 设置出现错误进行重新连接。
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout((60 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .sslSocketFactory(SSHUtil.getSslSocketFactory().sSLSocketFactory)
            .hostnameVerifier(SSHUtil.getHostnameVerifier())
            .build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, com.yaobing.framemvpproject.mylibrary.glide.OkHttpUrlLoader.Factory(okhttpClient))
    }

    /** 获取一个SSLSocketFactory */
    val sSLSocketFactory: SSLSocketFactory
        get() = try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(trustManager), SecureRandom())
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    /** 获取一个忽略证书的X509TrustManager */
    val trustManager: X509TrustManager
        get() = object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out java.security.cert.X509Certificate>?, authType: String?) { }
            override fun checkServerTrusted(p0: Array<out java.security.cert.X509Certificate>?, authType: String?) { }
            override fun getAcceptedIssuers(): Array<out java.security.cert.X509Certificate>? { return arrayOf() }
        }
}