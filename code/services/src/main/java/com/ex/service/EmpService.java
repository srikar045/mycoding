package com.ex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ex.models.Employee;

@Service
public interface EmpService {
	public boolean saveEmp(Employee emp);

	public List<Employee> getAll();

	public Optional<Employee> findEmp(int id);

	public void delete(int id);

	public Employee update(int id, Employee emp);
	
	public  boolean getEmail(String email);
	
	public  boolean getUsername(String username);	
}