package com.kindergarten.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @Date Created on 2017/1/6.
 * @Author SongJiuHua.
 * @description
 */
public class MD5Util {

    private final static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    private final static String PRIVATE_KEY = "918F5A480984156E5EA368A7F7ED93B5";

    /**
     * 加密算法
     * @param target
     * @param algorithmType 加密类型
     * @return
     */
    public static String getEncryptedString(String target, String algorithmType){
        return getEncryptedString(target, algorithmType, PRIVATE_KEY);
    }

    /**
     * 加密算法
     * @param target
     * @return
     */
    public static String getEncryptedString(String target){
        return getEncryptedString(target, "MD5", "");
    }

    /**
     * 加密
     * @param target
     * @param algorithmType 加密类型 MD5, SHA-1, SHA-256.
     * @param privateKey 秘钥
     * @return
     */
    public static String getEncryptedString(String target, String algorithmType, String privateKey){
        target = target + privateKey;
        String md5String = "";
        try {
            //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithmType);
            //2 将消息变成byte数组
            byte[] targetBytes = target.getBytes();
            //3 计算后获得字节数组,这就是那128位了
            byte[] buff = messageDigest.digest(targetBytes);
            md5String = bytesToHex(buff);
        } catch (Exception e) {
            logger.error("加密异常", e);
        }
        return md5String;
    }

    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes){
        StringBuilder md5Str = new StringBuilder();
        int digital;
        for (int i = 0; i < bytes.length; i++){
            digital = bytes[i];
            if(digital < 0){
                digital += 256;
            }
            if(digital < 16){
                md5Str.append("0");
            }
            md5Str.append(Integer.toHexString(digital));
        }
        return md5Str.toString().toUpperCase();
    }


}
