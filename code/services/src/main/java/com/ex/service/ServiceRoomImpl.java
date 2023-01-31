package com.ex.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.con_room.Con_room;
import com.ex.repository.RoomRepo;

@Service
public class ServiceRoomImpl implements RoomService{
	private Date date=new Date();

	@Autowired
	RoomRepo roomRepo;
	
	@Override
	public Con_room save(Con_room room) {
		Con_room aaa=roomRepo.findByName(room.getName());
		if(aaa==null) {
		Con_room data=room;
		
		data.setCreated_by("SUPER_ADMIN");
		data.setCreated_on(date);
		data.setUpdated_by(null);
		data.setUpdated_on(null);
		return roomRepo.save(data);
		}else {
			return null;
		}
	}

	@Override
	public List<Con_room> getAll() {
		return roomRepo.findAll();
	}

	@Override
	public Optional<Con_room> findRoom(long id) {
		Optional<Con_room>room=roomRepo.findById(id);
		return room;
	}

	@Override
	public void delete(long id) {
		roomRepo.deleteById(id);
	}

	@Override
	public Con_room update(long id, Con_room room) {
		Optional<Con_room>rooms=roomRepo.findById(id);
		if(rooms.isPresent()) {
			Con_room data=rooms.get();
			data.setName(room.getName());
			data.setFloor(room.getFloor());
			data.setCapacity(room.getCapacity());
			data.setUpdated_by("SUPER_ADMIN");
			data.setUpdated_on(date);
			
			return roomRepo.save(data);
		}else {
		return null;
		}
	}

}
