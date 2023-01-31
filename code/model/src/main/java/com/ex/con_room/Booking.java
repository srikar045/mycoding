package com.ex.con_room;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.ex.models.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book")
	@SequenceGenerator(name = "book", sequenceName = "book",initialValue=10, allocationSize = 10)
	@Column(name="bid")
	private long bid;
	
	@Column(name="title")
	private String title;
	
	@Column(name="start")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm aa")
//	 @Temporal(TemporalType.TIMESTAMP)
	private Date start;
	
	@Column(name="endt")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm aa")
//	 @Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	@Column(name="created")
	private String created;
	
	@Column(name="createdon")
	private Date createdon;
	
	@Column(name="updated")
	private String upated;
	
	@Column(name="updatedon")
	private Date upatedon;
	
//	@Transient
//	private String s;
//
//	@Transient
//	private String e;
//
//	public String getS() {
//		return s;	}
//	public void setS(String s) {
//		this.s = s;	}
//	public String getE() {
//		return e;	}
//	public void setE(String e) {
//		this.e = e;	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="eid")
	private Employee eid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="rid")
	private Con_room rid;
	
	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getUpated() {
		return upated;
	}

	public void setUpated(String upated) {
		this.upated = upated;
	}

	public Date getUpatedon() {
		return upatedon;
	}

	public void setUpatedon(Date upatedon) {
		this.upatedon = upatedon;
	}

	public Employee getEid() {
		return eid;
	}

	public void setEid(Employee eid) {
		this.eid = eid;
	}

	public Con_room getRid() {
		return rid;
	}

	public void setRid(Con_room rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "Booking [bid=" + bid + ", title=" + title + ", start=" + start + ", end=" + end + ", created=" + created
				+ ", createdon=" + createdon + ", upated=" + upated + ", upatedon=" + upatedon + ", eid=" + eid
				+ ", rid=" + rid + "]";
	}
}