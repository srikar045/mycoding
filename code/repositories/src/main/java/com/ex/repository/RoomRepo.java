package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.con_room.Con_room;
@Repository
public interface RoomRepo extends JpaRepository<Con_room, Long>{
	public Con_room findByName(String name);
	public Con_room findByRoomId(long id);
}
