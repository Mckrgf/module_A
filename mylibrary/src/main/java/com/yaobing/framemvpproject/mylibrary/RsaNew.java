package com.yaobing.framemvpproject.mylibrary;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RsaNew {

    /**
     * 加密算法
     */
    private static final String ALGORITHM = "RSA";
    /**
     * RSA 加密模式与填充方式
     * "RSA/ECB/PKCS1Padding" 是常用的组合。
     * ECB: Electronic Codebook mode（电子密码本模式）
     * PKCS1Padding: RSA 数据填充标准之一，确保加密数据的安全性和兼容性。
     */
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    /**
     * 密钥长度，一般为1024、2048位。
     * 2048位提供更高的安全性，但加密解密速度会慢一些。
     * 注意：RSA 加密数据长度有限制，明文长度不能超过 (密钥长度/8) - 11 字节（对于PKCS1Padding）。
     * 1024位密钥大约能加密 117 字节。
     * 2048位密钥大约能加密 245 字节。
     */
    private static final int KEY_SIZE = 2048; // 推荐使用 2048 位

    /**
     * 生成 RSA 密钥对
     *
     * @return 包含公钥和私钥的 KeyPair 对象
     * @throws Exception 如果生成过程中发生错误
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 使用公钥加密数据
     *
     * @param publicKey 公钥
     * @param data      待加密的原始数据
     * @return Base64 编码的加密后数据
     * @throws Exception 如果加密过程中发生错误
     */
    public static String encryptByPublicKey(PublicKey publicKey, String data) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        // 对加密后的字节数组进行 Base64 编码，方便传输和存储
        // 这里使用标准 Base64 编码
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 使用私钥解密数据
     *
     * @param privateKey 私钥
     * @param encryptedData Base64 编码的加密数据
     * @return 解密后的原始数据
     * @throws Exception 如果解密过程中发生错误
     */
    public static String decryptByPrivateKey(PrivateKey privateKey, String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 先对 Base64 编码的字符串进行解码
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    /**
     * 将公钥转换为 Base64 字符串
     *
     * @param publicKey 公钥
     * @return Base64 编码的公钥字符串
     */
    public static String getPublicKeyAsString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * 将私钥转换为 Base64 字符串
     *
     * @param privateKey 私钥
     * @return Base64 编码的私钥字符串
     */
    public static String getPrivateKeyAsString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 从 Base64 字符串恢复公钥
     *
     * @param publicKeyString Base64 编码的公钥字符串
     * @return PublicKey 对象
     * @throws Exception 如果恢复过程中发生错误
     */
    public static PublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从 Base64 字符串恢复私钥
     *
     * @param privateKeyString Base64 编码的私钥字符串
     * @return PrivateKey 对象
     * @throws Exception 如果恢复过程中发生错误
     */
    public static PrivateKey getPrivateKeyFromString(String privateKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) {
        try {
            // 1. 生成密钥对
            KeyPair keyPair = generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("--- 密钥生成成功 ---");
            // 将密钥转换为字符串以便存储或传输
            String publicKeyString = getPublicKeyAsString(publicKey);
            String privateKeyString = getPrivateKeyAsString(privateKey);
            System.out.println("公钥 (Base64):\n" + publicKeyString);
            System.out.println("私钥 (Base64):\n" + privateKeyString);

            // 模拟将密钥字符串传输给其他人或从存储中读取
            PublicKey loadedPublicKey = getPublicKeyFromString(publicKeyString);
            PrivateKey loadedPrivateKey = getPrivateKeyFromString(privateKeyString);
            System.out.println("\n--- 密钥从字符串恢复成功 ---");

            // 2. 原始数据
            String originalData = "这是一段需要使用 RSA 加密的秘密信息，测试RSA加密解密。";
            // 如果数据太长，可能会导致加密失败，因此这里要控制长度。
            // 例如，2048 位 RSA 使用 PKCS1Padding 最多加密 245 字节。
            // "这是一段需要使用 RSA 加密的秘密信息，测试RSA加密解密。" 转换为UTF-8字节后大约47字节，是安全的。

            System.out.println("\n--- 开始加密解密测试 ---");
            System.out.println("原始数据: " + originalData);

            // 3. 使用公钥加密
            String encryptedData = encryptByPublicKey(loadedPublicKey, originalData);
            System.out.println("加密后数据 (Base64):\n" + encryptedData);

            // 4. 使用私钥解密
            String decryptedData = decryptByPrivateKey(loadedPrivateKey, encryptedData);
            System.out.println("解密后数据: " + decryptedData);

            // 验证解密是否成功
            if (originalData.equals(decryptedData)) {
                System.out.println("\nRSA 加密解密成功！");
            } else {
                System.out.println("\nRSA 加密解密失败！");
            }

            // --- 尝试超出长度限制的明文 (可能导致异常或截断，具体行为取决于JRE实现) ---
            // 注意：下面的代码可能会报错，因为它超出了RSA单次加密的长度限制
            // String longData = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+{}[]|\\;:'\",.<>/?`~";
            // StringBuilder sb = new StringBuilder();
            // for(int i=0; i<3; i++) { // 构造一个远超245字节的字符串
            //     sb.append(longData);
            // }
            // String veryLongData = sb.toString();
            // System.out.println("\n--- 尝试加密超长数据 ---");
            // System.out.println("超长数据长度: " + veryLongData.getBytes("UTF-8").length + " bytes");
            // try {
            //     String encryptedLongData = encryptByPublicKey(loadedPublicKey, veryLongData);
            //     System.out.println("加密超长数据成功 (这通常是分块加密的结果): " + encryptedLongData.length());
            // } catch (Exception e) {
            //     System.err.println("加密超长数据失败，错误: " + e.getMessage());
            //     System.err.println("RSA 直接加密大数据会报错，需要分段加密或使用混合加密。");
            // }


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("发生错误: " + e.getMessage());
        }
    }
}