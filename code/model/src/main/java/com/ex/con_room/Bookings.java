package com.ex.con_room;

import java.util.Date;
import java.util.List;


public class Bookings {
	private String title;
	private Date start;
	private Date end;
	private int eid;
	private long roomId;
	private int eids[];

	
	public int[] getEids() {
		return eids;
	}

	public void setEids(int[] eids) {
		this.eids = eids;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

}
