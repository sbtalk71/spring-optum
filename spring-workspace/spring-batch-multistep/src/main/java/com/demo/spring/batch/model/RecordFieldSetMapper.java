package com.demo.spring.batch.model;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class RecordFieldSetMapper implements FieldSetMapper<Employee> {

    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {

       // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Employee emp = new Employee();
        
        emp.setId(fieldSet.readInt("id"));
        emp.setName(fieldSet.readString("name"));
        emp.setCity(fieldSet.readString("city"));
        emp.setPosition(fieldSet.readString("position"));
       

        return emp;

    }

}