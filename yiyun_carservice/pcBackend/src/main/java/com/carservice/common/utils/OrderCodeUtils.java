package com.carservice.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OrderCodeUtils {
	/**
	 * 生成18位订单号
	 * 
	 */
	public static String getCoder() {
		String date = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		//String random1 = (int) (Math.random()*90 + 10) + "";
		String random2 = (int) (Math.random()*900 + 100) + "";
		String code = date + random2;
		return code;
	}

	public static String getFpqqlsh() {
		String date = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		String random2 = (int) (Math.random()*90 + 10) + "";
		String code = date + random2;
		return code;
	}

	/**
	 * 生成8位随机码
	 * 
	 */
	public static String get8Code() {
		String random1 = (int) (Math.random()*9000 + 1000) + "";
		String random2 = (int) (Math.random()*9000 + 1000) + "";
		String code = random1 + random2;
		return code;
	}
	
	/**
	 * 生成23位退单号
	 * 
	 */
	public static String getRefundCoder(String source) {
		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String random1 = (int) (Math.random()*90 + 10) + "";
		String random2 = (int) (Math.random()*900 + 100) + "";
		String code = source + random1 + date + random2;
		return code;
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws Exception
	 */
	/*public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1000; i++) {
			String content = get8Code();
			System.out.println(content);
		}
		
	}*/

}
