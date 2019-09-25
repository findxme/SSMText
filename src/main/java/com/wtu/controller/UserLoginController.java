package com.wtu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wtu.pojo.User;
import com.wtu.service.See;

@Controller
@RequestMapping("/user")
public class UserLoginController {
	@Autowired
	See see;
	
	@RequestMapping("/login")
	public String userlogin(HttpServletRequest request,HttpServletResponse response) {
		String name=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println(request.getParameter("username"));
		
//		ModelAndView modelAndView = new ModelAndView("success");
		//See  see=new SeeIpmpl();
		User user= new User();
		user.setName(name);
		user.setPassword(password);
		see.aaa(user);
		System.out.println("++++++++++++++++++++++");
		//see.aaa(User user);
		
		return "success";
	}
	
	@RequestMapping("/login2")
	public String userlogin2(HttpServletRequest request,HttpServletResponse response) {
	System.out.println("++++++++++++++++++");
		return "success2";
	}
}
