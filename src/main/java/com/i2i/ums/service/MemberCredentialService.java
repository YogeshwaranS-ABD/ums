package com.i2i.ums.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberCredentialService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
