package com.sistemacitas.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String getWelcomePage() {
		return "welcomePage";
	}

	@GetMapping("/index")
	public String getIndex(){
		return "index";
	}
}
