package com.example.SBTRegistratoinApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.SBTRegistratoinApp.entity.UserDtls;
import com.example.SBTRegistratoinApp.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String em) throws UsernameNotFoundException {
		
		try {
			UserDtls u = repo.findByEmail(em);
			if(u==null) {
				throw new UsernameNotFoundException("no user");
			}
			else
			{
				return new CustomUserDetails(u);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
