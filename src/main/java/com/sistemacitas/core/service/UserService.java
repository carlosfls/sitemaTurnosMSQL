package com.sistemacitas.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistemacitas.core.entity.User;
import com.sistemacitas.core.repo.UserRepo;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
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

}
