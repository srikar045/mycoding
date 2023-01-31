package com.ex.con_room;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "con_room")
public class Con_room {

	@Id
	@Column(name = "room_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roomId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "floor")
	private int floor;
	
	@Column(name = "capacity")
	private int capacity;
	
	@Column(name = "created_by")
	private String created_by;
	
	@Column(name = "created_on")
	private Date created_on;
	
	@Column(name = "updated_by")
	private String updated_by;
	
	@Column(name = "updated_on")
	private Date updated_on;

	@OneToMany(mappedBy ="rid",fetch = FetchType.LAZY,cascade={CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.REFRESH,
			CascadeType.REMOVE})
			@JsonIgnore 
			@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
			private Set<Booking> booking;
	
	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	@Override
	public String toString() {
		return "Con_room [roomId=" + roomId + ", name=" + name + ", floor=" + floor + ", capacity=" + capacity
				+ ", created_by=" + created_by + ", created_on=" + created_on + ", updated_by=" + updated_by
				+ ", updated_on=" + updated_on + ", booking=" + booking + "]";
	}

}
