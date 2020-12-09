package com.auth.oauth.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.oauth.dto.entity.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ChatbotUserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("UserDetailsServiceImpl.loadUserByUsername :::: {}",username);

		UserDetailsImpl user = service.findByUsername(username);

		if(ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("Invalid username, please check user info !");
		}

		user.setAuthorities(AuthorityUtils.createAuthorityList(String.valueOf(user.getRole())));

		return user;
	}
}