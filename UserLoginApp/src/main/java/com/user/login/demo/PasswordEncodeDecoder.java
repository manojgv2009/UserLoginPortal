package com.user.login.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeDecoder {

	public static void main(String[] args) {
		PasswordEncoder encodeDecoder = new BCryptPasswordEncoder();
		
		String rawPwd = "7200980480";
		String encodedPwd = encodeDecoder.encode(rawPwd);
		
		System.out.println(encodedPwd);
		//System.out.println(encodedPwd.matches("7200980480",encodedPwd));

	}

}
