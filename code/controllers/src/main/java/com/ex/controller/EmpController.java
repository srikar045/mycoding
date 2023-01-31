package com.ex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.models.Employee;
import com.ex.service.EmpService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmpController {
	@Autowired
	EmpService service;

	@PostMapping("/post")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<String> saveEmp(@RequestBody Employee emp) {

		boolean run=service.saveEmp(emp);
		if(run==true) {
			return ResponseEntity.ok("Saved Employee Data");	
		}else {
			return new ResponseEntity("Username or Email Exsist", HttpStatus.IM_USED);
			
		}
		
	}
	
	@GetMapping("/get")
	@ResponseBody
	@PreAuthorize("hasRole('Admin')")
	public List<Employee> findAll() {	//use this only to check in postman findAll(@RequestBody Employee employee)
		List<Employee> emp = service.getAll();
		return emp;
	}

	@GetMapping("/get/{id}")
//	@PreAuthorize("hasRole('Admin') or hasRole('Lead')")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<Employee> findone(@PathVariable("id") int id) {
		Optional<Employee> emp = service.findEmp(id);
		if (emp.isPresent()) {
			return new ResponseEntity(emp, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

	}
	
	@PutMapping("put/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Employee> update(@PathVariable("id") int id, @RequestBody Employee emp){
		Optional<Employee>old=service.findEmp(id);
		if(old.isPresent()) {
		
			return new ResponseEntity(service.update(id, emp),HttpStatus.OK);
		}
		return new ResponseEntity(null,HttpStatus.NOT_MODIFIED);
	}
	@CrossOrigin(origins = "http://localhost:4200/delete/{id}")
	@DeleteMapping("delete/{id}")
	@PreAuthorize("hasRole('Admin')")
	public void delete(@PathVariable("id") int id) {

		service.delete(id);
		//return "Record Deleted";
	}
}
