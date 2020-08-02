package com.example.demo.security;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import org.h2.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class QRCodeUtil {

    private static Logger log =  LoggerFactory.getLogger(QRCodeUtil.class);

    /**
     * 生成二维码
     * @param text 内容，可以是链接或者文本
     * @param path 生成的二维码位置
     */
    public static void encodeQRCode(String text, String path) {
        encodeQRCode(text, path, null, null, null);
    }

    /**
     * 生成二维码
     * @param text 内容，可以是链接或者文本
     * @param path 生成的二维码位置
     * @param width 宽度，默认300
     * @param height 高度，默认300
     * @param format 生成的二维码格式，默认png
     */
    public static void encodeQRCode(String text, String path, Integer width, Integer height, String format) {
        try {

            // 得到文件对象
            File file = new File(path);
            // 判断目标文件所在的目录是否存在
            if(!file.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建父目录
                log.info("目标文件所在目录不存在，准备创建它！");
                if(!file.getParentFile().mkdirs()) {
                    log.info("创建目标文件所在目录失败！");
                    return;
                }
            }

            // 宽
            if (width == null) {
                width = 300;
            }
            // 高
            if (height == null) {
                height = 300;
            }
            // 图片格式
            if (format == null) {
                format = "png";
            }

            // 设置字符集编码

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 生成二维码矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            // 二维码路径
            Path outputPath = Paths.get(path);
            // 写入文件
            MatrixToImageWriter.writeToPath(bitMatrix, format, outputPath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 对二维码图片进行解码
     * @param filepath 二维码路径
     * @return 解码后对内容
     */
    public static void decodeQRCode(String filepath) {

        MultiFormatReader multiFormatReader = new MultiFormatReader();
        File file = new File(filepath);

        // 图片缓冲
        BufferedImage image = null;

        // 二进制比特图
        BinaryBitmap binaryBitmap = null;

        // 二维码结果
        Result result = null;

        try {
            image = ImageIO.read(file);
            binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            result = multiFormatReader.decode(binaryBitmap);
        } catch (IOException | NotFoundException e1) {
            e1.printStackTrace();
        }

        System.out.println("读取二维码： " + result.toString());
        System.out.println("二维码格式： " + result.getBarcodeFormat());
        System.out.println("二维码内容： " + result.getText());

    }
    public static void main(String[] args) {

        // 生成路径
        String filePath = "D:\\dowload\\first.png";

        // 生成二维码
        encodeQRCode("https://juejin.im/post/6844903703053336583", filePath);

        // 解码二维码
        decodeQRCode(filePath);
    }
}
