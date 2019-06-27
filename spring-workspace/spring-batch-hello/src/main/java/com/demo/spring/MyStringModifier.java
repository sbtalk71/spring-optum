package com.demo.spring;

import org.springframework.batch.item.ItemProcessor;

public class MyStringModifier implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Processing...."+item);
		return item+" Processed";
	}

	
}
