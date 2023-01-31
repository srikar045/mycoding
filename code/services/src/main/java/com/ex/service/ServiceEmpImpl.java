package com.ex.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ex.models.Employee;
import com.ex.models.Roles;
import com.ex.repository.EmpRepo;
import com.ex.repository.RoleRepo;

@Service
public class ServiceEmpImpl implements EmpService {
	private Date date = new Date();

	@Autowired
	EmpRepo empRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	public boolean saveEmp(Employee emp) {
		Employee empusername = empRepo.findByUsername(emp.getUsername());
		Optional<Employee> empmail = empRepo.findByEmail(emp.getEmail());
		if (empmail.isPresent() || empusername != null) {
			return false;
		} else {
			Roles role = roleRepo.findByRole("Employee");
			Set<Roles> userRole = new HashSet<>();
			userRole.add(role);

			Employee Emp = emp;
			Emp.setPassword(passwordEncoder.encode(emp.getPassword()));

			Emp.setRole(userRole);

			Emp.setCreated_by("SUPER_ADMIN");
			Emp.setCreated_on(date);
			Emp.setUpdated_by(null);
			Emp.setUpdated_on(null);
			empRepo.save(Emp);
			return true;
		}

	}

	public List<Employee> getAll() {

		return empRepo.findAll();
	}

	public Optional<Employee> findEmp(int number) {
		Optional<Employee> emp = empRepo.findById(number);
		return emp;
	}

	public void delete(int id) {
		empRepo.deleteById(id);

	}

	public Employee update(int id, Employee Emp) {
		Optional<Employee> data = empRepo.findById(id);
		Employee empusername = empRepo.findByUsername(Emp.getUsername());
		Optional<Employee> empmail = empRepo.findByEmail(Emp.getEmail());
		if (data.isPresent()) {
//			if (empmail.isPresent() || empusername != null) {
//				return null;
//			} else {
			Employee emp = data.get();
			emp.setFname(Emp.getFname());
			emp.setLname(Emp.getLname());
			emp.setGender(Emp.getGender());
			emp.setEmail(Emp.getEmail());
			emp.setMobile(Emp.getMobile());
			emp.setUsername(Emp.getUsername());
			emp.setPassword((Emp.getPassword()));
			emp.setRole(Emp.getRole());
			emp.setUpdated_by("SUPER_ADMIN");
			emp.setUpdated_on(date);

			return empRepo.save(emp);}
//		}
			else {
			return null;
		}
	}

	@Override
	public boolean getEmail(String email) {
		Optional<Employee> empEmail = empRepo.findByEmail(email);
		if (empEmail.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getUsername(String username) {
		Employee empUsername = empRepo.findByUsername(username);
		if (empUsername != null) {
			return true;
		} else {
			return false;
		}
	}
	// we can use this method or we can encode directly in the code
//	public String getEncoded (String password) {
//		return passwordEncoder.encode((password));
//	}
}
