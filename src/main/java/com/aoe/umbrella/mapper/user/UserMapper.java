package com.aoe.umbrella.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	@Select("select * from user")
	List<Map<String, Object>> getAllUsers();
	
	@Select("select * from user where phone_number = #{phoneNumber}")
	Map<String, Object> getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
	@Select("select * from user where third_type = #{type} and third_id = #{id}")
	Map<String, Object> getUserByThird(@Param("type") String type, @Param("id") String id);
	
	void updateUser(Map<String, Object> user);
	
	void createUser(Map<String, Object> user);
}