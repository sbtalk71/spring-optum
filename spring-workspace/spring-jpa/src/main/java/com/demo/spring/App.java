package com.demo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.demo.spring.entity.Emp;
import com.demo.spring.entity.EmpDaoJpaImpl2;

public class App {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		EmpDaoJpaImpl2 dao = (EmpDaoJpaImpl2) ctx.getBean("dao1");

		//String resp = dao.saveEmp(new Emp(206, "Chetan", "Delhi", 89000));
		//System.out.println(resp);
		
		Emp e= dao.findEmpById(200);
		System.out.println(e.getEmpId()+" "+e.getName());
		System.out.println(dao.getClass().getName());
		
		dao.findAllEmp().stream().forEach(e1->System.out.println(e1.getEmpId()+" "+e1.getName()));

	}

}
