package com.demo.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.demo.spring.entity.Emp;

public class GetDataApp {

	public static void main(String[] args) {
		String INSERT_STMT="insert into emp(empno,name,address,salary) values(?,?,?,?)";
		ApplicationContext ctx= new AnnotationConfigApplicationContext(AppConfig.class);
		JdbcTemplate jt = (JdbcTemplate)ctx.getBean("jdbcTemplate");
		
		List<Emp> empList=jt.query("select * from emp", new RowMapper<Emp>() {

			@Override
			public Emp mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return new Emp(rs.getInt("EMPNO"), 
						rs.getString("NAME"), 
						rs.getString("ADDRESS"), 
						rs.getDouble("SALARY"));
			}
			
		});
		
	for(Emp e:empList) {
		System.out.println(e.getEmpId()+" "+e.getName()+" "+e.getCity()+" "+e.getSalary());
	}
	}

}
