package com.aoe.umbrella.restcontroller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aoe.umbrella.mapper.user.UserMapper;

@RestController()
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserMapper userMapper ;
	
	@RequestMapping("/m1")
    public Map<String, Object> m1() {
        Map<String, Object> result = new HashMap<String, Object>() ;
        
        Map<String, Object> user = this.userMapper.getUserByThird("wx_mini", "456") ;
        result.put("all", this.userMapper.getAllUsers()) ;
        result.put("id", this.userMapper.getUserByPhoneNumber("15190104187")) ;
        result.put("wx", user) ;
        result.put("wxx", this.userMapper.getUserByThird("wx_mini", "45s6")) ;
        
        
    
        user.put("third_id", "中文") ;
        user.put("register_date", new Date()) ;
        this.userMapper.updateUser(user);
        return result ;
    }
    
    
}
