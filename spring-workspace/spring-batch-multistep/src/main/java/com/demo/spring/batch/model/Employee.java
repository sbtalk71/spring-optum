package com.demo.spring.batch.model;

public class Employee {
	protected int id;
	protected String name;
	protected String city;
	protected String position;

	public int getId() {
		return id;
	}

	public Employee(int id, String name, String city, String position) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.position = position;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+" "+name+" "+city+" "+position;
	}

}
