package com.auth.oauth.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.oauth.dto.entity.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatbotUserServiceImpl implements ChatbotUserService {

	@Autowired
	private ChatbotUserRepository repository;

	@Override
	public UserDetailsImpl findByUsername(String username) {
		log.info("ChatbotUserServiceImpl.findByUsername :::: {}",username);
		return repository.findByUsername(username);
	}

	@Override
	public UserDetailsImpl save(UserDetailsImpl user) {
		log.info("ChatbotUserServiceImpl.save :::: {}",user.toString());
		return repository.save(user);
	}
}