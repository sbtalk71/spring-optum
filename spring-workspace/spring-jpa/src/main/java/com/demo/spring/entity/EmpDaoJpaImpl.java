package com.demo.spring.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("dao")
@Transactional
public class EmpDaoJpaImpl {
	@PersistenceContext
	EntityManager em;

	public String saveEmp(Emp e) {
		em.persist(e);
		return "Data Saved in DB";
	}

	public Emp findEmpById(int id) {
		Emp e = em.find(Emp.class, id);
		if (e != null) {
			return e;
		} else {
			throw new RuntimeException("No Emp Found");
		}
	}

	public List<Emp> findAllEmp() {

		Query query = em.createQuery("select e from Emp e");
		List<Emp> empList = query.getResultList();
		return empList;
	}
}
