package com.aoe.umbrella.restcontroller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aoe.umbrella.constants.Constants;
import com.aoe.umbrella.entity.RestMessage;
import com.aoe.umbrella.entity.ValidationEntity;
import com.aoe.umbrella.service.user.UserService;
import com.aoe.umbrella.utils.MemoryCacheUtils;
import com.aoe.umbrella.utils.TokenUtils;

@Component
@Path("/user")
public class UserController {
	@Autowired
	UserService userService ;
	
	@POST
	@Path("/register")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET })
	public Map<String, Object> register(Map<String, Object> postContent){
		Map<String, Object> map = new HashMap<String, Object>() ;
		
		if(postContent == null || postContent.isEmpty()){
			RestMessage restMessage=new RestMessage(Constants.POST_CONTENT_NULL, Constants.REST_TYPE_E, Constants.POST_CONTENT_NULL);
			map.put(Constants.REST_MESSAGE_KEY, restMessage);
			return map ;
		}
		String openId			= (String)postContent.get("openId") ;
		String phoneNumber 		= (String)postContent.get("phoneNumber") ;
		String password			= (String)postContent.get("password") ;
		String validationCode 	= (String)postContent.get("validationCode") ;
		if(	StringUtils.isEmpty(openId) || StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(validationCode) || StringUtils.isEmpty(password)){
			RestMessage restMessage=new RestMessage(Constants.POST_CONTENT_NULL, Constants.REST_TYPE_E, Constants.POST_CONTENT_NULL);
			map.put(Constants.REST_MESSAGE_KEY, restMessage);
			return map ;
		}
		
		if(this.userService.checkUserExistByPhoneNum(phoneNumber)){
			RestMessage restMessage=new RestMessage(Constants.USER_REGISTER_EXIST, Constants.REST_TYPE_E, Constants.USER_REGISTER_EXIST);
			map.put(Constants.REST_MESSAGE_KEY, restMessage);
			return map ;
		}
		
		MemoryCacheUtils memoryCacheUtils = MemoryCacheUtils.getInstance() ;
		ValidationEntity validationEntity = (ValidationEntity)memoryCacheUtils.get(phoneNumber) ;
		//if(validationEntity != null){
			//Long expires = validationEntity.getValidationDate().getTime() + 3 * 60 * 1000 ;
			//Long current = new Date().getTime() ;
			//if(current >= expires){
				//TODO
				//return null ;
			//}
			Map<String, Object> user = new HashMap<String, Object>() ;
			user.put("status", "3") ;
			user.put("phone_number", phoneNumber) ;
			user.put("password", password) ;
			user.put("third_type", "wx_mini") ;
			user.put("third_id", openId) ;
			user.put("register_date", new Date()) ;
			String token = TokenUtils.createToken(user);
			user.put("token", token) ;
			this.userService.createUser(user);
			
			RestMessage restMessage=new RestMessage(Constants.USER_REGISTER_OK, Constants.REST_TYPE_S, Constants.USER_REGISTER_OK);
			map.put(Constants.REST_MESSAGE_KEY, restMessage);
			return map ;
		//}
		

	}
}
