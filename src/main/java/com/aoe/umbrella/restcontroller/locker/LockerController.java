package com.aoe.umbrella.restcontroller.locker;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aoe.umbrella.constants.Constants;
import com.aoe.umbrella.mapper.locker.LockerMapper;

@RestController()
@RequestMapping("/locker")
@Path("/locker")
public class LockerController {
	@Autowired
	private LockerMapper lockerMapper ;

	@RequestMapping("/m1")
    public Map<String, Object> m1() {
        Map<String, Object> result = new HashMap<String, Object>() ;
        
        result.put("all", this.lockerMapper.getAllLockers()) ;
        result.put("id", this.lockerMapper.getLockerById("abcde")) ;
        return result ;
    }
    
	@GET
	@Path("/m2")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET })
    public Map<String, Object> m2() {
        Map<String, Object> result = new HashMap<String, Object>() ;
        
        result.put("all", "hello") ;
        result.put("id", "world") ;
        return result ;
    }
    
}
