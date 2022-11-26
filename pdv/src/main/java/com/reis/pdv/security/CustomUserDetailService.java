package com.reis.pdv.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reis.pdv.dto.LoginDTO;
import com.reis.pdv.entity.User;
import com.reis.pdv.exceptions.PasswordNotFoundException;
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
		
		if(user == null) {
			throw new UsernameNotFoundException("Login invalido!");
		}
		
		return new UserPrincipal(user);
	}
	
	public void verifyUserCredentials(LoginDTO login) {
		UserDetails user = loadUserByUsername(login.getUsername());
		
		boolean passwordIsTheSame = SecurityConfig.passwordEncoder()
									.matches(login.getPassword(), user.getPassword());
		
		if (!passwordIsTheSame) {
			throw new PasswordNotFoundException("Senha invalida");
		}
	}

}

