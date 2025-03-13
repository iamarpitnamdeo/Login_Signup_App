package com.example.SBTRegistratoinApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SBTRegistratoinApp.entity.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls,Integer>{
	public UserDtls findByEmail(String em);
}
