package com.platzipizzeria.service;

import com.platzipizzeria.persistence.entity.UserEntity;
import com.platzipizzeria.persistence.entity.UserRoleEntity;
import com.platzipizzeria.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = this.userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found" ));

        System.out.println(userEntity);

        String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(this.grantedAuthorities(roles)) // <-- Cambiado a grantedAuthorities para enviar la lista completa con roles y permisos
                .accountLocked(userEntity.getLocked())
                .disabled(userEntity.getDisabled())
                .build();
    }

    // Método auxiliar que retorna los permisos específicos según el rol
    private String[] getAuthorities(String role) {
        if ("ADMIN".equals(role) || "CUSTOMER".equals(role)) {
            return new String[] {"random_order"};
        }

        return new String[] {};
    }

    // Método que construye la lista de GrantedAuthority agregando el prefijo ROLE_ y los permisos
    private List<GrantedAuthority> grantedAuthorities(String[] roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority : this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }
}