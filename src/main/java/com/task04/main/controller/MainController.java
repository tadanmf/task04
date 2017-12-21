package com.task04.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@RequestMapping(path = {"/main", "/"}, method = RequestMethod.GET)
	public String goMain() {
		return "main";
	}
}
