package com.yiyun.yiyuncarservice.utils;

import com.yiyun.yiyuncarservice.constant.DataConstants;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author hxx
 * @version 1.0
 * @title: RefundAESutil
 * @projectName ytcxxcx
 * @description: TODO
 * @date 2020/5/31 20:10
 */
@Component
public class RefundAESutil {
  /** 密钥算法 */
  private static final String ALGORITHM = "AES";
  /** 加解密算法/工作模式/填充方式 */
  private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

  static {
    // Security.addProvider(new BouncyCastleProvider());
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
  }

  /**
   * AES加密
   *
   * @param data
   * @return
   * @throws Exception
   */
  public static String encryptData(String data) throws Exception {
    // 创建密码器
    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    SecretKeySpec secretKeySpec =
        new SecretKeySpec(Md5Util.MD532(DataConstants.PaySECRET).getBytes(), ALGORITHM);
    // 初始化
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    return new String(RefundBase64Util.encodeBase64(cipher.doFinal(data.getBytes())));
  }

  /**
   * AES解密
   *
   * @param base64Data
   * @return
   * @throws Exception
   */
  public static String decryptData(String base64Data) throws Exception {
    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    SecretKeySpec secretKeySpec =
        new SecretKeySpec(Md5Util.MD532(DataConstants.PaySECRET).getBytes(), ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    return new String(cipher.doFinal(RefundBase64Util.decodeBase64(base64Data)));
  }
}
