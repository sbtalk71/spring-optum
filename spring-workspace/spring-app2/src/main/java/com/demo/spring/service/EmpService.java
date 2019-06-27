package com.demo.spring.service;


import com.demo.spring.entity.Emp;
import com.demo.spring.entity.EmpDaoMockImpl;

public class EmpService {
	
	EmpDaoMockImpl dao;
	

	public void setDao(EmpDaoMockImpl dao) {
		this.dao = dao;
	}


	public String registerEmp(int id,String name,String city,double sal) {
		String resp=dao.save(new Emp(id, name, city, sal));
		return resp;
	}
}
