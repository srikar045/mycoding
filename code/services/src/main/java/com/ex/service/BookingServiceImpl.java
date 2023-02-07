package com.ex.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.ex.con_room.Booking;
import com.ex.con_room.Bookings;
import com.ex.con_room.Con_room;
import com.ex.exceptions.BadRequestException;
import com.ex.exceptions.ResourceNotFoundException;
import com.ex.exceptions.UsedException;
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

	@Autowired
	private JavaMailSender mailSenderObj;

	Date date = new Date();

	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Override public boolean savebook(Booking book) { Booking data = book;
	 * Booking datas=new Booking(); DateFormat df = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm aa");
	 * 
	 * Date start = data.getStart(); Date end = data.getEnd(); Con_room room =
	 * data.getRid(); long roomid = room.getRoomId(); String str =
	 * String.valueOf(book.getEid().getEid()); List<Booking> list =
	 * bookingRepo.findByRid(room); System.out.println(date + " " + start + " " +
	 * end); if (start.after(date) && end.after(date) && end.after(start)) {
	 * 
	 * for (int i = 0; i < list.size(); i++) { System.out.println(list.size()); Date
	 * lstart = list.get(i).getStart(); Date lend = list.get(i).getEnd(); if
	 * ((start.after(lstart) || start.before(lstart)) && (start.after(lend) ||
	 * start.before(lstart) || start.equals(lend)) && end.after(start) &&
	 * (end.after(lend) || end.equals(lstart) || end.before(lstart))) {
	 * System.out.println("if condition  2" + i); } else {
	 * System.out.println("Time Slot Already Booked"); return false; } } } else {
	 * return false; } datas.setStart(start); datas.setEnd(end);
	 * datas.setCreated(str); datas.setCreatedon(date); datas.setEid(data.getEid());
	 * datas.setRid(data.getRid()); datas.setUpated(null); datas.setUpatedon(null);
	 * bookingRepo.save(datas); return true; }
	 */

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
	public List<Booking> mybook(int emp) {
		Employee emps = empRepo.findByEid(emp);
		if (emps == null) {
			throw new ResourceNotFoundException("Employee not exist with id:" + emp);
		}
		List<Booking> book = bookingRepo.findByEid(emps);

		return book;
	}

	@Override
	public Booking updateTitle(long id, Bookings booking) {
		Date date = new Date();
		Booking data = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("booking not exist with id:" + id));

		data.setTitle(booking.getTitle());
		data.setUpatedon(date);
		Booking bookings = bookingRepo.save(data);
		if (booking != null) {
			return bookings;
		}
		return null;
	}

	@Override
	public Booking updateTime(long id, Bookings booking) {
		Date date = new Date();
		Booking data = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("booking not exist wit id:" + id));
		int eid = booking.getEid();
		if (data.getTitle() != booking.getTitle()) {
			data.setTitle(booking.getTitle());
		}
		Date start = booking.getStart();
		Date end = booking.getEnd();
		Employee emp = empRepo.findByEid(eid);
		if ((start.equals(data.getStart()) && end.equals(data.getEnd()))) {
			return bookingRepo.save(data);
		} else {
			if (start.after(date) && end.after(date) && end.after(start)) {

				Date lstart = data.getStart();
				Date lend = data.getEnd();
				if ((start.after(lstart) || start.before(lstart))
						&& (start.after(lend) || start.before(lstart) || start.equals(lend)) && end.after(start)
						&& (end.after(lend) || end.equals(lstart) || end.before(lstart))) {
					System.out.println("if condition of Time Slot Worked");
				} else {
					System.out.println("Time Slot Already Booked");
					throw new UsedException("time slot not available:");
				}

			} else {
				throw new UsedException("time slot not available:");
			}

		}
		data.setStart(start);
		data.setEnd(end);
		data.setUpated(emp.getFname() + "." + emp.getLname());
		data.setUpatedon(date);
		return bookingRepo.save(data);
	}

	@SuppressWarnings("unused")
	@Override
	public boolean savebook(Bookings book) {
		Date date = new Date();
		Booking datas = new Booking();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
		int eid = book.getEid();
		Date start = book.getStart();
		Date end = book.getEnd();
		long roomid = book.getRoomId();
		int myList[] = book.getEids();
		System.out.println(Arrays.toString(myList));
		Employee emp = empRepo.findByEid(eid);
		if(book.getTitle().isEmpty()) {
			throw new BadRequestException("Title can't be empty");
		}
		
		Con_room room = roomRepo.findByRoomId(roomid);
		List<Booking> list = bookingRepo.findByRid(room);
		System.out.println(date + " " + start + " " + end);
		if(list!=null) {
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
					throw new UsedException("Time slot is not Available");
				}
			}
		} else {
			throw new ResourceNotFoundException("Time is in past");

		}
		}else {
			throw new ResourceNotFoundException("Room not Found");
		}
		datas.setTitle(book.getTitle());
		datas.setStart(start);
		datas.setEnd(end);
		datas.setCreated(emp.getFname() + "." + emp.getLname());
		datas.setCreatedon(date);
		datas.setEid(emp);
		datas.setRid(room);
		datas.setUpated(null);
		datas.setUpatedon(null);
		Booking abcd = bookingRepo.save(datas);
		sendmail1(abcd);
		for (int i = 0; i < myList.length; i++) {
			int id = myList[i];
			sendmail2(abcd, id);
		}

		return true;
	}

	private void sendmail1(Booking employee) {
		Employee emp = employee.getEid();
		int eid = emp.getEid();
		Employee employee1 = empRepo.findById(eid).get();
		Con_room book = employee.getRid();
		long rid = book.getRoomId();
		Con_room room = roomRepo.findById(rid).get();
		final String emailToRecipient = employee1.getEmail();
		final String emailSubject = "Booking succesful";
		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>You have succesfully Booked the Room"
				+ "<br><br>" + " <p>Booking details</p>" + "<p>Booking Id : " + employee.getBid() + "</p>"
				+ "<h3>BookedBy</h3>" + "<p>Name : " + employee1.getFname() + "." + employee1.getLname() + "</p>"
				+ "<p>Id : " + employee1.getEid() + "</p>"
				+ "<table border='1' width='300px' style='text-align:center;font-size:20px;'>"
				+ "<tr><td>Roomid</td><td>" + room.getRoomId() + "</td></tr><tr><td>Floor</td><td>" + room.getFloor()
				+ "</td></tr><tr><td>Start time</td><td>" + employee.getStart() + "</td></tr><tr><td>End time</td><td>"
				+ employee.getEnd() + "</table> </body></html>";
		mailSenderObj.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setText(emailMessage1, true);
				mimeMsgHelperObj.setSubject(emailSubject);
			}
		});
	}

	private void sendmail2(Booking employee, int ids) {
		Employee emp = employee.getEid();
		int eid = emp.getEid();
		Employee employee1 = empRepo.findById(ids).get();
		Con_room book = employee.getRid();
		long rid = book.getRoomId();
		Con_room room = roomRepo.findById(rid).get();
		final String emailToRecipient = employee1.getEmail();
		final String emailSubject = "Invitation Mail";
		final String emailMessage1 = "<html> <body> <p>Hi All,</p><p>You have been Invited to this Meeting" + "<br><br>"
				+ "<table border='1' width='300px' style='text-align:center;font-size:20px;'>"
				+ "<tr><td>Title</td><td>" + employee.getTitle() + "</td></tr><tr><td>Room</td><td>" + room.getRoomId()
				+ "</td></tr><tr><td>Start time</td><td>" + employee.getStart() + "</td></tr><tr><td>End time</td><td>"
				+ employee.getEnd() + "</table>" + " <h2>Invited By</h2>" + "<p>Employee Id : " + employee1.getEid()
				+ "</p>" + "<p>Name : " + employee1.getFname() + "" + employee1.getLname() + "</p>" + "<br>"
				+ "<p>Hope you will attend in this meeting</p>" + "<br>" + "<p>Thanks and regards</p>" + "<p> "
				+ employee1.getFname() + "" + employee1.getLname() + "</p>" + "<p>StratApps</p></body></html>";

		mailSenderObj.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setText(emailMessage1, true);
				mimeMsgHelperObj.setSubject(emailSubject);
			}
		});
	}

}

/*
 * lines from save method Date start = sdate; Date end = edate; Date start =
 * data.getStart(); Date end = data.getEnd(); lines in for loop save method
 * System.out.println("Bid: " + list.get(i).getBid() + " title: " +
 * list.get(i).getTitle() + " start: " + list.get(i).getStart() + " end: " +
 * list.get(i).getEnd());
 */

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