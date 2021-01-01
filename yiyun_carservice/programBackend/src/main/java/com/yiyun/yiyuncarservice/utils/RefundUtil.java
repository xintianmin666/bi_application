package com.yiyun.yiyuncarservice.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author hxx
 * @version 1.0
 * @title: RefundUtil
 * @projectName ytcxxcx
 * @description: TODO
 * @date 2020/5/31 16:41
 */
public class RefundUtil {
  /** 密钥算法 */
  private static final String ALGORITHM = "AES";
  /** 加解密算法/工作模式/填充方式 */
  private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";
  /** AES加密 */
  public static String encryptData(String data, String password) throws Exception {
    // 创建密码器
    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    SecretKeySpec key = new SecretKeySpec(MD5(password).toLowerCase().getBytes(), ALGORITHM);
    // 初始化
    cipher.init(Cipher.ENCRYPT_MODE, key);
    return encode(cipher.doFinal(data.getBytes()));
  }
  /** AES解密 */
  public static String decryptData(String base64Data, String password) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    SecretKeySpec key = new SecretKeySpec(MD5(password).toLowerCase().getBytes(), ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decode = decode(base64Data);
    byte[] doFinal = cipher.doFinal(decode);
    return new String(doFinal, "utf-8");
  }

  /** 解码 */
  public static byte[] decode(String encodedText) {
    final Base64.Decoder decoder = Base64.getDecoder();
    return decoder.decode(encodedText);
  }
  /** 编码 */
  public static String encode(byte[] data) {
    final Base64.Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(data);
  }

  public static String MD5(String input) {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      return "check jdk";
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
    char[] charArray = input.toCharArray();
    byte[] byteArray = new byte[charArray.length];
    for (int i = 0; i < charArray.length; i++) {
      byteArray[i] = (byte) charArray[i];
    }
    byte[] md5Bytes = md5.digest(byteArray);
    StringBuffer hexValue = new StringBuffer();
    for (int i = 0; i < md5Bytes.length; i++) {
      int val = ((int) md5Bytes[i]) & 0xff;
      if (val < 16) {
        hexValue.append("0");
      }
      hexValue.append(Integer.toHexString(val));
    }
    return hexValue.toString();
  }
}
