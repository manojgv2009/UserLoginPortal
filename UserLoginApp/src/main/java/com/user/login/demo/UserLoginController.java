package com.user.login.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserLoginController {
	
	@Autowired
	private UserInfoRepository repository;
	
	@GetMapping("")
	public String viewPage() {
		return "UserHomePage";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new UserInfo());
		return "RegistrationPage";
	}
	
	@GetMapping("/userLogin")
	public String loginPage() {
		return "LoginPage";
	}
	
	@PostMapping("/user_registration")
	public String userRegistration(UserInfo user, /* BindingResult result, */ Model model) {
		UserInfo userExist = repository.findByEmail(user.getEmail());
		if(null != userExist) {
			/*
			 * ObjectError error = new ObjectError("errorMsg", "Email Id already exists");
			 * result.addError(error); } if(result.hasErrors()) {
			 */
			model.addAttribute("user", user);
			model.addAttribute("errorMsg", "Email Id already exists");
			return "RegistrationPage";
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repository.save(user);
		return "RegistrationSuccess";
		
	}
	
	@GetMapping("/userInformation")
	public String viewUserInfo(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SpecificUserDetails userDetails = (SpecificUserDetails) auth.getPrincipal();
		UserInfo user = repository.findByEmail(userDetails.getUsername());
		model.addAttribute("userDetails", user);
		model.addAttribute("firstName", user.getFirstName()+" "+user.getLastName());
		return "UserInformationPage";
	}
	
	@GetMapping("/editAccountInfo")
	public String editAccountInfo(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SpecificUserDetails userDetails = (SpecificUserDetails) auth.getPrincipal();
		UserInfo user = repository.findByEmail(userDetails.getUsername());
		model.addAttribute("userDetails", user);
		return "EditAccountInfo";
	}
	
	@PostMapping("/edit_information")
	public String editInformation(UserInfo user, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SpecificUserDetails userDetails = (SpecificUserDetails) auth.getPrincipal();
		UserInfo userInfo = repository.findByEmail(userDetails.getUsername());
		//userInfo.setId(user.getId());
		//userInfo.setEmail(user.getEmail());
		
		if(user.getPassword() != null) {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(user.getPassword());
			repository.setUserInfoByIdWithPwd(user.getFirstName(), user.getLastName(), encodedPassword, user.getBirthday(), userInfo.getId());
		}else {
			repository.setUserInfoById(user.getFirstName(), user.getLastName(), user.getBirthday(), userInfo.getId());
		}
		model.addAttribute("userDetails", user);
		return "UserInformationPage";
	}

}
