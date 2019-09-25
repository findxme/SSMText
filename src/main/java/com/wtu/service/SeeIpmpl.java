package com.wtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtu.dao.UserDao;
import com.wtu.pojo.User;

@Service
public class SeeIpmpl implements See{
	
	@Autowired
	UserDao userDao;

	public void aaa(User user) {
		// TODO Auto-generated method stub
//		userDao.inset(user);
		System.out.println("service²äµÄ·½·¨");
		
	}
	
	
	
	

}
