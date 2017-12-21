package com.task04.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping(path = {"/main", "/"}, method = RequestMethod.GET)
	@ResponseBody
	public String goMain() {
		return "main";
	}
}
