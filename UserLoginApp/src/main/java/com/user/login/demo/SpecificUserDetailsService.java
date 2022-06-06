package com.user.login.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SpecificUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserInfoRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserInfo user = repo.findByEmail(email);
		if(null == user) {
			throw new UsernameNotFoundException("User not Found");
		}
		return new SpecificUserDetails(user);
	}

}
