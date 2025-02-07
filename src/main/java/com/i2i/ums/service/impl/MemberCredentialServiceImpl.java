package com.i2i.ums.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.i2i.ums.service.MemberCredentialService;
import com.i2i.ums.service.MemberService;

@Service
@Slf4j
public class MemberCredentialServiceImpl implements MemberCredentialService {
    private static final Logger log = LoggerFactory.getLogger(MemberCredentialServiceImpl.class);
    private final MemberService memberService;

    @Autowired
    public MemberCredentialServiceImpl(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    public UserDetails loadUserByUsername(String username) {
        UserDetails credentials = memberService.getUserCredentials(username);
        log.info("The credentials of the Member ({}) retrieved successfully.", username);
        return credentials;
    }
}
