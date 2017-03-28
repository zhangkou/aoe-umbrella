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
		if(postContent == null || postContent.isEmpty()){
			return null ;
		}
		String openId			= (String)postContent.get("openId") ;
		String phoneNumber 		= (String)postContent.get("phoneNumber") ;
		String validationCode 	= (String)postContent.get("validationCode") ;
		if(	StringUtils.isEmpty(openId) || 
			StringUtils.isEmpty(phoneNumber) || 
			StringUtils.isEmpty(validationCode)){
			return null ;
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
			user.put("third_type", "wx_mini") ;
			user.put("third_id", openId) ;
			user.put("register_date", new Date()) ;
			String token = TokenUtils.createToken(user);
			user.put("token", token) ;
			this.userService.createUser(user);
		//}
		
		
		return null ;
	}
}
