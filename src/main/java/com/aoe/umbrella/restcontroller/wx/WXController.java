package com.aoe.umbrella.restcontroller.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aoe.umbrella.constants.Constants;
import com.aoe.umbrella.entity.Page;
import com.aoe.umbrella.entity.RestMessage;
import com.aoe.umbrella.service.user.UserService;
import com.aoe.umbrella.utils.TokenUtils;
import com.aoe.umbrella.utils.WXUtils;

@Component
@Path("/wx")
public class WXController {
	@Autowired
	UserService userService ;
	
	@GET
	@Path("/jscode2session")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + Constants.CHARSET })
	public HashMap<String, Object> jscode2session(@QueryParam("js_code") String js_code) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>() ;
		
		if(StringUtils.isBlank(js_code)){
			RestMessage restMessage=new RestMessage(Constants.WX_JSCODE_NULL, Constants.REST_TYPE_E, "invalid code, jscode is null");
			map.put(Constants.REST_MESSAGE_KEY, restMessage);
			return map ;
		}
		
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>() ;
		
		Map<String, Object> result =  WXUtils.jscode2session(js_code) ;
		if(result != null && !result.isEmpty()){
			String errcode = String.valueOf(result.get("errcode")) ;
			if(StringUtils.isNotBlank(errcode) && !StringUtils.equalsIgnoreCase("null", errcode)){
				RestMessage restMessage=new RestMessage(Constants.WX_JSCODE_INVALID, Constants.REST_TYPE_E, (String)result.get("errmsg"));
				map.put(Constants.REST_MESSAGE_KEY, restMessage);
			}else{
				String openId = (String)result.get("openid") ;
				if(StringUtils.isNotEmpty(openId)){
					Map<String, Object> user = this.userService.getUserByThird("wx_mini", openId) ;	
					if(user != null){
						String token = TokenUtils.createToken(user);
						result.put("token",token);
						
						RestMessage restMessage=new RestMessage(Constants.WX_JSCODE_OK, Constants.REST_TYPE_S, "successfull");
						map.put(Constants.REST_MESSAGE_KEY, restMessage);	
					}else{
						RestMessage restMessage=new RestMessage(Constants.WX_USER_NULL, Constants.REST_TYPE_E, "can not find user");
						map.put(Constants.REST_MESSAGE_KEY, restMessage);
					}
					results.add(result) ;
					map.put(Constants.REST_DATA_KEY, new Page(results));
				}
				
			}
			
		}
		
		return map;
	}
}