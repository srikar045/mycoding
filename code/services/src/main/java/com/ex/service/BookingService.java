package com.ex.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ex.con_room.Booking;
import com.ex.con_room.Bookings;
import com.ex.models.Employee;

@Service
public interface BookingService {

//	public boolean savebook(Booking book);
	public boolean savebook(Bookings book);
	
	public void delid(long bid);
	public List<Booking> showall();
	public Booking onebook(long bid);
	public List<Booking> mybook(int emp);
	public Booking updateTitle(long id,Bookings booking);
	public Booking updateTime(long id,Bookings booking);
	
		
}
