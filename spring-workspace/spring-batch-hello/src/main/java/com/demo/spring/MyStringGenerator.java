package com.demo.spring;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MyStringGenerator implements ItemReader<String> {

	int counter=0;
	int limit=6;
	
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(counter<limit) {
			counter++;
			return new String("Hello "+counter);
		}
		return null;
	}

}
