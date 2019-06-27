package com.demo.spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer>{
	
	@Query(value="select e from Emp e where e.city like %?1%")
	public List<Emp> getEmpFromHyd(String city);

}
