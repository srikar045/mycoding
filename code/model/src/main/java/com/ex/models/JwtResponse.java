package com.ex.models;

public class JwtResponse {

	private Employee employee;
	private String jwtToken;
	
	
	public JwtResponse(Employee employee, String jwtToken) {
		this.employee = employee;
		this.jwtToken = jwtToken;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public String getJwtToken() {
		return jwtToken;
	}


	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	
}
