package com.demo.spring.batch;

import java.util.List;

import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;

import com.demo.spring.batch.model.Employee;

public class MemItemReader extends ListItemReader<Employee> {

   @SuppressWarnings("unchecked")
   public MemItemReader(ListItemWriter<Employee> writer) {
      super((List<Employee>) writer.getWrittenItems());

   }

}