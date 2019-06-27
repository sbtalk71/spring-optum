package com.demo.spring;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class MyItemPrinter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		//items.stream().forEach(System.out::println);
		for(String item:items) {
			System.out.println(item);
		}

	}

}
