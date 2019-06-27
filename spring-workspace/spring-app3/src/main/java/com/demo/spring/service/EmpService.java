package com.demo.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Emp;
import com.demo.spring.entity.EmpDaoMockImpl;

@Service
public class EmpService {
	
	@Autowired
	EmpDaoMockImpl dao;
	

	public String registerEmp(int id,String name,String city,double sal) {
		String resp=dao.save(new Emp(id, name, city, sal));
		return resp;
	}
}
