package com.aoe.umbrella.service.user;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aoe.umbrella.mapper.user.UserMapper;

@Component
@Transactional(readOnly = true)
public class UserService {
	@Autowired
	private UserMapper userMapper ;
	
	public List<Map<String, Object>> getAllUsers(){
		return this.userMapper.getAllUsers() ;
	}
	
	public Map<String, Object> getUserByPhoneNumber(String phoneNumber){
		if(StringUtils.isEmpty(phoneNumber)){
			return null ;
		}
		return this.userMapper.getUserByPhoneNumber(phoneNumber) ;
	}
	
	public boolean checkUserExistByPhoneNum(String phoneNumber){
		boolean userExist = false ;
		Map<String, Object> user = this.userMapper.getUserByPhoneNumber(phoneNumber) ;
		if(user != null && !user.isEmpty()){
			userExist = true ;
		}
		return userExist ;
	}
	
	
	public Map<String, Object> getUserByThird(String type, String id){
		if(StringUtils.isEmpty(type) || StringUtils.isEmpty(id)){
			return null ;
		}
		return this.userMapper.getUserByThird(type, id) ;
	}
	
	@Transactional(readOnly = false)
	public void createUser(Map<String, Object> user){
		if(user == null || user.isEmpty()){
			return  ;
		}
		this.userMapper.createUser(user);
	}
}
