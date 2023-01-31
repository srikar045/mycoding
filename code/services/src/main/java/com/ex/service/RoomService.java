package com.ex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ex.con_room.Con_room;

@Service
public interface RoomService {
	public Con_room save(Con_room room);

	public List<Con_room> getAll();

	public Optional<Con_room> findRoom(long id);

	public void delete(long id);

	public Con_room update(long id, Con_room room);
	
	
}
