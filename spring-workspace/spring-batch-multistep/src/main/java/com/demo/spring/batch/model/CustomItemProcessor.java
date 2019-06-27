package com.demo.spring.batch.model;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Employee, Employee> {

    public Employee process(Employee item) {
        System.out.println("Processing..." + item);
        return item;
    }
}