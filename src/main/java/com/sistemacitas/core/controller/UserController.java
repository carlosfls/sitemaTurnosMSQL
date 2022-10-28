package com.sistemacitas.core.controller;

import com.sistemacitas.core.entity.User;
import com.sistemacitas.core.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

	private final UserRepo userRepo;

	private final PasswordEncoder passwordEncoder;
	
	@ModelAttribute("roles")
	public List<String>getRoles(){
		List<String>roles=new ArrayList<String>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
		
		return roles;
	}
	
	@GetMapping("crear")
	public String getUserForm(@ModelAttribute("usuario")User u) {
		return "userForm";
	}
	
	@PostMapping("crear")
	public String crearUsuario(@Valid @ModelAttribute ("usuario") User u, Model modelo) {
		Optional<User>usrBusq = userRepo.findByUsername(u.getUsername());
		if(usrBusq.isPresent()) {
			System.out.println("Ya esta creado un usuario con nombre "+ u.getUsername());
			return "redirect:/error";
		}
		u.setEnabled(true);
		u.setAccountNonExpired(u.isEnabled());
		u.setCredentialsNonExpired(u.isEnabled());
		u.setAccountNonLocked(u.isEnabled());
		u.setPassword(passwordEncoder.encode(u.getPassword()));

		userRepo.save(u);
		
		return "redirect:/login";
	}
}
