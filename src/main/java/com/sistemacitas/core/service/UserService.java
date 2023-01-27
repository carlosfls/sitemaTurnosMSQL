package com.sistemacitas.core.service;

import com.sistemacitas.core.models.User;
import com.sistemacitas.core.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService{

	private final UserRepo userRepo;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	private void init(){
		if (userRepo.findAll().isEmpty()){
			log.info("Creating the default user...");
			User root = User.builder()
					.username("admin")
					.password(passwordEncoder.encode("admin"))
					.rol("ROLE_ADMIN")
					.enabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.accountNonLocked(true)
					.build();
			userRepo.save(root);
			log.info("Default user created successfully");
		}else {
			log.info("The default user already exist");
		}
	}

	public List<User>listarUsuarios(){
		return userRepo.findAll();
	}

	public void crearUsuario(User u) throws Exception {
		Optional<User>usrBusq = userRepo.findByUsername(u.getUsername());
		if(usrBusq.isPresent()) {
			log.error("Ya esta creado un usuario con nombre "+ u.getUsername());
			throw new Exception("Ya esta creado un usuario con nombre "+ u.getUsername());
		}else{
			u.setEnabled(true);
			u.setAccountNonExpired(u.isEnabled());
			u.setCredentialsNonExpired(u.isEnabled());
			u.setAccountNonLocked(u.isEnabled());
			u.setPassword(passwordEncoder.encode(u.getPassword()));
			userRepo.save(u);
		}
	}

	public void eliminarUsuario(Long id) throws UsernameNotFoundException {
		Optional<User>usrBusq = userRepo.findById(id);
		if(usrBusq.isPresent()) {
			userRepo.delete(usrBusq.get());
		}else{
			log.error("No exite el usuario");
			throw new UsernameNotFoundException("No existe el usuario con id "+ id);
		}
	}

}
