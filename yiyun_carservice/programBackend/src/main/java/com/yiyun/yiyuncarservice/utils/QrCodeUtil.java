package com.yiyun.yiyuncarservice.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @ClassName: QrCodeUtil
 * @Description: 本地生活二维码生成及解析公共方法
 * @author: hxx
 * @date: 2018年7月10日 下午2:33:05
 */
@Component
@SuppressWarnings("restriction")
public class QrCodeUtil {

    /**
     * 生成二维码
     * @param content 二维码的内容
     * @return BitMatrix对象
     */
    public static String createCode(String content) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 600, 600);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        Base64.Encoder encoder = Base64.getEncoder();

        String text = encoder.encodeToString(outputStream.toByteArray());

        return "data:image/png;base64," + text;
    }


}
