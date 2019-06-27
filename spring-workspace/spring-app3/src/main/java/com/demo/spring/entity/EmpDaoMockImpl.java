package com.demo.spring.entity;

import org.springframework.stereotype.Repository;

@Repository
public class EmpDaoMockImpl {
	
	public String save(Emp e) {

		return "Emp Saved with id " + e.getEmpId();
	}
}
