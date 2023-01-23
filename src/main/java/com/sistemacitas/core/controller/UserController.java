package com.sistemacitas.core.controller;

import com.sistemacitas.core.models.User;
import com.sistemacitas.core.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("roles")
	public List<String>getRoles(){
		List<String>roles= new ArrayList<>();
		roles.add("ROLE_GESTOR_CITAS");
		roles.add("ROLE_ADMIN");
		return roles;
	}

	@GetMapping("/")
	public String getUsersHome(Model modelo) {
		modelo.addAttribute("listadoUsuarios", userService.listarUsuarios());
		return "usersHome";
	}
	
	@GetMapping("crear")
	public String getUserForm(@ModelAttribute("usuario")User u) {
		return "userForm";
	}
	
	@PostMapping("crear")
	public String crearUsuario(@Valid @ModelAttribute("usuario") User u) throws Exception {
		userService.crearUsuario(u);
		return "redirect:/user/";

	}

	@GetMapping("eliminar/{id}")
	public String deleteUserById(@PathVariable("id") Long id) {
		userService.eliminarUsuario(id);
		return "redirect:/user/";
	}
}
