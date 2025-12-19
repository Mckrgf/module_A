package com.yaobing.framemvpproject.mylibrary

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object AESUtils {
    private const val AES_KEY = "asdasdasdasdasda" // 16/24/32字节（AES-128/192/256）
    private val secretKey = SecretKeySpec(AES_KEY.toByteArray(), "AES")

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance("AES/GCM/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    fun decrypt(encrypted: String): String {
        try {
            val cipher = Cipher.getInstance("AES/GCM/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val decodedBytes = Base64.decode(encrypted, Base64.NO_WRAP)
            return String(cipher.doFinal(decodedBytes))
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("解密失败: ${e.message}")
        }
    }

    fun testUnit(var1: String, var2:Int, invokeFun: (String) -> Unit,var3: String) {
        var result = var1 + var2 + var3
        invokeFun(result)
    }
}