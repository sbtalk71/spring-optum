package com.demo.spring;

import org.springframework.batch.item.ItemProcessor;

import com.demo.spring.model.Emp;

public class EmpItemProcessor implements ItemProcessor<Emp, Emp> {

	public Emp process(Emp item) throws Exception {
		System.out.println(item);
		return item;
	}

}
