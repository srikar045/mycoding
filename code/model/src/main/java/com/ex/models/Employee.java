package com.ex.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.ex.con_room.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "employees")
public class Employee {
//eid,fname,lname,gender,email,mobile,username,password,created_by,created_on,updated_by,updated_on
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eid")
	@SequenceGenerator(name = "eid", sequenceName = "employees_eid_seq",initialValue=1, allocationSize = 1)
	@Column(name = "eid")
	private int eid;

	@Column(name = "fname")
	private String fname;

	@Column(name = "lname")
	private String lname;

	@Column(name = "gender")
	private String gender;

	@Column(name = "email",unique = true)
	private String email;

	@Column(name = "mobile")
	private long mobile;

	@Column(name = "username",unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "created_by")
	private String created_by;

	@Column(name = "created_on")
	private Date created_on;

	@Column(name = "updated_by")
	private String updated_by;

	@Column(name = "updated_on")
	private Date updated_on;
	
	@ManyToMany(fetch = FetchType.EAGER)//,cascade = CascadeType.ALL
	@JoinTable(name="Roles_employees",
			joinColumns ={
					@JoinColumn(name="eid")	},
			inverseJoinColumns = {
					@JoinColumn(name="role_id")})
	private Set<Roles>role;
	
	@OneToMany(mappedBy = "eid",fetch = FetchType.LAZY,cascade={CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.REFRESH,
			CascadeType.REMOVE})
			@JsonIgnore 
			@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
			private Set<Booking> data;

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public Set<Roles> getRole() {
		return role;
	}

	public void setRole(Set<Roles> role) {
		this.role = role;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", fname=" + fname + ", lname=" + lname + ", gender=" + gender + ", email="
				+ email + ", mobile=" + mobile + ", username=" + username + ", password=" + password + ", created_by="
				+ created_by + ", created_on=" + created_on + ", updated_by=" + updated_by + ", updated_on="
				+ updated_on + ", role=" + role + ", data=" + data + "]";
	}
	

}
