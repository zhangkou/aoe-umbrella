package com.aoe.umbrella.restcontroller.test;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.aoe.umbrella.constants.Constants;

@Component
@Path("/test")
public class TestController {
	@GET
	@Path("/m1")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET })
    public Map<String, Object> m1() {
        Map<String, Object> result = new HashMap<String, Object>() ;
        
        result.put("all", "test") ;
        result.put("id", "world") ;
        return result ;
    }
    
    
}
