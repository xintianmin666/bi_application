package com.yiyun.yiyuncarservice.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hxx
 * @version 1.0
 * @title: HttpPostUtil
 * @projectName 01.后台代码
 * @description: TODO
 * @date 2020/3/11 15:48
 */
@Component
public class HttpPostUtil {

    private static Logger log = LoggerFactory.getLogger(HttpPostUtil.class);

    public static String sendPost2(String strurl, String param) throws IOException {
        StringBuffer sbf = new StringBuffer();
        try {
            URL realUrl = new URL(strurl);
            // 将realUrl以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection =
                    (HttpURLConnection) realUrl.openConnection(); // 此时cnnection只是为一个连接对象,待连接中
            // 设置连接输出流为true,默认false (post请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);
            // 设置连接输入流为true
            connection.setDoInput(true);
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            // post请求缓存设为false
            connection.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            //            String query = data.toString();
            // 将参数输出到连接
            //            dataout.write(param.getBytes("utf-8"));
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
            log.info("开始调用第三方的接口===" + new Timestamp(System.currentTimeMillis()));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sbf.append(lines);
            }
            log.info(
                    "打印获取到调用的结果==="
                            + sbf.toString()
                            + "===目前的时间==="
                            + new Timestamp(System.currentTimeMillis()));
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sbf.toString();
    }

    public static JSONObject doPost(JSONObject date, String port) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        // 要调用的接口方法
        String url = port;
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        log.info("保险执行请求参数====" + date.toString());
        try {

            StringEntity s = new StringEntity(date.toString(), "utf-8"); // 此处为解决传输来的数据变成乱码的问题
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.addHeader("content-type", "text/xml;charset:utf-8");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == 200) { // 此处判断状态是否为200
                String result = EntityUtils.toString(res.getEntity());
                String str1 = URLDecoder.decode(result, "UTF-8");
                jsonObject = JSONObject.parseObject(str1); // 返回json格式：
                log.info("保险执行返回结果====" + str1);
            }
        } catch (IOException f) {
            throw new IOException(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

//  public static JSONObject getInsuranceParam() {
//    String param;
//    String Url;
//    // 解析json
//    JSONObject jsonObject = new JSONObject();
//    Url = DataConstants.validatePowerUrl;
//    StringBuilder sb = new StringBuilder();
//    sb.append("?app_id=" + DataConstants.app_id + "");
//    sb.append("&app_secret=" + DataConstants.app_secret + "");
//    sb.append("&code=" + DataConstants.code + "");
//    sb.append("&grant_type=" + DataConstants.grant_type + "");
//    param = sb.toString();
//    Url = Url.concat(param);
//    try {
//      jsonObject = (JSONObject) JSONObject.parse(sendPost2(Url, ""));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    JSONObject jdata = jsonObject.getJSONObject("data");
//    return jdata;
//  }

    public static JSONObject sendPost(JSONObject date, String port) {
        HttpClient client = HttpClients.createDefault();
        // 要调用的接口方法
        String url = port;
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        try {

            StringEntity s = new StringEntity(date.toString(), "utf-8"); // 此处为解决传输来的数据变成乱码的问题
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.addHeader("content-type", "text/xml;charset:utf-8");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == 200) { // 此处判断状态是否为200
                String result = EntityUtils.toString(res.getEntity());
                String str1 = URLDecoder.decode(result, "UTF-8");
                jsonObject = JSONObject.parseObject(str1); // 返回json格式：
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static String httpURLConnectionPOST(String url, JSONObject data) throws IOException {
        StringBuffer sbf = new StringBuffer();
        try {
            log.info("调用接口传入的参数：" + data.toString());
            System.out.println("调用接口传入的参数：" + data.toString());
            URL realUrl = new URL(url);
            // 将realUrl以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection =
                    (HttpURLConnection) realUrl.openConnection(); // 此时cnnection只是为一个连接对象,待连接中
            // 设置连接输出流为true,默认false (post请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);
            // 设置连接输入流为true
            connection.setDoInput(true);
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            // post请求缓存设为false
            connection.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            String query = data.toString();
            // 将参数输出到连接
            dataout.write(query.getBytes("UTF-8"));
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sbf.append(lines);
            }
            reader.close();
            connection.disconnect();
            log.info("返回来的报文：" + sbf.toString());
            System.out.println("返回来的报文：" + sbf.toString());
        } catch (IOException f) {
            throw new IOException(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sbf.toString();
    }

    /**
     * 发起http请求并获取结果
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest1(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
      /*  // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new MyX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();*/

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            // httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
            log.info("开始调用第三方的接口===" + new Timestamp(System.currentTimeMillis()));
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            boolean flag = false;
            if (buffer.toString().contains("<ns:return>")) {
                flag = true;
                Pattern p = Pattern.compile("<ns:return>(.+?)</ns:return>");
                Matcher m = p.matcher(buffer.toString());
                while (m.find()) {
                    str = m.group(1);
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            if (flag) {
                jsonObject = JSONObject.parseObject(str);
            } else {
                jsonObject = JSONObject.parseObject(buffer.toString());
            }
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        log.info("调用接口返回结果====" + buffer.toString());
        return jsonObject;
    }

    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        String str = "";
        StringBuffer buffer = new StringBuffer();
        try {
      /*  // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new MyX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();*/

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);
            // httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
            log.info("开始调用第三方的接口===" + new Timestamp(System.currentTimeMillis()));
            log.info("开始调用第三方的接口参数===" + outputStr);
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        log.info("调用接口返回结果====" + buffer.toString());
        return jsonObject;
    }
}
