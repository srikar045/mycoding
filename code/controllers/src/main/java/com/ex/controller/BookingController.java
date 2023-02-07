package com.ex.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RestController;

import com.ex.con_room.Booking;
import com.ex.con_room.Bookings;
import com.ex.exceptions.ResourceNotFoundException;
import com.ex.models.Employee;
import com.ex.service.BookingService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BookingController {

	@Autowired
	BookingService service;

	@PostMapping("/book")
//	@PreAuthorize("hasRole('Admin')")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<String> saveBook(@RequestBody Bookings booking) {
		boolean run = service.savebook(booking);
		if (run == true) {
			return ResponseEntity.ok("Booked");
		} else {
			return new ResponseEntity("Change Start or End TIME to continue", HttpStatus.IM_USED);
		}
	}

	@GetMapping("/getbook")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager','Employee')")
	public ResponseEntity<List<Booking>> get() {
		List<Booking> list = service.showall();
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@GetMapping("/getmybook/{eid}")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<List<Booking>> getmy(@PathVariable int eid) {
		List<Booking> list = service.mybook(eid);
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@GetMapping("/getbook/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Booking> getById(@PathVariable long id) {
		Booking data = service.onebook(id);
		return new ResponseEntity(data, HttpStatus.OK);
	}

	@PutMapping("/putbook/{id}")
//	@PreAuthorize("hasRole('Admin')")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<Booking> update(@PathVariable long id, @RequestBody Bookings booking) {
		Booking bookings = service.updateTitle(id, booking);
		if (booking != null) {
			return new ResponseEntity(bookings, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.NOT_FOUND);
	}

	@PutMapping("/puttime/{id}")
//	@PreAuthorize("hasRole('Admin')")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<Booking> updateT(@PathVariable long id, @RequestBody Bookings booking) {
		Booking bookings = service.updateTime(id, booking);
		if (bookings != null) {
			return new ResponseEntity(bookings, HttpStatus.OK);
		}
		return new ResponseEntity("Time slot NOT Avaliable", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/del/{id}")
//	@PreAuthorize("hasRole('Admin')")
	@PreAuthorize("hasAnyRole('Admin','Lead','Manager')")
	public ResponseEntity<String> deleteBook(@PathVariable long id) {
		service.delid(id);
		return new ResponseEntity<String>("deleted data", HttpStatus.ACCEPTED);
	}
}
