package com.sistemacitas.core.controller;

import com.sistemacitas.core.entity.User;
import com.sistemacitas.core.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

	private final UserService userService;

	public UserController(@Lazy UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("roles")
	public List<String>getRoles(){
		List<String>roles= new ArrayList<>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
		
		return roles;
	}
	
	@GetMapping("crear")
	public String getUserForm(@ModelAttribute("usuario")User u) {
		return "userForm";
	}
	
	@PostMapping("crear")
	public String crearUsuario(@Valid @ModelAttribute("usuario") User u, RedirectAttributes redirectAttributes) {
		try {
			userService.crearUsuario(u);
			return "redirect:/login";
		}catch (Exception e){
			redirectAttributes.addFlashAttribute("errMessage",e.getMessage());
			return "redirect:/error";
		}
	}
}
