package com.demo.spring.entity;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.EmpRepository;

@Repository("dao1")
public class EmpDaoJpaImpl2 {
	@Autowired
	EmpRepository repo;
	public String saveEmp(Emp e) {
		repo.save(e);
		return "Data Saved in DB";
	}

	public Emp findEmpById(int id) {
		Optional<Emp> o=repo.findById(id);
		if(o.isPresent()) {
			return o.get();
		}else {
			return new Emp();
		}
	}

	public List<Emp> findAllEmp() {

		System.out.println(repo.getClass().getName());
		return repo.findAll();
	}
}
