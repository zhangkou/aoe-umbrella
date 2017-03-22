package com.aoe.umbrella.mapper.locker;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LockerMapper {

	@Select("select * from locker")
	List<Map<String, Object>> getAllLockers();
	
	@Select("select * from locker where id = #{id}")
	Map<String, Object> getLockerById(@Param("id") String id);

}