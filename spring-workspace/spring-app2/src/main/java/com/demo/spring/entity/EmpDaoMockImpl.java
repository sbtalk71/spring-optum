package com.demo.spring.entity;

public class EmpDaoMockImpl {
	
	public String save(Emp e) {

		return "Emp Saved with id " + e.getEmpId();
	}
}
