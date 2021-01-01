package com.yiyun.yiyuncarservice.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author hxx
 * @version 1.0
 * @title: CyptoUtil
 * @projectName ytcxxcx
 * @description: TODO
 * @date 2020/5/31 17:41
 */
@Component
public class CyptoUtil {

  public static String base64_decode_8859(final String source) {
    String result = "";

    final Base64.Decoder decoder = Base64.getDecoder();
    try {
      result = new String(decoder.decode(source), "ISO-8859-1");
      // 此处的字符集是ISO-8859-1
    } catch (final UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return result;
  }

  public static String base64_encode_8859(final String source) {
    String result = "";

    final Base64.Encoder encoder = Base64.getEncoder();
    byte[] textByte = null;
    try {
      textByte = source.getBytes("ISO-8859-1");
      // 注意此处的编码是ISO-8859-1
    } catch (final UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    result = encoder.encodeToString(textByte);
    return result;
  }
}
