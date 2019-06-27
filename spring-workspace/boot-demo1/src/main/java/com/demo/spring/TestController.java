package com.demo.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping(path="/greet",method=RequestMethod.GET)
	public String greet() {
		return "Welcome to Spring REST";
	}

}
