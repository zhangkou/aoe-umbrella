package com.aoe.umbrella.utils;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class WXUtils {
	private static final String jscode2session_url 				= "https://api.weixin.qq.com/sns/jscode2session?" ;
	private static final String accessToken_url 				= "https://api.weixin.qq.com/cgi-bin/token?" ;
	private static final String template_send					= "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?" ;
	private static final String appid							= "wx45af4760ff335945" ;
	private static final String secret 							= "14242e14cde20a0b2c3fb1621b85da79" ;
	private static final String grant_type 						= "authorization_code" ;
	private static final String accessToken_grant_type			= "client_credential" ;
	private static final String template_id						= "_Ez9IeIX3bmnGvom5mAYYOslUOQaZxXfKfMRYJFPmHI" ;
	
	private static Long expires = 0L ;
	private static String accessToken = null ;
	
	public static Map<String, Object> jscode2session(String js_code) throws Exception{
		Map<String, Object> jscode2session = null ;
		
		if(StringUtils.isNotBlank(js_code)){
			String token_url = jscode2session_url + "appid=" + appid + "&secret=" + secret + "&js_code=" + js_code + "&grant_type=" + grant_type  ;
			String responseContent = HttpUtils.doGet(token_url);
			if(StringUtils.isNotEmpty(responseContent)){
				JsonMapper jsonMapper = new JsonMapper() ;
				jscode2session =  jsonMapper.fromJson(responseContent, Map.class) ;
			}
		}
		
		return jscode2session ;
	}
	
	public static String getAccessToken() throws Exception{
		String token = null ;
		
		boolean refreshAccessToken = true ;
		if(StringUtils.isNotEmpty(accessToken)){
			Long current = new Date().getTime() ;
			if(current < expires){
				refreshAccessToken = false ;
				token = accessToken ;
			}
		}
		
		if(refreshAccessToken){
			String token_url = accessToken_url + "grant_type=" + accessToken_grant_type + "&appid=" + appid + "&secret=" + secret  ;
			String responseContent = HttpUtils.doGet(token_url);
			if(StringUtils.isNotEmpty(responseContent)){
				JsonMapper jsonMapper = new JsonMapper() ;
				Map<String, Object> result =  jsonMapper.fromJson(responseContent, Map.class) ;
				if(result != null && !result.isEmpty()){
					token = (String)result.get("access_token") ;
					if(StringUtils.isNotEmpty(token)){
						accessToken = token ;
						expires = new Date().getTime() + 7200000 ;
					}
				}
			}
		}
		
		return token ;
	}
	
	public static String sendTemplate(Map<String, Object> entry) throws Exception{
		String response = null ;
		
		if(entry != null){
			if(!entry.containsKey("template_id")){
				entry.put("template_id", template_id) ;
			}
			String template_send_url = template_send + "access_token=" + getAccessToken() ;
			HttpUtils.doPost(template_send_url, entry) ;
		}
		
		return response ;
	}
	public static void main(String[] args) {
		try {
			System.out.println(getAccessToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
