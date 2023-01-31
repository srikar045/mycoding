package com.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.con_room.Booking;
import com.ex.con_room.Con_room;
import com.ex.models.Employee;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long >{

//	@Query("select book from booking book where book.room_id= :roomid")
	
//	@Query(value="SELECT * FROM public.booking "+
//			"WHERE rid = :roomid",nativeQuery=true)
//    public List<Booking> findByconference(long roomid);
	
	
	public List<Booking> findByRid(Con_room room);
	public List<Booking> findByEid(Employee emp);
	public Booking findByBid(long id);
}