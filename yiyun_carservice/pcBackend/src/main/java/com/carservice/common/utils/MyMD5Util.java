package com.carservice.common.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author hxx
 * @version 1.0
 * @title: MyMD5Util
 * @projectName rentalcars
 * @description: TODO
 * @date 2020/8/27 9:21
 */
@Component
public class MyMD5Util {
    private static final Integer SALT_LENGTH = 12;
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

    public static String md5(String input) {
        //    char hexDigits[] = {
        //      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        //    };
        //    try {
        //      byte[] btInput = s.getBytes();
        //      // 获得MD5摘要算法的 MessageDigest 对象
        //      MessageDigest mdInst = MessageDigest.getInstance("MD5");
        //      // 使用指定的字节更新摘要
        //      mdInst.update(btInput);
        //      // 获得密文
        //      byte[] md = mdInst.digest();
        //      // 把密文转换成十六进制的字符串形式
        //      int j = md.length;
        //      char str[] = new char[j * 2];
        //      int k = 0;
        //      for (int i = 0; i < j; i++) {
        //        byte byte0 = md[i];
        //        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        //        str[k++] = hexDigits[byte0 & 0xf];
        //      }
        //      return new String(str);
        //    } catch (Exception e) {
        //      e.printStackTrace();
        //      return null;
        //    }
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

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    };

    /**

     获得加密后的16进制形式口令
     @param password
     @return
     @throws NoSuchAlgorithmException
     @throws UnsupportedEncodingException
     */
    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
//声明加密后的口令数组变量
        byte[] pwd = null;
//随机数生成器
        SecureRandom random = new SecureRandom();
//声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
//将随机数放入盐变量中
        random.nextBytes(salt);
//声明消息摘要对象
        MessageDigest md = null;
//创建消息摘要
        md = MessageDigest.getInstance("MD5");
//将盐数据传入消息摘要对象
        md.update(salt);
//将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
//获得消息摘要的字节数组
        byte[] digest = md.digest();

//因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];
//将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
//将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
//将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }
    /**

     将指定byte数组转换成16进制字符串
     @param b
     @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(getEncryptedPwd("123456"));
    }

}
