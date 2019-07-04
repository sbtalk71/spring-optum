package com.demo.spring.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class OutputPrinter implements ItemWriter<String> {

	private int counter=0;
	@Override
	public void write(List<? extends String> items) throws Exception {
		System.out.println("hi..");
		if(counter<items.size()) {
			counter++;
			System.out.println("Counter : "+counter);
			throw new RuntimeException();
		}
		items.stream().forEach(System.out::println);

	}

}
