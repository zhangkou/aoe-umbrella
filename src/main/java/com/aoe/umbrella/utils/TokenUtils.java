package com.aoe.umbrella.utils;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.lang3.StringUtils;
import com.aoe.umbrella.constants.Constants;
import com.aoe.umbrella.entity.RestMessage;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

public final class TokenUtils {
	private static final String SECRET;

	static {
		SECRET = "I8cT7W6lhlEcbYiYyeR1j3kBEaTxVZbiF6qJtLtwJq30xH2tTskR04JzIbZU8dvN";
	}

	private static JWTSigner signer = new JWTSigner(SECRET);
	private static JWTVerifier verifier = new JWTVerifier(SECRET);
	
	public static String createToken(Map<String, Object> claims){
		if(claims == null || claims.isEmpty()){
			return null ;
		}
        claims.put("createTokenDate", System.currentTimeMillis());
        String token = signer.sign(claims);
        return token;
	}
	
	public static Map<String, Object> decodeToken(String token) {
		Map<String, Object> decodedPayload;
		try {
			decodedPayload = verifier.verify(token);
		} catch (Exception e) {
			RestMessage userRestMessage = new RestMessage(Constants.TOKEN_INVALID, Constants.REST_TYPE_E,  "token invalid");
			HashMap<String,Object> map=new HashMap<String, Object>();
			map.put(Constants.REST_MESSAGE_KEY, userRestMessage);
			throw RsResponse.buildJsonException(Status.OK,map) ;
		}
		return decodedPayload;
	}
	
	public static String resetToken(Map<String, Object> claims){
		return TokenUtils.createToken(claims) ;
	}
	
	public static String getHeaderValueByHttpHeaders(HttpHeaders httpHeaders, String headerKey){
		if (httpHeaders != null && StringUtils.isNotEmpty(headerKey)) {
			if(null != httpHeaders.getRequestHeaders().get(headerKey)){
				return String.valueOf(httpHeaders.getRequestHeaders().get(headerKey).get(0)) ;
			}
		}
		return null ;
	}
}
