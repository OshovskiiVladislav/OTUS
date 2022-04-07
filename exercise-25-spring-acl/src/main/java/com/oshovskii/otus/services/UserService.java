package com.oshovskii.otus.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserByUsername(String username);
}
