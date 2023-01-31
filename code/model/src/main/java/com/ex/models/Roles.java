package com.ex.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@Column(name = "role_id")
	public int role_id;
	@Column(name = "role")
	public String role;

	
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
