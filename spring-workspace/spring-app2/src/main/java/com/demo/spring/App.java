package com.demo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.spring.service.EmpService;

public class App {

	public static void main(String[] args) {
		ApplicationContext ctx= new ClassPathXmlApplicationContext("context.xml");
		EmpService service=(EmpService)ctx.getBean("service");
		
		System.out.println(service.registerEmp(100, "Ranjith", "Hyderabad", 60000));

	}

}
