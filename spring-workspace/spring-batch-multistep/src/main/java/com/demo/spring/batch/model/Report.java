package com.demo.spring.batch.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Report extends Employee {
	private String annualReport;

	public String getAnnualReport() {
		return annualReport;
	}

	public void setAnnualReport(String annualReport) {
		this.annualReport = annualReport;
	}

	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Report(int id, String name, String city, String position, String report) {
		super(id, name, city, position);
		this.annualReport = report;
	}
@Override
public String toString() {
	// TODO Auto-generated method stub
	return super.toString()+" "+annualReport;
}
}
