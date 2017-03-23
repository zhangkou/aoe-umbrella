package com.aoe.umbrella.config;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.stereotype.Component;

import com.aoe.umbrella.restcontroller.locker.LockerController;
import com.aoe.umbrella.restcontroller.user.UserController;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(UserController.class);
		register(LockerController.class);
	}

}
