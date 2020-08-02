package com.example.demo.security;

import javax.sound.sampled.AudioFormat;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class URLEncryption {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        System.out.printf(md5("dfadsfsa"));
    }
    public static String md5(String str) throws NoSuchAlgorithmException {
        //指定加密算法为MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //传入需要加密的字节数组
        md.update(str.getBytes());
        //计算信息摘要digest()方法,返回值为字节数组
        byte[] b = md.digest();

        int x;//定义整型
        //声明StringBuffer对象
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            x = b[offset];//将首个元素赋值给i
            if (x < 0)
                x += 256;
            if (x < 16)
                buf.append("0");//前面补0
            buf.append(Integer.toHexString(x));//转换成16进制编码
        }
        String md5UPW = buf.toString();//转换成字符串

        return md5UPW;
    }
}
