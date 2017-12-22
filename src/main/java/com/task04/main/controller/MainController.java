package com.task04.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.task04.main.service.MainService;

@Controller
public class MainController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MainService service;

	@RequestMapping(path = {"/main", "/"}, method = RequestMethod.GET)
	public String goMain() {
		return "main";
	}
	
	@RequestMapping("getData")
	public void getData() {
		service.getData();
	}
	
}
