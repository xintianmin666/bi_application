package com.yiyun.yiyuncarservice.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * @author hxx
 * @version 1.0
 * @title: MD5test
 * @projectName yiyun
 * @description: TODO
 * @date 2020/2/20 17:40
 */
public class GetAddress {
  public static void main(String[] args) {
    // String uid = newGUID();
    String town = getAdressDetail(118.38548, 31.34072);
    System.out.println(town);
  }

  /** 逆地理编码 获取乡镇名称 */
  public static String getAdressDetail(double lng, double lat) {
    String result = ""; // 访问返回结果
    BufferedReader read = null; // 读取访问结果
    String town = ""; // 城镇名称
    String regionScope = ""; // 城镇范围

    String url = "http://api.map.baidu.com/reverse_geocoding/v3/";
    String param =
        "ak=SfrwGH7INvjPq7BwCrYrioBQZm9XXxrR&output=json&coordtype=wgs84ll&extensions_town=true"
            + "&location="
            + lat
            + ","
            + lng;
    // System.out.println("访问地址：" + url + "?" + param);
    try {
      // 创建url
      URL realurl = new URL(url + "?" + param);
      // 打开连接
      URLConnection connection = realurl.openConnection();
      // 设置通用的请求属性
      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty(
          "user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // 建立连接
      connection.connect();
      // 定义 BufferedReader输入流来读取URL的响应
      read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
      String line; // 循环读取
      while ((line = read.readLine()) != null) {
        result += line;
      }

      // 不为空，解析处城镇名称
      if (!"".equals(result)) {
        JSONObject jsonInfo = JSONObject.parseObject(result);
        town =
            JSONObject.parseObject(
                    JSONObject.parseObject(jsonInfo.getString("result"))
                        .getString("addressComponent"))
                .getString("district");
        regionScope =
            JSONObject.parseObject(jsonInfo.getString("result")).getString("formatted_address");
        System.out.println(regionScope);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (read != null) { // 关闭流
        try {
          read.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return town;
  }

  /**
   * 产生一个32位的GUID
   *
   * @return
   */
  public static String newGUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}
