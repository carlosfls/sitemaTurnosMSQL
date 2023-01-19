package com.sistemacitas.core.service;

import com.sistemacitas.core.models.User;
import com.sistemacitas.core.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService{

	private final UserRepo userRepo;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User>usBusq=userRepo.findByUsername(username);
		if(usBusq.isPresent()) {
			User u=usBusq.get();
			List<SimpleGrantedAuthority>permisos=new ArrayList<>();
			permisos.add(new SimpleGrantedAuthority(u.getRol()));
			
			return new org.springframework.security.core.userdetails
					.User(
						  u.getUsername(),
						  u.getPassword(),
						  u.isEnabled(),
						  u.isAccountNonExpired(),
						  u.isCredentialsNonExpired(),
						  u.isAccountNonExpired(),
						  permisos);
		}
		throw new UsernameNotFoundException(username);
		
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
