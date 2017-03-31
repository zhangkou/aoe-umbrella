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
	
	@Select("select * from locker where "
			+ "sqrt("
					+ "("
						+ "((#{longitude}-longitude)*PI()*12656*cos(((#{latitude}+latitude)/2)*PI()/180)/180) "
						+ "*"
						+ "((#{longitude}-longitude)*PI()*12656*cos (((#{latitude}+latitude)/2)*PI()/180)/180) "
					+ ")"
					+ "+"
					+ "("
						+ "((#{latitude}-latitude)*PI()*12656/180) "
						+ "*"
						+ "((#{latitude}-latitude)*PI()*12656/180) "
					+ ")"
			+ ")<#{scope}")
	List<Map<String, Object>> getLockersByLongAndLatitude(@Param("longitude") double longitude, @Param("latitude") double latitude,@Param("scope") int scope);
	
}