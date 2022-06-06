package com.user.login.demo;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	@Query("SELECT user FROM UserInfo user WHERE user.email= ?1")
	UserInfo findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserInfo user SET user.firstName = ?1, user.lastName = ?2,  user.birthday = ?3 WHERE user.id = ?4")
	void setUserInfoById(String firstName, String lastName, String birthday, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserInfo user SET user.firstName = ?1, user.lastName = ?2, user.password = ?3, user.birthday = ?4 WHERE user.id = ?5")
	void setUserInfoByIdWithPwd(String firstName, String lastName, String password, String birthday, int id);
	
	@Query("SELECT user.email from UserInfo user")
	@Override
	default long count() {
		// TODO Auto-generated method stub
		return 0;
	}
}
