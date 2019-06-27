package com.demo.spring.batch.model;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<Employee, Report> {

	public Report process(Employee item) {
		Report report = new Report(item.getId(), item.getName(), item.getCity(), item.getPosition(),
				" Excellent Employee");
		System.out.println("Generating Report..." + report);
		return report;
	}
}