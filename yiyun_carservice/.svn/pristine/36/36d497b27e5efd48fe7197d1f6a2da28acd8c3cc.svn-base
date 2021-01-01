package com.yiyun.yiyuncarservice.utils;

import com.yiyun.yiyuncarservice.constant.DataConstants;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxx
 * @version 1.0
 * @title: WxUtils
 * @projectName 01.后台代码
 * @description: TODO
 * @date 2020/4/2 19:22
 */
@Component
public class WxUtils {
  /**
   * * 微信 数据解密<br>
   * * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br>
   * * 对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br>
   * * 对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br>
   * * 对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br>
   * * * @param encrypted 目标密文 * @param session_key 会话ID * @param iv 加密算法的初始向量
   */
  public static String wxDecrypt(String encrypted, String session_key, String iv) {
    String result = null;
    byte[] encrypted64 = Base64.decodeBase64(encrypted);
    byte[] key64 = Base64.decodeBase64(session_key);
    byte[] iv64 = Base64.decodeBase64(iv);
    try {
      init();
      result = new String(decrypt(encrypted64, key64, generateIV(iv64)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /** * 初始化密钥 */
  public static void init() throws Exception {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    KeyGenerator.getInstance(DataConstants.AES).init(128);
  }

  /** * 生成iv */
  public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
    // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
    // Arrays.fill(iv, (byte) 0x00);
    AlgorithmParameters params = AlgorithmParameters.getInstance(DataConstants.AES);
    params.init(new IvParameterSpec(iv));
    return params;
  }

  /** * 生成解密 */
  public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
      throws Exception {
    Key key = new SecretKeySpec(keyBytes, DataConstants.AES);
    Cipher cipher = Cipher.getInstance(DataConstants.AES_CBC_PADDING, "BC");
    // 设置为解密模式
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    return cipher.doFinal(encryptedData);
  }

  /**
   * xml转换成map
   *
   * @param xml
   * @return
   */
  public static Map<String, String> readStringXmlOut(String xml) {
    Map<String, String> map = new HashMap<String, String>();
    Document doc = null;
    try {
      doc = (Document) DocumentHelper.parseText(xml); // 将字符串转为XML
      Element rootElt = doc.getRootElement(); // 获取根节点
      List<Element> list = rootElt.elements(); // 获取根节点下所有节点
      for (Element element : list) { // 遍历节点
        map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
      }
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return map;
  }
}
