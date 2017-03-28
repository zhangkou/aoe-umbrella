package com.aoe.umbrella.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.aoe.umbrella.restcontroller.test.TestController;
import com.aoe.umbrella.restcontroller.user.UserController;
import com.aoe.umbrella.restcontroller.wx.WXController;

@Component
@Configuration
@ApplicationPath("/jersey")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(TestController.class);
		register(UserController.class);
		register(WXController.class);
	}

}
