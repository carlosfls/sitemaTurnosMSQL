package com.sistemacitas.core.service;

import com.sistemacitas.core.models.User;
import com.sistemacitas.core.repo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public JpaUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usBusq=userRepo.findByUsername(username);
        if(usBusq.isPresent()) {
            User u=usBusq.get();
            List<SimpleGrantedAuthority> permisos=new ArrayList<>();
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
