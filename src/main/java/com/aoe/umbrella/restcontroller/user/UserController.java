package com.aoe.umbrella.restcontroller.user;

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
        
        result.put("all", this.userMapper.getAllUsers()) ;
        result.put("id", this.userMapper.getUserByPhoneNumber("15190104187")) ;
        return result ;
    }
    
    
}
