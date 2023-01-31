package com.ex.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.con_room.Booking;
import com.ex.con_room.Bookings;
import com.ex.con_room.Con_room;
import com.ex.exceptions.ResourceNotFoundException;
import com.ex.models.Employee;
import com.ex.repository.BookingRepo;
import com.ex.repository.EmpRepo;
import com.ex.repository.RoomRepo;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepo bookingRepo;

	@Autowired
	RoomRepo roomRepo;
	
	@Autowired
	EmpRepo empRepo;
	
	Date date = new Date();

//	@SuppressWarnings("unused")
//	@Override
//	public boolean savebook(Booking book) {
//		Booking data = book;
//		Booking datas=new Booking();
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
//		
//		Date start = data.getStart();
//		Date end = data.getEnd();
//		Con_room room = data.getRid();
//		long roomid = room.getRoomId();
//		String str = String.valueOf(book.getEid().getEid());
//		List<Booking> list = bookingRepo.findByRid(room);
//		System.out.println(date + " " + start + " " + end);
//		if (start.after(date) && end.after(date) && end.after(start)) {
//
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.size());
//				Date lstart = list.get(i).getStart();
//				Date lend = list.get(i).getEnd();
//				if ((start.after(lstart) || start.before(lstart))
//						&& (start.after(lend) || start.before(lstart) || start.equals(lend)) && end.after(start)
//						&& (end.after(lend) || end.equals(lstart) || end.before(lstart))) {
//					System.out.println("if condition  2" + i);
//				} else {
//					System.out.println("Time Slot Already Booked");
//					return false;
//				}
//			}
//		} else {
//			return false;
//		}
//		datas.setStart(start);
//		datas.setEnd(end);
//		datas.setCreated(str);
//		datas.setCreatedon(date);
//		datas.setEid(data.getEid());
//		datas.setRid(data.getRid());
//		datas.setUpated(null);
//		datas.setUpatedon(null);
//		bookingRepo.save(datas);
//		return true;
//	}

	@Override
	public void delid(long bid) {
		Optional<Booking> data = bookingRepo.findById(bid);
		if (data.isPresent()) {
			bookingRepo.deleteById(bid);
		} else {
			throw new ResourceNotFoundException("Booking not found with " + bid);
		}
	}

	@Override
	public List<Booking> showall() {
		List<Booking> list = bookingRepo.findAll();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");

		for (int i = 0; i < list.size(); i++) {
			Date sdate = list.get(i).getStart();
			Date edate = list.get(i).getEnd();

			String cdate = df.format(list.get(i).getCreatedon());
			String s = df.format(sdate);
			String e = df.format(edate);

			try {
				list.get(i).setCreatedon(df.parse(cdate));
				list.get(i).setStart(df.parse(s));
				list.get(i).setEnd(df.parse(e));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public Booking onebook(long bid) {
		Booking data = bookingRepo.findById(bid)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not exist wit id:" + bid));
		return data;
	}

	@Override
	public Booking updateTitle(long id, Booking booking) {
		Booking data = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("booking not exist wit id:" + id));

		data.setTitle(booking.getTitle());
		data.setUpated(booking.getUpated());
		data.setUpatedon(date);
		Booking bookings = bookingRepo.save(data);
		if (booking != null) {
			return bookings;
		}
		return null;
	}

	@Override
	public Booking updateTime(long id, Booking booking) {
		Booking data = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("booking not exist wit id:" + id));

		Date start = booking.getStart();
		Date end = booking.getEnd();

		if (start.after(date) && end.after(date) && end.after(start)) {

			Date lstart = data.getStart();
			Date lend = data.getEnd();
			if ((start.after(lstart) || start.before(lstart))
					&& (start.after(lend) || start.before(lstart) || start.equals(lend)) && end.after(start)
					&& (end.after(lend) || end.equals(lstart) || end.before(lstart))) {
				System.out.println("if condition of Time Slot Worked");
			} else {
				System.out.println("Time Slot Already Booked");
				return null;
			}

		} else {
			return null;
		}
		data.setStart(start);
		data.setEnd(end);
		data.setUpated(booking.getUpated());
		data.setUpatedon(date);
		return bookingRepo.save(data);
	}

	@SuppressWarnings("unused")
	@Override
	public boolean savebook(Bookings book) {
//		Booking data = book;
		Booking datas = new Booking();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
		int eid = book.getEid();
		Date start = book.getStart();
		Date end = book.getEnd();
		long roomid = book.getRoomId();
		
		Employee emp=empRepo.findByEid(eid);
		
		Con_room room = roomRepo.findByRoomId(roomid);
		List<Booking> list = bookingRepo.findByRid(room);
		System.out.println(date + " " + start + " " + end);
		if (start.after(date) && end.after(date) && end.after(start)) {

			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.size());
				Date lstart = list.get(i).getStart();
				Date lend = list.get(i).getEnd();
				if ((start.after(lstart) || start.before(lstart))
						&& (start.after(lend) || start.before(lstart) || start.equals(lend)) && end.after(start)
						&& (end.after(lend) || end.equals(lstart) || end.before(lstart))) {
					System.out.println("if condition  2" + i);
				} else {
					System.out.println("Time Slot Already Booked");
					return false;
				}
			}
		} else {
			return false;
		}
		datas.setTitle(book.getTitle());
		datas.setStart(start);
		datas.setEnd(end);
		datas.setCreated(emp.getFname()+"."+emp.getLname());
		datas.setCreatedon(date);
		datas.setEid(emp);
		datas.setRid(room);
		datas.setUpated(null);
		datas.setUpatedon(null);
		bookingRepo.save(datas);
		return true;
	}

}

// lines from save method
//Date start = sdate;
//Date end = edate;
//Date start = data.getStart();
//Date end = data.getEnd();
//lines in for loop save method
//System.out.println("Bid: " + list.get(i).getBid() + " title: " + list.get(i).getTitle() + " start: "
//		+ list.get(i).getStart() + " end: " + list.get(i).getEnd());

//lines from findById
//Optional<Booking> data=bookingRepo.findById(bid);
//if(data.isPresent()) {
//return data;
//}else {
//	throw new ResourceNotFoundException("Booking not found with "+bid);
//}

//String s = data.getS();
//String e = data.getE();
//Date start = null;
//Date end = null;
//try {
//	start = df.parse(s);
//	end = df.parse(e);
//} catch (ParseException ex) {
//	ex.printStackTrace();
//}