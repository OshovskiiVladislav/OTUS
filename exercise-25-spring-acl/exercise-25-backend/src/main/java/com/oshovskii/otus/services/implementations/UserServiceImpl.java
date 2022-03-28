package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Authority;
import com.oshovskii.otus.models.User;
import com.oshovskii.otus.repositories.UserRepository;
import com.oshovskii.otus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with [%s] not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getAuthorities())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Authority> roles) {
        return roles.stream().map(auth -> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList());
    }
}
