package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.models.Roles;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer>{
public Roles findByRole(String role);

}