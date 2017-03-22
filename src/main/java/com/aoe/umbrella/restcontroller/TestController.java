package com.aoe.umbrella.restcontroller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/m1")
    public Map<String, Object> m1() {
        Map<String, Object> result = new HashMap<String, Object>() ;
        
        result.put("a", "1") ;
        result.put("b", 2) ;
        
        return result ;
    }
    
    
}
