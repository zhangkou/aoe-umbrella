package com.aoe.umbrella.utils;

public class SmsUtils {	
	private static final String Url 				= "http://106.ihuyi.cn/webservice/sms.php?method=Submit&format=json";
	private static final String account				= "C16953635" ;
	private static final String password			= "07c00c95aeb2ca9f047cfb887597d260" ;
	
	public static String sendRegisterVCode(String phoneNum, String vCode){
		String registerTemplate = "您的验证码是：{{v1}}。请不要把验证码泄露给其他人。" ;
		String content = registerTemplate.replace("{{v1}}", vCode) ;
		StringBuilder urlParam = new StringBuilder(Url) ;
		urlParam.append("&").append("account=").append(account) ;
		urlParam.append("&").append("password=").append(password) ;
		urlParam.append("&").append("mobile=").append(phoneNum) ;
		urlParam.append("&").append("content=").append(content) ;
		return HttpUtils.doGet(urlParam.toString()) ;
	}
	
	public static String generateVCode(){
		Integer mobile_code = (int)((Math.random()*9+1)*100000);
		return mobile_code.toString() ;
	}
	
	public static void main(String [] args) {
		String vCode = generateVCode() ;
		String response = sendRegisterVCode("15190104187", vCode) ;
		System.out.println(response);
	}
	
}
