package com.carservice.project.wzcx;


import org.json.JSONObject;
import org.json.XML;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ViolationUtil {
    //将中文转成utf8格式
    public static String encodeUtf8(String xmDoc){
        try {
            return URLEncoder.encode(xmDoc,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    //将utf8格式转成中文
    public static String decodeUtf8(String xmDoc){
        try {
            return URLDecoder.decode(xmDoc,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    //xml文档头封装
    public static String getXmlFileHead(){
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>\n";

    }
    //xml文档尾封装
    public static String getXmlFileFoot(){
        return "</root>";
    }

    public static String bean2xmlUtf8(Map bean, String itemName, String itemId){
        StringBuffer buffer = new StringBuffer();
        Set s = bean.keySet();
        Iterator i = s.iterator();
        buffer.append("<");
        buffer.append(itemName);
        if (itemId==null||itemId.equals("")){
            buffer.append(">");
        }else{
            buffer.append("id=\"");
            buffer.append(itemId);
            buffer.append("\">");
        }
        while (i.hasNext()){
            String key = (String) i.next();
            Object value = bean.get(key);
            buffer.append("\n<");
            buffer.append(key);
            buffer.append(">");
            if (value==null||value.toString().equals("")){
                //buffer.append("");
            }else{
                buffer.append(encodeUtf8(value.toString()));
            }
            buffer.append("</");
            buffer.append(key);
            buffer.append(">");
        }
        buffer.append("\n</");
        buffer.append(itemName);
        buffer.append(">\n");
        return buffer.toString();
    }

    public static JSONObject xml2jsonString(String xml){
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj;
    }

    public static void main(String[] args) throws RemoteException {
        Map map = new HashMap();
        map.put("gxrq","2020-10-13");
        String param = getXmlFileHead()+
                bean2xmlUtf8(map,"QueryCondition","")+
                getXmlFileFoot();
        String result = new TmriOutAccess().getTmriOutAccessHttpSoap12Endpoint().queryObjectOut("09","4E9C94F0257F14D6E050007F0100BB76","09C02",param);
        System.out.println(xml2jsonString(decodeUtf8(result)));

        Map map1 = new HashMap();
        map1.put("hpzl","01");
        map1.put("hphm","B84644");
        map1.put("fdjh","339380");
        map1.put("sfzmhm","");
        map1.put("sfzmhm","");
        map1.put("sjhm","18055524332");

        String param1 = getXmlFileHead()+
                bean2xmlUtf8(map1,"veh","")+
                getXmlFileFoot();
        String result1 = new TmriOutAccess().getTmriOutAccessHttpSoap12Endpoint().writeObjectOut("09","4E9C94F0257F14D6E050007F0100BB76","09C55",param1);
        System.out.println(xml2jsonString(decodeUtf8(result1)));

    }
}