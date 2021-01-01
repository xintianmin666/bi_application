package com.yiyun.yiyuncarservice.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author hxx
 * @version 1.0
 * @title: RefundBase64Util
 * @projectName ytcxxcx
 * @description: TODO
 * @date 2020/5/31 20:01
 */
public class RefundBase64Util {
  /**
   * 进行Base64解密
   *
   * @param binaryData
   * @return
   * @throws Exception
   */
  public static byte[] decodeBase64(String binaryData) throws Exception {
    byte[] result = Base64.decodeBase64(binaryData.getBytes());
    return result;
  }
  /**
   * 进行Base64加密
   *
   * @param binaryData
   * @return
   * @throws Exception
   */
  public static byte[] encodeBase64(byte[] binaryData) throws Exception {
    byte[] result = Base64.encodeBase64(binaryData);
    return result;
  }
}
