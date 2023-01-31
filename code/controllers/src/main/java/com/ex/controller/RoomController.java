package com.ex.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.con_room.Con_room;
import com.ex.service.RoomService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoomController {
	@Autowired
	RoomService service;

	@PostMapping("/Rpost")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<String> saveRoom(@RequestBody Con_room room) {
		
		Con_room op= service.save(room);
		if(op!=null) {
		return ResponseEntity.ok("Saved Room Data"); 
		}else{
			return new ResponseEntity("Name in USE",HttpStatus.IM_USED);
		}
	}

	@GetMapping("/Rget")
	@ResponseBody
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public List<Con_room> findAll() {
		List<Con_room> room = service.getAll();
		return room;
	}

	@GetMapping("/Rget/{id}")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<Con_room> findRoom(@PathVariable("id") long id) {
		Optional<Con_room> room = service.findRoom(id);
		if (room.isPresent()) {
			return new ResponseEntity(room, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/Rput/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Con_room> update(@PathVariable("id") long id, @RequestBody Con_room room) {
		Optional<Con_room> data = service.findRoom(id);
		if (data.isPresent()) {
			return new ResponseEntity(service.update(id, room), HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200/roomview/{id}")
	@DeleteMapping("dell/{id}")
	@PreAuthorize("hasRole('Admin')")
	public void delete(@PathVariable("id") long id) {
		service.delete(id);
	}
}
