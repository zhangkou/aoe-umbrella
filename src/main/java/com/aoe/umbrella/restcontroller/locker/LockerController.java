package com.aoe.umbrella.restcontroller.locker;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aoe.umbrella.mapper.locker.LockerMapper;

@RestController()
@RequestMapping("/locker")
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
}
