package com.yiyun.yiyuncarservice.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author hxx
 * @version 1.0
 * @title: MD5test
 * @projectName yiyun
 * @description: TODO
 * @date 2020/2/20 17:40
 */
public class MD5test {

  public static String MD5(String inStr) throws UnsupportedEncodingException {
    String result = "";
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(inStr.getBytes("utf-8"));
      byte b[] = md.digest();
      int i;
      StringBuffer buf = new StringBuffer("");
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset];
        if (i < 0) i += 256;
        if (i < 16) buf.append("0");
        buf.append(Integer.toHexString(i));
      }
      result = buf.toString();
      //             buf.toString();
    } catch (NoSuchAlgorithmException e) {
      System.out.println(e);
    }
    return result.toUpperCase();
  }

  public static void main(String args[]) {
    //    String instr = "AHYTXCX340200WHDQFZ202004301004091.0.0";
    String timestamp = System.currentTimeMillis() + "";
    //    String instr = DataConstants.gtSignature + timestamp;
    System.out.println(timestamp);
    //    String instr = "3210001213211025713220421oL762R6hDpTfXzQk";

    String instr =
        "DistributorCode=WuHuYunTai&WorkerCode=001&TicketCode=20120811375631136201&SecretKey=5B06361EF6783790F62CD66870A89E0D";
    //
    // "DistributorCode=WuHuYunTai&WorkerCode=001&StartProvinceCode=3400000000&SecretKey=5B06361EF6783790F62CD66870A89E0D";
    //    String instr =
    //
    //
    // "DistributorCode=WuHuYunTai&WorkerCode=001&DepartDate=200803&StartRegionCode=3402250000&StopRegionCode=077&SecretKey=5B06361EF6783790F62CD66870A89E0D";
    //    String instr =
    //
    //
    // "DistributorCode=WuHuYunTai&WorkerCode=001&StartProvinceCode=3400000000&SecretKey=5B06361EF6783790F62CD66870A89E0D";
    try {
      System.out.println(MD5(instr));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
}
