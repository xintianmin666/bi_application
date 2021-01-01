package com.yiyun.yiyuncarservice.utils;

import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hxx
 * @title: TokenUtil
 * @projectName yiyun
 * @description: Token工具类
 * @date 2020/2/3 14:30
 */
public class TokenUtil {

    public static String getTokenUserId() {
		String token = getRequest().getHeader("token");// 从 http 请求头中取出 token
		String userId = JWT.decode(token).getAudience().get(0);
		return userId;
	}


	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return requestAttributes == null ? null : requestAttributes.getRequest();
	}
}
