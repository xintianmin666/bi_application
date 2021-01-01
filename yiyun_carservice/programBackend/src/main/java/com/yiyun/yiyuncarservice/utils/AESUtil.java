package com.yiyun.yiyuncarservice.utils;

import com.yiyun.yiyuncarservice.constant.DataConstants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

/**
 * @author hxx
 * @version 1.0
 * @title: AESUtil
 * @projectName 01.后台代码
 * @description: TODO
 * @date 2020/3/24 20:39
 */
@Component
public class AESUtil {
  /** 密钥算法 */
  private static final String ALGORITHM = "AES";
  /** 加解密算法/工作模式/填充方式 */
  private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
  /** 生成key */
  private static SecretKeySpec key;

  static {
    try {
      key =
          new SecretKeySpec(
              MyMD5Util.MD5(DataConstants.SECRET).toLowerCase().getBytes(), ALGORITHM);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
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
    //    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    // 初始化
    cipher.init(Cipher.ENCRYPT_MODE, key);
    //    return Base64Util.encode(cipher.doFinal(data.getBytes()));
    return CyptoUtil.base64_encode_8859(new String(cipher.doFinal(data.getBytes()), "ISO-8859-1"));
  }

  /**
   * AES解密
   *
   * @param base64Data
   * @return
   * @throws Exception
   */
  public static String decryptData(String base64Data) throws Exception {
    Security.addProvider(new BouncyCastleProvider());
    //    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    cipher.init(Cipher.DECRYPT_MODE, key);
    return new String(
        cipher.doFinal(CyptoUtil.base64_decode_8859(base64Data).getBytes("ISO-8859-1")), "utf-8");
    //    return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    //    Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
    //    //    SecretKeySpec key =
    //    //        new SecretKeySpec(MyMD5Util.MD5(DataConstants.SECRET).toLowerCase().getBytes(),
    //    // ALGORITHM);
    //    cipher.init(Cipher.DECRYPT_MODE, key);
    //    byte[] decode = Base64Util.deCode(base64Data);
    //    byte[] doFinal = cipher.doFinal(decode);
    //    return new String(doFinal, "utf-8");
  }
}
