package com.auth.oauth.global.config;

import com.auth.oauth.dto.entity.UserDetailsImpl;

public interface ChatbotUserService {

	public UserDetailsImpl findByUsername(String username);
	public UserDetailsImpl save(UserDetailsImpl user);

}
