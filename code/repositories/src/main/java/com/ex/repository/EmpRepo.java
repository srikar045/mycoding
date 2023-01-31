package com.ex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.models.Employee;
@Repository
public interface EmpRepo extends JpaRepository<Employee, Integer>{
	public Employee findByUsername(String username);
	public Optional<Employee> findByEmail(String mail);
	public Employee findByEid(int id);
}
