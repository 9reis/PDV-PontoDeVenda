package com.reis.pdv.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reis.pdv.entity.User;
import com.reis.pdv.service.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService{

	private UserService userService; 
	
	public CustomUserDetailService (UserService userService ){
		this.userService = userService; 
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		return new UserPrincipal(user);
	}

}

