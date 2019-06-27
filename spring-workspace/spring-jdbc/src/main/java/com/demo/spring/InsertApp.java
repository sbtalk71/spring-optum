package com.demo.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class InsertApp {

	public static void main(String[] args) {
		String INSERT_STMT="insert into emp(empno,name,address,salary) values(?,?,?,?)";
		ApplicationContext ctx= new AnnotationConfigApplicationContext(AppConfig.class);
		JdbcTemplate jt = (JdbcTemplate)ctx.getBean("jdbcTemplate");
		
	int updatedRow =jt.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt=con.prepareStatement(INSERT_STMT);
				stmt.setInt(1, 300);
				stmt.setString(2, "John");
				stmt.setString(3, "London");
				stmt.setDouble(4, 78000);
				return stmt;
			}
		});
	
	System.out.println("Rows Inserted : "+updatedRow);
	}

}
