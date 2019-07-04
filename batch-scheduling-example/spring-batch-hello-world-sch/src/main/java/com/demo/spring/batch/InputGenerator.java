package com.demo.spring.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class InputGenerator implements ItemReader<String> {

	private int counter=0;
	private int limit=4;
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(counter<limit) {
			counter++;
			return new String("Hello - "+counter);
			
		}
		return null;
	}

}
